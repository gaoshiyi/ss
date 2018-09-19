
package com.sstc.hmis.conf.cas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.alibaba.fastjson.JSON;
import com.sstc.hmis.conf.cas.constants.ShiroCasProperties;
import com.sstc.hmis.logger.bean.LoggerUser;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.permission.data.cas.SimpleVo;
import com.sstc.hmis.permission.service.PermissionService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.service.StaffService;
import com.sstc.hmis.statusSync.handler.UserSessionHandler;
import com.sstc.hmis.statusSync.model.UserSession;

@SuppressWarnings("deprecation")
public class MyCasRealm extends CasRealm {

	private static final Logger LOG = LoggerFactory.getLogger(MyCasRealm.class);
	@Autowired
	private StaffService staffService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private ShiroCasProperties shiroCasProperties;
	
	@Value("${hostname.portal}")
	private String portalUri;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@Override
	protected void onInit() {
		this.setCasServerUrlPrefix(shiroCasProperties.getSsoServerUrlPrefix());
		this.setCasService(shiroCasProperties.getCasService());
		super.onInit();
	}
	
	
	/**
	 * 用户权限校验
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		String account = (String) principals.getPrimaryPrincipal();
		LOG.debug("当前登录用户帐号：{} ", account);
		Staff u = (Staff) SecurityUtils.getSubject().getSession().getAttribute(PortalConstants.SESSION_USER_KEY);
		if(u == null){
			u = staffService.queryByUserId(account);
		}
		// 用户信息为空，或者不是删除状态
		if (u == null || u.getStatus() == 3) {
			LOG.error("用户帐号{}不存在", account);
			throw new UnknownAccountException();
		}

		Session session = SecurityUtils.getSubject().getSession();
		// 用户信息
		session.setAttribute(PortalConstants.SESSION_USER_KEY, u);

		@SuppressWarnings("unchecked")
		Set<String> roleIdSet = (Set<String>) session.getAttribute(PortalConstants.PERM_USER_ROLE_IDS);
		// 用户角色信息
		if (roleIdSet != null && roleIdSet.size() > 0) {
			roleIdSet = (Set<String>) roleService.findUserRole(u);
			authorizationInfo.setRoles(roleIdSet);
			session.setAttribute(PortalConstants.PERM_USER_ROLE_IDS, roleIdSet);
			
			
			session.setAttribute("roleList", roleIdSet);
			List<Permission> permList = permissionService.findUserPerms(roleIdSet);
			
			Set<String> urlSet = new HashSet<>();
			List<Permission> list = new ArrayList<>();
			
			for (Permission permission : permList) {
				Short type = permission.getType();
				if (type == PortalConstants.PERMS_FUNCTION || type == PortalConstants.PERMS_ACTION || type == PortalConstants.PERMS_MODULE) {
					String url = permission.getUrl();
					if(StringUtils.isNoneBlank(url)){
						urlSet.add(url);
					}
					String[] subUrl = permission.getSubUrl();
					if(subUrl != null && subUrl.length > 0){
						for (String sub : subUrl) {
							if(StringUtils.isNoneBlank(sub)){
								urlSet.add(sub);
							}
						}
					}
				} else if (type == PortalConstants.PERMS_SYSTEM) {
					list.add(permission);
				}
			}
			
			session.setAttribute(PortalConstants.PERM_SYS_LIST, list);
			session.setAttribute(PortalConstants.PERM_USER_PERMS, permList);
			stringRedisTemplate.opsForValue()
				.set(CasConstants.getSessionUserPermKey(u.getAccount()), JSON.toJSONString(urlSet), 12, TimeUnit.HOURS);
			authorizationInfo.setStringPermissions(urlSet);
		}
		return authorizationInfo;
	}

	/**
	 * 用户登录校验
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		CasToken casToken = (CasToken) token;
		if (token == null) {
			return null;
		}
		String ticket = (String) casToken.getCredentials();
		if (StringUtils.isBlank(ticket)) {
			return null;
		}
		ticketValidator = ensureTicketValidator();
		try {
			// ticket校验
			AssertionImpl casAssertion = (AssertionImpl) ticketValidator.validate(ticket, getCasService());

			// 获取当事人信息
			PrincipalVo casPrincipal = casAssertion.getPrincipalVo();
			String account = casPrincipal.getName();


			List<Object> principals = CollectionUtils.asList(casPrincipal.getName());
			PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
			Staff u = staffService.queryByUserId(account);
			// 用户信息为空，或者不是删除状态
			if (u == null || u.getStatus() == 3) {
				LOG.error("用户帐号{}不存在", account);
				throw new UnknownAccountException();
			}
			
			Session session = SecurityUtils.getSubject().getSession();
			//其他子系统自动登录时同步portal切换的酒店和班次信息
			changeDefault(u, session, ticket);
			
			UserSession userSession = new UserSession(account,session.getId().toString(),ticket, getCasService());
			userSession.setAppName(appName);
			UserSessionHandler handler = new UserSessionHandler(stringRedisTemplate);
			CasConstants.setStSessionIdCache(ticket,session.getId().toString(),stringRedisTemplate);
			handler.recordUserSessionInfo(userSession);
			
			//剔除上次登录用户的session和tgt信息
//			Map<String,Object> msg = new HashMap<>(2);
//			msg.put("account", account);
//			msg.put("type", MqConstants.MSG_TYPE_KICKOUT);
//			msgQueueService.sender(MqConstants.QUEUE_KICKOUT, JSON.toJSONString(msg));
			
			// 用户信息
			session.setAttribute(PortalConstants.SESSION_USER_KEY, u);
			LoggerUser log = new LoggerUser(u.getAccount(), u.getDefaultHotelId(), u.getGrpId());
			session.setAttribute(PortalConstants.SESSION_LOGGER_KEY, log);
			Set<String> roleIdSet = roleService.findUserRole(u);
			List<Permission> list = new ArrayList<>();
			if (roleIdSet != null && !roleIdSet.isEmpty()) {
				session.setAttribute(PortalConstants.PERM_USER_ROLE_IDS, roleIdSet);
				List<Permission> permList = permissionService.findUserPerms(roleIdSet);
				for (Permission permission : permList) {
					Short type = permission.getType();
					if (type == PortalConstants.PERMS_SYSTEM) {
						list.add(permission);
					}
				}
			}
			session.setAttribute(PortalConstants.PERM_SYS_LIST, list);

			String uid = u.getId();
			List<SimpleVo> hotelList = staffService.findUserStaffHotel(uid);
			session.setAttribute(PortalConstants.SESSION_HOTELS_KEY, hotelList);

			return new SimpleAuthenticationInfo(principalCollection, ticket);
		} catch (TicketValidationException e) {
			throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
		}
	}
	
	/**
	 * 其他子系统自动登录时同步portal切换的酒店和班次信息
	 * @param u
	 * @param casPrincipal
	 * @param session 
	 */
	private void changeDefault(Staff u, Session session,final String ticket) {
		
		String tgtId = CasConstants.getTgtBySt(ticket, stringRedisTemplate);
		
		PrincipalVo casPrincipal = CasConstants.getPrincipalInfo(u.getAccount(), tgtId, stringRedisTemplate);
		String hotelId = casPrincipal.getHotelId();
		if(StringUtils.isNotBlank(hotelId)){
			u.setHotelId(hotelId);
		}
		String shiftId = casPrincipal.getShiftId();
		if(StringUtils.isNotBlank(shiftId)){
			u.setShiftId(shiftId);
		}
		String shiftCode = casPrincipal.getShiftCode();
		if(StringUtils.isNotBlank(shiftCode)){
			u.setShiftCode(shiftCode);
		}
		String localeStr = casPrincipal.getLocale();
		if(StringUtils.isNotBlank(localeStr)){
			Locale locale = Locale.CHINA;
			try {
				if("en".equals(localeStr)){
					locale = Locale.ENGLISH;
				}
				session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
			} catch (Exception e) {
				LOG.error("未知错误",e);
			}
		}
	}

