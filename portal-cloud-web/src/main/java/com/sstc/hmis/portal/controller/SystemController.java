/**
 * 
 */
package com.sstc.hmis.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.sstc.hmis.conf.cas.MyCasRealm;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.permission.service.PermissionService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.service.StaffMenuService;
import com.sstc.hmis.permission.service.StaffService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;
import com.sstc.hmis.statusSync.handler.UserSessionHandler;
import com.sstc.hmis.statusSync.model.SessionUpdateInfo;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.spring.SpringUtil;
import com.xiaoleilu.hutool.util.ArrayUtil;

/**
  * <p> Title: SystemController </p>
  * <p> Description:  系统权限控制器 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年5月22日 下午5:20:01
   */
@Controller
@RequestMapping("/sys")
public class SystemController extends BaseController{
	
	@Value("${spring.redis.expire:1800}")
	private int sesseionExpire;

	@Value("${sys.defaultPwd:111111}")
	private String defaultPwd;
	@Value("${spring.profiles.active:empark}")
	private String profile;
	
	private static final String[] testEnv = new String[]{"dev","uat","tst","edu"};
	
	@Autowired
	StaffService staffService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private StaffMenuService staffMenuService;
	@Autowired
	private RoleService roleService;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private DefaultWebSessionManager sessionManager;
	
	
	@ResponseBody
	@RequestMapping("/perm/module")
	public List<Permission> list(Permission query){
		try {
			return permissionService.listUserSysModule(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>(0);
	}
	
	/**
	 * 员工菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/staff/menu")
	public Result menu(){
		try {
			Staff staff = getLoginStaff();
			String salt = staff.getSalt();
			String encodePwd = HashUtils.stringMd5Encode(defaultPwd, salt);
			String dbPwd = staff.getPassword();
			if(!ArrayUtil.contains(testEnv, profile) && StringUtils.equalsAnyIgnoreCase(dbPwd, encodePwd)){
				return Result.DEFAULT_PWD;
			}else{
				return staffMenuService.listUserMenu();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.SUCCESS;
	}
	
	/**
	 * 班次查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/shift")
	public Result shift(){
		try {
			
			String tgtId = CasConstants.getTgtBySid(LoginInfoHolder.getSessionId(), stringRedisTemplate);
			PrincipalVo info = CasConstants.getPrincipalInfo(LoginInfoHolder.getLoginAccount(), tgtId, stringRedisTemplate);
			
			Result result = staffService.shift(info);
			Session session = SecurityUtils.getSubject().getSession();
			Staff staff = LoginInfoHolder.getLoginInfo();
			Map<String,Object> data = (Map<String, Object>) result.getResult();
			Map<String,Object> current = (Map<String, Object>) data.get("current");
			if(current != null){
				final String shiftCode = (String)current.get("StrVal1");
				final String shiftId = (String) current.get("id");
				staff.setShiftId(shiftId);
				staff.setShiftCode(shiftCode);
				session.setAttribute(PortalConstants.SESSION_USER_KEY,staff);
				PrincipalVo principalVo = new PrincipalVo(staff.getAccount(), staff.getHotelId(), "cn", shiftCode,shiftId );
				CasConstants.updatePrincipalInfo(principalVo, LoginInfoHolder.getLoginAccount(), tgtId, stringRedisTemplate);
			}
			return result; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.SUCCESS;
	}
	
	
	
	/**
	 * 切换工作酒店
	 * @param hotelId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/hotel/switch")
	public Result switchHotel(String hotelId){
		Session session = SecurityUtils.getSubject().getSession();
		LoginInfoHolder.setSessionId(session.getId().toString());
		Staff staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
		if(staff != null){
			staff.setHotelId(hotelId);
			String tgtId = CasConstants.getTgtBySid(LoginInfoHolder.getSessionId(), stringRedisTemplate);
			PrincipalVo info = CasConstants.getPrincipalInfo(LoginInfoHolder.getLoginAccount(), tgtId, stringRedisTemplate);
			Result result = staffService.shift(info);
			Map<String,Object> data = (Map<String, Object>) result.getResult();
			Map<String,Object> current = (Map<String, Object>) data.get("current");
			if(current != null){
				staff.setShiftId((String)current.get("id"));
				staff.setShiftCode((String)current.get("StrVal1"));
			}
			session.setAttribute(PortalConstants.SESSION_USER_KEY, staff);
			
			Set<String> roleIdSet = roleService.findUserRole(staff);
			List<Permission> permList = permissionService.findUserPerms(roleIdSet);
			session.setAttribute(PortalConstants.PERM_SYS_LIST, 
					permList.stream()
					.filter(p -> p.getType() == PortalConstants.PERMS_SYSTEM)
					.collect(Collectors.toList()));
			
			broadcastDataChange(staff,null);
		}else{
			return Result.ERROR_SYS;
		}
		try {
			cleanCachedAuthenticationInfo();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR_SYS;
		}
		
		return result;
	}
	
	/**
	 * 切换语言
	 * @param localeStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/locale/switch")
	public Result switchLocale(String localeStr){
		Locale locale = Locale.CHINA;
		try {
			if("en".equals(localeStr)){
				locale = Locale.ENGLISH;
			}
			SpringUtil.applicationContext.getBean(SessionLocaleResolver.class).setLocaleContext(request, response, new SimpleLocaleContext(locale));
			SecurityUtils.getSubject().getSession().setAttribute(PortalConstants.SESSION_LOCALE_KEY, localeStr);
			
			broadcastDataChange(LoginInfoHolder.getLoginInfo(),localeStr);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR_SYS;
		}
		
		return result;
	}
	/**
	 * 班次切换
	 * @param id
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shift/switch")
	public Result switchShift(String id,String code){
		Session session = SecurityUtils.getSubject().getSession();
		Staff staff = (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
		if(staff != null){
			staff.setShiftId(id);
			staff.setShiftCode(code);
			session.setAttribute(PortalConstants.SESSION_USER_KEY, staff);
			broadcastDataChange(staff,null);
		}else{
			return Result.ERROR_SYS;
		}
		return result;
	}
	
	
	private void cleanCachedAuthenticationInfo(){
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		MyCasRealm casRealm = (MyCasRealm) securityManager.getRealms().iterator().next();
		casRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
	/**
	 * 框架上的数据（班次、语言、酒店）变更，广播到其他系统
	 * @param staff 员工信息
	 * @param localeStr 语言字符串
	 */
	private void broadcastDataChange(Staff staff, String localeStr){
		String account = staff.getAccount();
		String hotelId = staff.getHotelId();
		String shiftId = staff.getShiftId();
		String shiftCode = staff.getShiftCode();
		SessionUpdateInfo info = new SessionUpdateInfo.Builder(account)
				.hotelId(hotelId).shiftId(shiftId).shiftCode(shiftCode).build();
		String type = UserSessionHandler.SYNC_STAFF;
		
		UserSessionHandler handler = new UserSessionHandler(redisTemplate);
		handler.setStringRedisTemplate(stringRedisTemplate);
		
		
		if(StringUtils.isNotBlank(localeStr)){
			info.setLocale(localeStr);
			type = UserSessionHandler.SYNC_LOCALE;
		}
//		handler.updatePrincipalInfo(principalVo, account);
		handler.broadcastDataChange(info,type,sessionManager);
	}
	
}