	private TicketValidator ticketValidator;
	
	@Override
	protected TicketValidator ensureTicketValidator() {
        if (this.ticketValidator == null) {
            this.ticketValidator = createTicketValidator();
        }
        return this.ticketValidator;
    }
	
	@Override
    protected TicketValidator createTicketValidator() {
        String urlPrefix = getCasServerUrlPrefix();
        if ("saml".equalsIgnoreCase(getValidationProtocol())) {
            return new Saml11TicketValidator(urlPrefix);
        }
        return new CasSstcServiceTicketValidator(urlPrefix);
    }

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
//		LOG.info("删除用户{}所有AuthorizationInfo",((SimplePrincipal)principals.getPrimaryPrincipal()).getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
//		LOG.info("删除用户{}所有AuthenticationInfo",((SimplePrincipal)principals.getPrimaryPrincipal()).getName());
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		LOG.debug("1- AuthorizationCache清除前：｛｝",getAuthorizationCache());
		getAuthorizationCache().clear();
		LOG.debug("1- AuthorizationCache清除前：｛｝",getAuthorizationCache());
	}

	public void clearAllCachedAuthenticationInfo() {
		LOG.debug("2- AuthenticationInfo清除前：｛｝",getAuthenticationCache());
		getAuthenticationCache().clear();
		LOG.debug("2- AuthenticationInfo清除后：｛｝",getAuthenticationCache());
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}


}
