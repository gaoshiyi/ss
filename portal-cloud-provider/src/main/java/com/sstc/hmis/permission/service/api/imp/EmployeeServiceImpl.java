package com.sstc.hmis.permission.service.api.imp;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.mdata.common.service.api.SmsSenderService;
import com.sstc.hmis.mdata.common.service.api.easemob.ChatGroupAPI;
import com.sstc.hmis.mdata.common.service.api.easemob.IMUserAPI;
import com.sstc.hmis.mdata.common.service.data.SmsSendParams;
import com.sstc.hmis.mdata.common.service.data.SmsSendSignProperties;
import com.sstc.hmis.mdata.common.service.data.SmsSendTplProperties;
import com.sstc.hmis.model.constants.CacheConstants;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDepartmentMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDptPostMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDptStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTGroupHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPostResponsibilityMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffTeamMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTWorkTeamMapper;
import com.sstc.hmis.permission.dbaccess.dao.TUsedLinkmanMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptPost;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptPostExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptStaffExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibilityExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTQuery;
import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample.Criteria;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeam;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;
import com.sstc.hmis.permission.dbaccess.data.TUsedLinkman;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.CommonUtils;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.NameResult;
import com.sstc.hmis.util.bean.utils.BeanUtils;
import com.sstc.hmis.util.name.NameUtils;
import com.sstc.hmis.util.sms.SendResult;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.User;
@RestController
public class EmployeeServiceImpl extends BaseServiceImpl<Staff> implements EmployeeService{
	
	
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Value("${sys.defaultPwd:111111}")
	private String defaultPwd;
	
	@Autowired
	private PermsTStaffMapper permsTStaffMapper;
	@Autowired
	private PermsTDepartmentMapper permsTDepartmentMapper;
	@Autowired
	private PermsTGroupHotelMapper permsTGroupHotelMapper;
	@Autowired
	private PermsTStaffHotelMapper permsTStaffHotelMapper;
	@Autowired
	private PermsTDptPostMapper permsTDptPostMapper;
	@Autowired
	private PermsTPostResponsibilityMapper permsTPostResponsibilityMapper;
	@Autowired
	private PermsTStaffRoleMapper permsTStaffRoleMapper;
	@Autowired
	private PermsTDptStaffMapper permsTDptStaffMapper;
	@Autowired
	private PermsTStaffTeamMapper permsTStaffTeamMapper;
	@Autowired
	private PermsTRoleMapper permsTRoleMapper;
	@Autowired
	private PermsTWorkTeamMapper permsTWorkTeamMapper;
	@Autowired
	private TUsedLinkmanMapper tUsedLinkmanMapper;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private SmsSenderService smsSenderService;
	
	@Autowired
	private ChatGroupAPI chatGroupAPI;
	
	@Autowired
	private IMUserAPI imUserAPI;
	
	public PermsTStaff convertStaff2PermsTStaff(Staff staff) {
		try {
			return BeanUtils.copyServiceBean2DbBean(staff, new PermsTStaff());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Staff convertPermsTStaff2Staff(PermsTStaff permsTStaff) {
		try {
			return BeanUtils.copyDbBean2ServiceBean(permsTStaff, new Staff());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Staff find(@RequestBody Staff t) throws AppException {
		return null;
	}

	@Override
	public Result updateStaffInfo(@RequestBody Staff staff) throws AppException {
//		去掉信息的前後空格；
		staffInfoTrim(staff);
		Short status = staff.getStatus();
		String hotelId = staff.getDefaultHotelId();
		
		//删除操作
		if (status != null && status == Constants.STATUS_DEL) {
			return deleteStaff(staff,hotelId);
		}
		
		if (status == null || status != Constants.STATUS_DEL) {
//			账号重复
			int size = isRepeat(staff);
			if (size > 0) {
				Result repeatResult = Result.ERROR_REPEAT;
				repeatResult.setMessage("该账号已经存在！");
				return repeatResult;
			}
		}
//		获取之前的员工信息；
		PermsTStaff staffUpdateBefore = permsTStaffMapper.selectByPrimaryKey(staff.getId());

		Result repeatResult = staffValidata(staff, staffUpdateBefore);
		if(repeatResult != null){
			return repeatResult;
		}
		
	
		String dptId = staff.getDefaultDptId();
		String postId = staff.getDefaultPostId();
		
		Integer isDelFlag = staff.getIsDelFlag();
		/*
		 * 是否删除该员工在默认酒店的登录权限？
		 * 修改酒店时候给isDelFlag赋值；
		 * 	 0：删除原有录入的角色信息；
		 * 	 1：不删除原有录入的角色信息；
		 * 	 null：进到员工界面没有触发修改酒店，直接修改部门/职位；
		 */
		String postBefore = staffUpdateBefore.getClDefaultPostId();
		String dptBefore = staffUpdateBefore.getClDefaultDptId();
		String hotelBefore = staffUpdateBefore.getClDefaultHotelId();
		
		PermsTStaffHotelExample staffHotelExample = new PermsTStaffHotelExample();
		PermsTStaffHotelExample.Criteria criteria = staffHotelExample.createCriteria();
		criteria.andClHotelIdEqualTo(hotelBefore);
		criteria.andClDptIdEqualTo(dptBefore);
		criteria.andClPostIdEqualTo(postBefore);
		String staffId = staff.getId();
		criteria.andClStaffIdEqualTo(staffId);
		String loginAccount = LoginInfoHolder.getLoginAccount();
		
		int effectLine = 0;
		//没有修改默认归属酒店
		if(isDelFlag == null){
			//修改了职位或者部门
			if(StringUtils.isNoneBlank(postId,dptId) && (!StringUtils.equals(postBefore, postId) || !StringUtils.equals(dptBefore, dptId))){
				//1、根据原组织信息更新staff_hotel
				PermsTStaffHotel staffHotel = new PermsTStaffHotel();
				staffHotel.setClDptId(dptId);
				staffHotel.setClPostId(postId);
				staffHotel.setClModifyBy(loginAccount);
				
				effectLine = permsTStaffHotelMapper.updateByExampleSelective(staffHotel, staffHotelExample);
				LOG.debug("{}更新{}工作酒店{}信息{}条，原组织信息{} > {}，新组织信息{} > {}",loginAccount, staffId, hotelBefore, effectLine, dptBefore, postBefore, dptId, postId );
			}
		}else if(isDelFlag == 0){
			//修改了默认的工作酒店后并确认删除之前默认酒店的登录权限
			//1、根据原组织信息删除staff_hotel
			PermsTStaffHotel staffHotel = new PermsTStaffHotel();
			staffHotel.setClStatus(Constants.STATUS_DEL);
			staffHotel.setClModifyBy(loginAccount);
			effectLine = permsTStaffHotelMapper.updateByExampleSelective(staffHotel, staffHotelExample);
			LOG.debug("{}删除{}工作酒店{}信息{}条，原组织信息{} > {}，新组织信息{} > {}",loginAccount, staffId, hotelBefore, effectLine, dptBefore, postBefore );
			
			//2、根据原hotelId删除staff_role、staff_team
			PermsTStaffRole staffRole = new PermsTStaffRole();
			staffRole.setClStatus(Constants.STATUS_DEL);
			staffRole.setClModifyBy(loginAccount);
			PermsTStaffRoleExample staffRoleExample = new PermsTStaffRoleExample();
			PermsTStaffRoleExample.Criteria staffRoleCriteria = staffRoleExample.createCriteria();
			staffRoleCriteria.andClHotelIdEqualTo(hotelId);
			staffRoleCriteria.andClStaffIdEqualTo(staffId);
			effectLine = permsTStaffRoleMapper.updateByExampleSelective(staffRole, staffRoleExample);
			LOG.debug("{}删除{}工作酒店{}角色信息{}条",loginAccount, staffId, hotelBefore, effectLine);
			
			//3、插入新的默认工作酒店信息（判断是否已经存在，如果存在仅更新）
			PermsTStaffHotelExample staffHotelExample2 = new PermsTStaffHotelExample();
			PermsTStaffHotelExample.Criteria criteria2 = staffHotelExample2.createCriteria();
			criteria2.andClWorkHotelIdEqualTo(hotelId);
			criteria2.andClDptIdEqualTo(dptId);
			criteria2.andClPostIdEqualTo(postId);
			
			PermsTStaffHotel newStaffHotel = new PermsTStaffHotel();
			newStaffHotel.setClWorkHotelId(hotelId);
			newStaffHotel.setClDptId(dptId);
			newStaffHotel.setClPostId(postId);
			newStaffHotel.setClHotelId(hotelId);
			newStaffHotel.setClStaffId(staffId);
			newStaffHotel.setClGrpId(staffUpdateBefore.getClGrpId());
			newStaffHotel.setClModifyBy(loginAccount);
			newStaffHotel.setClStatus(Constants.STATUS_NORMAL);
			
			effectLine = permsTStaffHotelMapper.updateByExampleSelective(newStaffHotel, staffHotelExample2);
			if(effectLine == 0){
				newStaffHotel.setClCreateBy(loginAccount);
				newStaffHotel.setClId(HashUtils.uuidGenerator());
				effectLine = permsTStaffHotelMapper.insertSelective(newStaffHotel);
				LOG.debug("{}为用户{}插入工作酒店信息{} > {} > {} {}条",loginAccount, staffId, hotelId, dptId, postId, effectLine);
			}else{
				LOG.debug("{}为用户{}更新工作酒店信息{} > {} > {} {}条",loginAccount, staffId, hotelId, dptId, postId, effectLine);
			}
		}else if(isDelFlag == 1){
			//不删除原默认工作酒店的登录权限
			//nothing to do
		}
		//职位信息有变更
		if(StringUtils.isNotBlank(postId) && !StringUtils.equals(postBefore, postId)){
			//1、插入新的职位关联的工作圈和角色信息（判断是否已经存在，如果存在仅更新）
			PermsTPostResponsibility permsTPostResponsibility = permsTPostResponsibilityMapper.selectByPrimaryKey(postId);
			String defaultTeam = permsTPostResponsibility.getClDefaultTeam();
			if(StringUtils.isNoneBlank(defaultTeam)){
				PermsTStaffTeamExample staffTeamExample = new PermsTStaffTeamExample();
				PermsTStaffTeamExample.Criteria criteria2 = staffTeamExample.createCriteria();
				criteria2.andClTeamIdEqualTo(defaultTeam);
				criteria2.andClStaffIdEqualTo(staffId);
				criteria2.andClHotelIdEqualTo(hotelId);
				
				PermsTStaffTeam staffTeam = new PermsTStaffTeam();
				staffTeam.setClStatus(Constants.STATUS_NORMAL);
				staffTeam.setClModifyBy(loginAccount);
				
				effectLine = permsTStaffTeamMapper.updateByExampleSelective(staffTeam, staffTeamExample);
				if(effectLine == 0){
					staffTeam.setClTeamId(defaultTeam);
					staffTeam.setClStaffId(staffId);
					staffTeam.setClHotelId(hotelId);
					staffTeam.setClCreateBy(loginAccount);
					staffTeam.setClGrpId(staffUpdateBefore.getClGrpId());
					staffTeam.setClId(HashUtils.uuidGenerator());
					effectLine = permsTStaffTeamMapper.insertSelective(staffTeam);
					LOG.debug("{}插入酒店{}员工{}工作圈{}信息{}条",loginAccount,hotelId, staffId, defaultTeam, effectLine);
				}else{
					LOG.debug("{}更新酒店{}员工{}工作圈{}信息{}条",loginAccount,hotelId, staffId, defaultTeam, effectLine);
				}
			}
			
			String[] roleIds = permsTPostResponsibility.getClDefaultRole();
			if(roleIds != null && roleIds.length > 0){
				List<PermsTStaffRole> addList = new ArrayList<>();
				PermsTStaffRoleExample staffRoleExample = new PermsTStaffRoleExample();
				PermsTStaffRoleExample.Criteria criteria2;
				for (String roleId : roleIds) {
					criteria2 = staffRoleExample.createCriteria();
					criteria2.andClStaffIdEqualTo(staffId);
					criteria2.andClHotelIdEqualTo(hotelId);
					PermsTStaffRole staffRole = new PermsTStaffRole();
					staffRole.setClStatus(Constants.STATUS_NORMAL);
					staffRole.setClModifyBy(loginAccount);
					criteria2.andClRoleIdEqualTo(roleId);
					effectLine = permsTStaffRoleMapper.updateByExampleSelective(staffRole, staffRoleExample);
					LOG.debug("{}更新用户{}在酒店{}的角色{}信息{}条",loginAccount, staffId, hotelId, roleId, effectLine);
					if(effectLine == 0){
						staffRole.setClId(HashUtils.uuidGenerator());
						staffRole.setClStaffId(staffId);
						staffRole.setClGrpId(LoginInfoHolder.getLoginGrpId());
						staffRole.setClHotelId(hotelId);
						staffRole.setClCreateBy(loginAccount);
						staffRole.setClRoleId(roleId);
						addList.add(staffRole);
					}
				}
				if(addList.size() > 0){
					effectLine = permsTStaffRoleMapper.batchInsertSelective(addList);
					LOG.debug("{}给用户{}工作酒店{}插入角色信息{}条：{}",loginAccount, staffId, hotelId, effectLine, JSON.toJSON(addList));
				}
			}
		}
		
		staff.setModifyTime(new Date());
		staff.setHotelId(hotelId);
		staff.setModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
//		验证账号密码是否更改，是否需要重新加密；
		String salt = staff.getSalt();
		String cashPwd = staff.getCashPwd();
		if (StringUtils.isNotBlank(cashPwd)) {
			String cashPwdMd5 = HashUtils.stringMd5Encode(cashPwd, salt);
			staff.setCashPwd(cashPwdMd5);
		}
			
		setSelInfo(staff);
		
//		更改员工默认工作酒店信息；
		permsTStaffMapper.updateByPrimaryKeySelective(convertStaff2PermsTStaff(staff));
		
		if (staff.getId() == null) {
			return Result.ERROR_SYS;
		}else {
			Result sucResult = Result.SUCCESS;
			sucResult.setMessage("保存成功!");
			sucResult.setResult(staff.getId());
			return sucResult;
		}
	}


	/**
	 * 删除员工
	 * 1、删除staff信息
	 * 2、删除staff_hotel
	 * 3、删除staff_role
	 * 4、删除staff_team
	 * @param staff
	 */
	@Override
	public Result deleteStaff(@RequestBody Staff staff,String hotelId) {

		//TODO 如果是删除非默认酒店的员工，那么仅仅删除该酒店员工和关联信息，
		String staffId = staff.getId();
		PermsTStaff permsTStaff = permsTStaffMapper.selectByPrimaryKey(staffId);
		String loginAccout = LoginInfoHolder.getLoginAccount();
		String defualtHotelId = permsTStaff.getClDefaultHotelId();
		
		
		boolean isDelAll = false;
		Date delDate = new Date();
		int effectLine = 0;
		
		try {
//			如果是删除默认酒店的员工那么全部信息删除
			//删除员工信息
			if(StringUtils.equals(hotelId, defualtHotelId)){
				permsTStaff.setClStatus(Constants.STATUS_DEL);
				permsTStaff.setClModifyBy(loginAccout);
				permsTStaff.setClModifyTime(delDate);
				effectLine = permsTStaffMapper.updateByPrimaryKeySelective(permsTStaff);
				LOG.debug("{}删除员工{}信息{}条",loginAccout, staffId, effectLine);
				isDelAll = true;
				
				// add by 刘溶容 2018/01/11 for 集成环信 begin
				// 获取当前用户所属的所有工作圈ID--》群组ID
				Map<String, Object> paramMap = new HashMap<String, Object>();
				List<String> groupIdList = new ArrayList<String>();
				paramMap.put("id", staff.getId());
				groupIdList = permsTStaffMapper.getGroupIdList(paramMap);
				if (groupIdList != null && groupIdList.size() > 0) {
					for (String groupId : groupIdList) {
						chatGroupAPI.removeSingleUserFromChatGroup(groupId, staff.getId());
					}
				}
				// add by 刘溶容 2018/01/11 for 集成环信 end
			}
			
			//删除工作酒店关联信息
			PermsTStaffHotel staffHotel = new PermsTStaffHotel();
			staffHotel.setClStatus(Constants.STATUS_DEL);
			staffHotel.setClModifyBy(loginAccout);
			staffHotel.setClModifyTime(delDate);
			PermsTStaffHotelExample staffHotelExample = new PermsTStaffHotelExample();
			PermsTStaffHotelExample.Criteria criteria = staffHotelExample.createCriteria();
			criteria.andClStaffIdEqualTo(staffId);
			if(!isDelAll){
				criteria.andClWorkHotelIdEqualTo(hotelId);
			}
			effectLine = permsTStaffHotelMapper.updateByExampleSelective(staffHotel, staffHotelExample);
			LOG.debug("{}删除员工{}工作酒店信息",loginAccout, staffId);
			
			//删除角色信息
			PermsTStaffRole staffRole = new PermsTStaffRole();
			staffRole.setClStatus(Constants.STATUS_DEL);
			staffRole.setClModifyBy(loginAccout);
			staffRole.setClModifyTime(delDate);
			PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
			PermsTStaffRoleExample.Criteria staffRoleCriteria = permsTStaffRoleExample.createCriteria();
			staffRoleCriteria.andClStaffIdEqualTo(staffId);
			if(!isDelAll){
				staffRoleCriteria.andClHotelIdEqualTo(hotelId);
			}
			effectLine = permsTStaffRoleMapper.updateByExampleSelective(staffRole, permsTStaffRoleExample);
			LOG.debug("{}删除员工{}角色信息{}条",loginAccout, staffId, effectLine);
			
			//删除工作圈
			PermsTStaffTeam staffTeam = new PermsTStaffTeam();
			staffTeam.setClStatus(Constants.STATUS_DEL);
			staffTeam.setClModifyBy(loginAccout);
			staffTeam.setClModifyTime(delDate);
			PermsTStaffTeamExample staffTeamExample = new PermsTStaffTeamExample();
			PermsTStaffTeamExample.Criteria staffTeamCriteria = staffTeamExample.createCriteria();
			staffTeamCriteria.andClStaffIdEqualTo(staffId);
			if(!isDelAll){
				staffTeamCriteria.andClHotelIdEqualTo(hotelId);
			}
			effectLine = permsTStaffTeamMapper.updateByExampleSelective(staffTeam, staffTeamExample);
			
			// add by 刘溶容 for 集成环信 2018/01/11 begin
			// 查询当前酒店 该用户所属的工作圈-->群组ID
			if (!StringUtils.equals(hotelId, defualtHotelId)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("hotelId", hotelId);
				paramMap.put("id", staff.getId());
				List<String> groupIdList = permsTStaffMapper.getGroupIdListByHotelId(paramMap);
				if (groupIdList != null && groupIdList.size() > 0) {
					for (String groupId : groupIdList) {
						chatGroupAPI.removeSingleUserFromChatGroup(groupId, staff.getId());
					}
				}
			}
			
			// add by 刘溶容 for 集成环信 2018/01/11 end
			
			LOG.debug("{}删除员工{}工作圈信息{}条",loginAccout, staffId, effectLine);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR_SYS;
		}
		return Result.SUCCESS;
	}

	/**
	 * 修改信息校验
	 * @param staff
	 * @param permsTStaffPast
	 */
	private Result staffValidata(Staff staff, PermsTStaff staffUpdateBefore) {
		Result repeatResult = null;
		
//		证件是否修改 ？ 修改后的信息是否被存在 ： 不操作；
		String pastCardTypeId = staffUpdateBefore.getClCardTypeId();
		String pastCardNo = staffUpdateBefore.getClCardNo();
		String nowCarTypeId = staff.getCardTypeId();
		if (StringUtils.isNotBlank(nowCarTypeId) && nowCarTypeId.contains("+")) {
			nowCarTypeId = nowCarTypeId.split("[+]")[0];
		}
		String nowCardNo = staff.getCardNo();
		if (StringUtils.isNotBlank(nowCardNo) && StringUtils.isNotBlank(nowCarTypeId)
				&& StringUtils.isNotBlank(pastCardNo)
				&& ((!pastCardTypeId.equals(nowCarTypeId)) || (!pastCardNo.equals(nowCardNo)))) {
			if (isCarInfoExist(nowCarTypeId, nowCardNo) > 0) {
				repeatResult = Result.ERROR_REPEAT;
				repeatResult.setMessage("该证件号已被注册！");
				return repeatResult;
			}
		}
		
		
//		手机号是否修改 ？ 修改过后的手机号是否已被注册 ： 不操作；
		String pastMobilePhone = staffUpdateBefore.getClMobilePhone();
		String nowMobilePhone = staff.getMobilePhone();
		if (StringUtils.isNotBlank(nowMobilePhone) && StringUtils.isNotBlank(pastMobilePhone) && !pastMobilePhone.equals(nowMobilePhone)) {
			if (isMobilePhoneExist(nowMobilePhone) > 0) {
				repeatResult = Result.ERROR_REPEAT;
				repeatResult.setMessage("该手机号已被注册！");
				return repeatResult;
			}
		}
		return repeatResult;
	}

	@Override
	public Result insertBackId(@RequestBody Staff staff) throws AppException {
		staffInfoTrim(staff);
//		验证证件号是否唯一；
		String cardTypeId = staff.getCardTypeId();
		String cardNo = staff.getCardNo();
		if (StringUtils.isNotBlank(cardTypeId) && StringUtils.isNotBlank(cardNo)) {
			if (isCarInfoExist(cardTypeId, cardNo) > 0) {
				Result paramsResult = Result.ERROR_REPEAT;
				paramsResult.setMessage("该证件号已被注册！");
				return paramsResult;
			}
		}
//		验证手机号是否唯一；
		String mobilePhone = staff.getMobilePhone();
		if (StringUtils.isNotBlank(mobilePhone)) {
			if (isMobilePhoneExist(mobilePhone) > 0) {
				Result paramsResult = Result.ERROR_REPEAT;
				paramsResult.setMessage("该手机号已被注册！");
				return paramsResult;
			}
		}
//		账号重复
		int size = isRepeat(staff);
		if (size > 0) {
			Result repeatResult = Result.ERROR_REPEAT;
			repeatResult.setMessage("该账号已经存在！");
			return repeatResult;
		}
		
		Staff loginStaff = LoginInfoHolder.getLoginInfo();
		Short status = staff.getStatus();
		if (status == null) {
			staff.setStatus(Constants.STATUS_NORMAL);
		}
		staff.setGrpId(loginStaff.getGrpId());
		staff.setHotelId(staff.getDefaultHotelId());
		staff.setSalt(HashUtils.uuidGenerator());
		String staffId = HashUtils.uuidGenerator();
		staff.setId(staffId);
		String salt = HashUtils.uuidGenerator();
		staff.setSalt(salt);
		String cashPwd = staff.getCashPwd();
		String password = staff.getPassword();
		if (StringUtils.isBlank(password)) {
			staff.setPassword(HashUtils.stringMd5Encode(defaultPwd, salt));
		}
		if (StringUtils.isNotBlank(cashPwd)) {
			String passwordMd5 = HashUtils.stringMd5Encode(cashPwd, salt);
			staff.setCashPwd(passwordMd5);
		}
		
		setSelInfo(staff);
//		插入信息至PERMS_T_STAFF主表；
		permsTStaffMapper.insertSelective(convertStaff2PermsTStaff(staff));
		
		String hotelId = staff.getDefaultHotelId();
		String dptId = staff.getDefaultDptId();
		String postId = staff.getDefaultPostId();
		if (StringUtils.isNotBlank(hotelId) && StringUtils.isNotBlank(dptId) && StringUtils.isNotBlank(postId)) {
//			保存工作酒店信息
			insertWorkHotelInfo(staff);
		}
//		保存职位下默认角色信息；
		insertPermsTPostResponsibilityByPostId(staff, postId);
		
		insertDptStaff(staff);
		
		// 保存成功；
		// add by 刘溶容 for 环信注册 2018/01/08 begin
		User registerUser = new User();
		registerUser.setUsername(staff.getId());
		registerUser.setPassword(HashUtils.encodeForHuanXin(staff.getId()));
		imUserAPI.createNewIMUserSingle(registerUser);
		Nickname nickName = new Nickname();
		String userNickName = StringUtils.EMPTY;
		if (!StringUtils.isEmpty(staff.getFamilyName())) {
			userNickName += staff.getFamilyName();
		}
		if (!StringUtils.isEmpty(staff.getName())) {
			userNickName += staff.getName();
		}
		nickName.setNickname(userNickName);
		imUserAPI.modifyIMUserNickNameWithAdminToken(staff.getId(), nickName);
		// add by 刘溶容 for 环信注册 2018/01/08 end
		
		Result sucResult = Result.SUCCESS;
		sucResult.setMessage("保存成功!");
		sucResult.setResult(staff.getId());
		return sucResult;
	}
/**
 * 验证手机号是否唯一；
 * @param mobilePhone
 * @return
 */
	private int isMobilePhoneExist(String mobilePhone) {
		PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria pStaffCriteria = permsTStaffExample.createCriteria();
		pStaffCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffCriteria.andClMobilePhoneEqualTo(mobilePhone);
		return permsTStaffMapper.countByExample(permsTStaffExample);
	}
/**
 * 验证证件号是否唯一；
 * @param cardTypeId
 * @param cardNo
 * @return
 */
	private int isCarInfoExist(String cardTypeId, String cardNo) {
		PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria pStaffCriteria = permsTStaffExample.createCriteria();
		pStaffCriteria.andClCardNoEqualTo(cardNo);
		pStaffCriteria.andClCardTypeIdEqualTo(cardTypeId);
		pStaffCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		return permsTStaffMapper.countByExample(permsTStaffExample);
	}
/*
 * 保存工作酒店信息
 */
	private void insertWorkHotelInfo(Staff staff) {
		PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
		permsTStaffHotel.setClId(HashUtils.uuidGenerator());
		permsTStaffHotel.setClStaffId(staff.getId());
		permsTStaffHotel.setClBlockup(Constants.BLOCKUP_NO);
		permsTStaffHotel.setClWorkHotelId(staff.getDefaultHotelId());
		permsTStaffHotel.setClDptId(staff.getDefaultDptId());
		permsTStaffHotel.setClHotelId(staff.getDefaultHotelId());
		permsTStaffHotel.setClPostId(staff.getDefaultPostId());
		permsTStaffHotel.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
		permsTStaffHotel.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
		permsTStaffHotelMapper.insertSelective(permsTStaffHotel);
	}
/**
 *	保存职位下默认角色信息；
 * @param staff
 * @param postId
 */
	private void insertPermsTPostResponsibilityByPostId(Staff staff, String postId) {
		PermsTPostResponsibility permsTPostResponsibility = permsTPostResponsibilityMapper.selectByPrimaryKey(postId);
		if (permsTPostResponsibility != null) {
			String[] roleIDArr = permsTPostResponsibility.getClDefaultRole();
			if (roleIDArr != null) {
				for (String roleId : roleIDArr) {
					PermsTStaffRole permsTStaffRole = new PermsTStaffRole();
					permsTStaffRole.setClRoleId(roleId);
					permsTStaffRole.setClId(HashUtils.uuidGenerator());
					permsTStaffRole.setClStaffId(staff.getId());
					permsTStaffRole.setClStatus(Constants.STATUS_NORMAL);
					permsTStaffRole.setClBlockup(Constants.BLOCKUP_NO);
					permsTStaffRole.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
					permsTStaffRole.setClHotelId(staff.getDefaultHotelId());
					permsTStaffRole.setClCreateBy(LoginInfoHolder.getLoginInfo().getCreateBy());
					permsTStaffRoleMapper.insertSelective(permsTStaffRole);
				}
			}
		}
	}
/*
 * 去除前后空格信息；
 */
	private void staffInfoTrim(Staff staff) {
		String account = staff.getAccount();
		if (StringUtils.isNotBlank(account)) {
			staff.setAccount(account.trim());
		}
		String name = staff.getName();
		if (StringUtils.isNotBlank(name)) {
			staff.setName(name.trim());
		}
		String nameEn = staff.getNameEn();
		if (StringUtils.isNotBlank(nameEn)) {
			staff.setNameEn(nameEn.trim());
		}
		String cashAccount = staff.getCashAccount();
		if (StringUtils.isNotBlank(cashAccount)) {
			staff.setCashAccount(cashAccount.trim());
		}
		String cashPwd = staff.getCashPwd();
		if (StringUtils.isNotBlank(cashPwd)) {
			staff.setCashPwd(cashPwd.trim());
		}
		String staffNo = staff.getStaffNo();
		if (StringUtils.isNotBlank(staffNo)) {
			staff.setStaffNo(staffNo.trim());
		}
		String cardNo = staff.getCardNo();
		if (StringUtils.isNotBlank(cardNo)) {
			staff.setCardNo(cardNo.trim());
		}
		String email = staff.getEmail();
		if (StringUtils.isNotBlank(email)) {
			staff.setEmail(email.trim());
		}
		String contacts = staff.getContacts();
		if (StringUtils.isNotBlank(contacts)) {
			staff.setContacts(contacts.trim());
		}
		String mobilePhone = staff.getMobilePhone();
		if (StringUtils.isNotBlank(mobilePhone)) {
			staff.setMobilePhone(mobilePhone.trim());
		}
		String address = staff.getAddress();
		if (StringUtils.isNotBlank(address)) {
			staff.setAddress(address.trim());
		}
		String emergency1 = staff.getEmergency1();
		if (StringUtils.isNotBlank(emergency1)) {
			staff.setEmergency1(emergency1.trim());
		}
		String emergencyNo1 = staff.getEmergencyNo1();
		if (StringUtils.isNotBlank(emergencyNo1)) {
			staff.setEmergencyNo1(emergencyNo1.trim());
		}
		String emergency2 = staff.getEmergency2();
		if (StringUtils.isNotBlank(emergency2)) {
			staff.setEmergency2(emergency2.trim());
		}
		String emergencyNo2 = staff.getEmergencyNo2();
		if (StringUtils.isNotBlank(emergencyNo2)) {
			staff.setEmergencyNo2(emergencyNo2.trim());
		}
		String description = staff.getDescription();
		if (StringUtils.isNotBlank(description)) {
			staff.setDescription(description.trim());
		}
	}

	private void insertDptStaff(Staff staff) {
//		插入关联信息至PERMS_T_DPT_STAFF表；
		PermsTDptStaff permsTDptStaff = new PermsTDptStaff();
		permsTDptStaff.setClId(HashUtils.uuidGenerator());
		permsTDptStaff.setClStaffId(staff.getId());
		permsTDptStaff.setClStatus(Constants.STATUS_NORMAL);
		permsTDptStaff.setClBlockup(Constants.BLOCKUP_NO);
		permsTDptStaff.setClGrpId(staff.getGrpId());
		permsTDptStaff.setClDprId(staff.getDefaultDptId());
		permsTDptStaff.setClHotelId(staff.getDefaultHotelId());
		permsTDptStaff.setClCreateBy(LoginInfoHolder.getLoginInfo().getCreateBy());
		permsTDptStaffMapper.insertSelective(permsTDptStaff);
	}

	private int isRepeat(Staff staff) {
		String account = staff.getAccount();
		PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria pStaffCriteria = permsTStaffExample.createCriteria();
		pStaffCriteria.andClAccountEqualTo(account);
		pStaffCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		pStaffCriteria.andClIdNotEqualTo(staff.getId());
		int size = permsTStaffMapper.countByExample(permsTStaffExample);
		return size;
	}

	/**
	 * 员工信息设置
	 * @param staff
	 */
	private void setSelInfo(Staff staff) {
		Short status = staff.getStatus();
		if (status == null) {
			staff.setStatus(Constants.STATUS_NORMAL);
		}
		String nameCh = staff.getName();
		if (StringUtils.isNotBlank(nameCh)) {
			nameCh = nameCh.trim();
			NameResult nameChRes = NameUtils.splitName(nameCh);
			if (nameChRes != null) {
				String nameFirst = nameChRes.getSurname();
				if (StringUtils.isNotBlank(nameFirst)) {
					staff.setFamilyName(nameFirst.trim());
				}
				String nameSecond = nameChRes.getName();
				if (StringUtils.isNotBlank(nameSecond)) {
					staff.setName(nameSecond.trim());
				}
			}
		}
		String nameEn = staff.getNameEn();
		if (StringUtils.isNotBlank(nameEn)) {
			nameEn = nameEn.trim();
			if (nameEn.contains(" ")) {
				String[] nameEnArr = nameEn.split(" ");
				if (nameEnArr != null && nameEnArr.length > 0) {
					staff.setFmyEn(nameEnArr[nameEnArr.length - 1].trim());
					staff.setNameEn(nameEn.substring(0,nameEn.lastIndexOf(nameEnArr[nameEnArr.length - 1])).trim());
				}
			}
		}
		String countryInfo = staff.getCountryId();
		if (StringUtils.isNotBlank(countryInfo)) {
			String[] countryArr = countryInfo.split("[+]");
			staff.setCountryId(countryArr[0]);
			staff.setCountryCode(countryArr[1]);
		}
		String provinceInfo = staff.getProvinceId();
		if (StringUtils.isNotBlank(provinceInfo)) {
			String[] provinceArr = provinceInfo.split("[+]");
			staff.setProvinceId(provinceArr[0]);
			staff.setProvinceCode(provinceArr[1]);
		}
		String cityInfo = staff.getCityId();
		if (StringUtils.isNotBlank(cityInfo)) {
			String[] cityArr = cityInfo.split("[+]");
			staff.setCityId(cityArr[0]);
			staff.setCityCode(cityArr[1]);
		}
		String degreesInfo = staff.getDegreesId();
		if (StringUtils.isNotBlank(degreesInfo)) {
			String[] degreesArr = degreesInfo.split("[+]");
			staff.setDegreesId(degreesArr[0]);
			staff.setDegreesCode(degreesArr[1]);
		}
		String cardTypeInfo = staff.getCardTypeId();
		if (StringUtils.isNotBlank(cardTypeInfo)) {
			String[] cadrdTypeArr = cardTypeInfo.split("[+]");
			staff.setCardTypeId(cadrdTypeArr[0]);
			staff.setCardTypeCode(cadrdTypeArr[1]);
		}
	}

/*
 * 获取组织架构信息；	
 */
	public String getOrgInfo(String dpartId){
		String organization = "";
		PermsTDepartment permsTDepartment = permsTDepartmentMapper.selectByPrimaryKey(dpartId);
		if(permsTDepartment != null){
			organization = organization + permsTDepartment.getClName();
			String pid = permsTDepartment.getClPid();
			while(StringUtils.isNotBlank(pid)) {
				permsTDepartment = permsTDepartmentMapper.selectByPrimaryKey(pid);
				pid = permsTDepartment.getClPid();
				organization =  permsTDepartment.getClName() + Constants.ORG_SPLIT + organization;
			}
			String hotelId = permsTDepartment.getClHotelId();
			PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelId);
			if(permsTGroupHotel != null){
				organization =  permsTGroupHotel.getClName() + Constants.ORG_SPLIT + organization;
				String hotelPid = permsTGroupHotel.getClPid();
				while(StringUtils.isNotBlank(hotelPid)){
					permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelPid);
					hotelPid = permsTGroupHotel.getClPid();
					organization = permsTGroupHotel.getClName() + Constants.ORG_SPLIT + organization;
				}
			}
		}
		return organization;
	}
/*
 * (non-Javadoc)获取员工管理信息
 * @see com.sstc.hmis.permission.service.EmployeeService#getEmployeeInfo(java.lang.String)
 */
	@Override
	public Staff getEmployeeInfo(String id) {
		PermsTStaff permsTStaff = permsTStaffMapper.selectByPrimaryKey(id);
		String familyName = permsTStaff.getClFamilyName();
		String name = permsTStaff.getClName();
		if (StringUtils.isNotBlank(familyName) && StringUtils.isNotBlank(name)) {
			permsTStaff.setClName(familyName + name);
		}
		permsTStaff.setClDegreesId(permsTStaff.getClDegreesId() + "+" + permsTStaff.getClDegreesCode());
		permsTStaff.setClCardTypeId(permsTStaff.getClCardTypeId() + "+" + permsTStaff.getClCardTypeCode());
//		设置默认工作酒店下拉列表的级联信息；
		Staff staff = convertPermsTStaff2Staff(permsTStaff);
		return staff;
	}
	
	@Override
	public PageResult<Staff> pageList(int index, int size, Short nodeType, String orgId, Short status, String info,String teamId,String roleId) throws AppException {
		PageResult<Staff> pageResult = new PageResult<Staff>();
		PageHelper.startPage(index+1, size);
		PermsTQuery pTQuery = new PermsTQuery();
		if (StringUtils.isNotBlank(teamId)) {
			pTQuery.setClTeamId(teamId); 
		}
		if(nodeType != null && StringUtils.isNotBlank(orgId)){
			if (nodeType == Constants.NODE_TYPE_ORG || nodeType == Constants.NODE_TYPE_HOTEL) {
				pTQuery.setClHotelId(orgId);
			}else if (nodeType == Constants.NODE_TYPE_DPT) {
				pTQuery.setClDpartID(orgId);
			}else if (nodeType == Constants.NODE_TYPE_POST){
				pTQuery.setClPostId(orgId);
			}
		}
		if (StringUtils.isNotBlank(roleId)) {
			pTQuery.setClRoleId(roleId);
		}
		pTQuery.setClStatus(status);
		if (StringUtils.isNotBlank(info)) {
			info = info.trim();
			pTQuery.setClName(info);
		}
		if(StringUtils.equals(orgId, "-1")){
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			List<Staff> staffList = new ArrayList<>();
			for (PermsTStaff permsTStaff : list) {
				Staff staff = convertPermsTStaff2Staff(permsTStaff);
				staffSetAllName(staff);

				PermsTStaff db_permsTStaff = permsTStaffMapper.selectByPrimaryKey(permsTStaff.getClId());
				PermsTStaff permsTStaffCondition = new PermsTStaff();
				permsTStaffCondition.setClId(permsTStaff.getClId());
				permsTStaffCondition.setClDefaultHotelId(db_permsTStaff.getClHotelId());
				List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaffCondition);
				String orgInfo = "";
				if (orgInfoList != null && orgInfoList.size() > 0) {
					orgInfo = orgInfoList.get(0);
				}
				staff.setOrgInfo(orgInfo);
				staffList.add(staff);
			}
			Page<PermsTStaff> page = (Page<PermsTStaff>) list;
			pageResult.setResult(true);
			pageResult.setResults(page.getTotal());
			pageResult.setRows(staffList);
		}else {
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			List<Staff> staffList = new ArrayList<>();
			for (PermsTStaff permsTStaff : list) {
				Staff staff = convertPermsTStaff2Staff(permsTStaff);
				staffSetAllName(staff);
				List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaff);
				String orgInfo = "";
				if (orgInfoList != null && orgInfoList.size() > 0) {
					orgInfo = orgInfoList.get(0);
				}
				staff.setOrgInfo(orgInfo);
				staffList.add(staff);
			}
			Page<PermsTStaff> page = (Page<PermsTStaff>) list;
			pageResult.setResult(true);
			pageResult.setResults(page.getTotal());
			pageResult.setRows(staffList);
		}
		
		return pageResult;
	}
	

	@Override
	public PageResult<Staff> listSel(int index, int size, Short nodeType, String orgId, Short statusVal, String infoVal,String postId2,String teamId,String orgIdTab,Short nodeTypeTab)
			throws AppException {
		PageResult<Staff> pageResult = new PageResult<Staff>();
		PageHelper.startPage(index+1, size);
		PermsTQuery pTQuery = new PermsTQuery();
		if (StringUtils.isNotBlank(postId2)) {
			pTQuery.setClPostId2(postId2);
		};
		if (StringUtils.isNotBlank(teamId)) {
			pTQuery.setClTeamId(teamId); 
		}
		if(nodeType != null && StringUtils.isNotBlank(orgId)){
			if (nodeType == Constants.NODE_TYPE_ORG || nodeType == Constants.NODE_TYPE_HOTEL) {
				pTQuery.setClHotelId(orgId);
			}else if (nodeType == Constants.NODE_TYPE_DPT) {
				pTQuery.setClDpartID(orgId);
			}else if (nodeType == Constants.NODE_TYPE_POST){
				pTQuery.setClPostId(orgId);
			}
		}
		if (nodeTypeTab == null || StringUtils.isBlank(orgIdTab)) {
			String defaultHotelId = LoginInfoHolder.getLoginInfo().getDefaultHotelId();
			pTQuery.setClHotelIdTab(defaultHotelId);
		}
		if (nodeTypeTab != null && StringUtils.isNotBlank(orgIdTab)) {
			if (nodeTypeTab == Constants.NODE_TYPE_ORG || nodeTypeTab == Constants.NODE_TYPE_HOTEL) {
				pTQuery.setClHotelIdTab(orgIdTab);
			}else if (nodeTypeTab == Constants.NODE_TYPE_DPT) {
				pTQuery.setClDpartIDTab(orgIdTab);
			}else if (nodeTypeTab == Constants.NODE_TYPE_POST){
				pTQuery.setClPostIdTab(orgIdTab);
			}
		}
		
		pTQuery.setClStatus(statusVal);
		
		if (StringUtils.isNotBlank(infoVal)) {
			String infoValTrm = infoVal.trim();
			infoValTrm = infoValTrm.trim();
			pTQuery.setClName(infoValTrm);
		}
		List<PermsTStaff> permsTStaffList = permsTStaffMapper.selStaffInfos(pTQuery);
		List<Staff> staffList = new ArrayList<>();
		for (PermsTStaff permsTStaff : permsTStaffList) {
			Staff staff = convertPermsTStaff2Staff(permsTStaff);
			staffSetAllName(staff);
			List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaff);
			String orgInfo = ""; 
			if (orgInfoList != null && orgInfoList.size() > 0) {
				orgInfo = orgInfoList.get(0);
			}
			if (StringUtils.isNotBlank(orgInfo)) {
				staff.setOrgInfo(orgInfo);
			}
			staffList.add(staff);
		}
		Page<PermsTStaff> page = (Page<PermsTStaff>) permsTStaffList;
		pageResult.setResult(true);
		pageResult.setResults(page.getTotal());
		pageResult.setRows(staffList);
		return pageResult;
	}
/*
 * 拼装Staff名字
 */
	private void staffSetAllName(Staff staff) {
		String familyName = staff.getFamilyName();
		String name = staff.getName();
		String fmyEn = staff.getFmyEn();
		String nameEn = staff.getNameEn();
		if (StringUtils.isNotBlank(familyName) && StringUtils.isNotBlank(name)) {
			staff.setName(familyName + name);
		}
		if (StringUtils.isNotBlank(fmyEn) && StringUtils.isNotBlank(nameEn)) {
			staff.setNameEn(fmyEn + nameEn);
		}
	}

	@Override
	public int insert(@RequestBody Staff t) throws AppException {
		PermsTStaff record = new PermsTStaff();
		try {
			record = BeanUtils.copyServiceBean2DbBean(t, record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permsTStaffMapper.insert(record);
	}

	@Override
	public int update(@RequestBody Staff t) throws AppException {
		PermsTStaff record = new PermsTStaff();
		try {
			record = BeanUtils.copyServiceBean2DbBean(t, record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permsTStaffMapper.updateByPrimaryKey(record);
	}

	@Override
	public Map<String, String> getWorkHotelInfos(String hotelIdVal) {
		Map<String, String> hotelLoginMap = new HashMap<String, String>();
		PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelIdVal);
		String hotelName = permsTGroupHotel.getClName();
		hotelLoginMap.put(hotelIdVal, hotelName);
		return hotelLoginMap;
	}
	
	/**
	 * (non-Javadoc)保存登陆策略信息；
	 */
	@Override
	public int saveStrateryInfo(@RequestBody StaffHotel staffHotel) {
		String staffIds = staffHotel.getStaffId();
		String workHotelId = staffHotel.getWorkHotelId();
		String[] staffIdArray = staffIds.split("\\+");
		Map<String,Object> condition = new HashMap<>(2);
		condition.put("staffIds", staffIdArray);
		condition.put("workHotelId", workHotelId);
		String[] weeks = staffHotel.getWeek();
		if(ArrayUtils.isEmpty(weeks)){
			staffHotel.setWeek(null);
		}
		Short isLimit = staffHotel.getIsLimit();
		if(isLimit == null){
			staffHotel.setIsLimit(Constants.LOGIN_LIMIT);
		}
		
		String[] timeInterval = staffHotel.getTimeInterval();
		if(ArrayUtils.isEmpty(timeInterval) || isAllBlank(timeInterval)){
			staffHotel.setTimeInterval(null);
		}
		staffHotel.setModifyBy(LoginInfoHolder.getLoginAccount());
		staffHotel.setModifyTime(new Date());
		PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
		try {
			BeanUtils.copyServiceBean2DbBean(staffHotel, permsTStaffHotel);
			return permsTStaffHotelMapper.batchUpdateStratery(permsTStaffHotel,condition);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
			
	}
	
	/**
	 * @param timeInterval
	 * @return
	 */
	private boolean isAllBlank(String[] timeInterval) {
		for (String string : timeInterval) {
			if(StringUtils.isNoneBlank(string)){
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据酒店Id和员工Id动态获取登陆策略值；	
	 */	
	public List<PermsTStaffHotel> getPermsStaffHotelInfo(String staffId, String workHotelId) {
		PermsTStaffHotelExample permsTStaffHotelExample = new PermsTStaffHotelExample();
		Criteria pStaffHotelriteria = permsTStaffHotelExample.createCriteria();
		pStaffHotelriteria.andClStaffIdIn(Arrays.asList(staffId.split("\\+")));
		pStaffHotelriteria.andClWorkHotelIdEqualTo(workHotelId);
		pStaffHotelriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffHotelriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		return permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample);
	}
	
	@Override
	public List<StaffHotel> getStaffHotelInfo(String staffId, String workHotelId) {
		List<PermsTStaffHotel> permsTStaffHotelList = getPermsStaffHotelInfo(staffId,workHotelId);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTStaffHotelList, StaffHotel.class);
	}
	
	/**
	 * 根据职位Id和员工Id保存工作酒店信息；	
	 */
	@Override
	public int savedWorkHotel(String[] postIdArr, String staffVal) {
		PermsTStaffHotelExample permsTStaffHotelExample = new PermsTStaffHotelExample();
		Criteria pStaffHotelCriteria = permsTStaffHotelExample.createCriteria();
		pStaffHotelCriteria.andClStaffIdEqualTo(staffVal);
		pStaffHotelCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		List<PermsTStaffHotel> permsTStaffHotelListPast = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample);
		List<String> postIdListNow = Arrays.asList(postIdArr);

		Iterator<PermsTStaffHotel> permsTStaffHotelItreatorPast = permsTStaffHotelListPast.iterator(); 
		while (permsTStaffHotelItreatorPast.hasNext()) {
			PermsTStaffHotel permsTStaffHotelPast = (PermsTStaffHotel) permsTStaffHotelItreatorPast.next();
			for(int i = 0; i < postIdListNow.size(); i++){
				String postId = postIdListNow.get(i);
				if (permsTStaffHotelPast != null && StringUtils.isNotBlank(postId) && postId.equals(permsTStaffHotelPast.getClPostId())) {
					permsTStaffHotelItreatorPast.remove();
//					postIdListNow.set(i, "");
				}
			}
		}
		
//		筛选过后permsTStaffHotelListPast存在的是需要删除的酒店值；
		if (permsTStaffHotelListPast != null && permsTStaffHotelListPast.size() > 0) {
			List<String> dptList=new ArrayList<String>();
			List<String> hotelList= new ArrayList<String>();
			for (PermsTStaffHotel permsTStaffHotel : permsTStaffHotelListPast) {
				String dptId= permsTStaffHotel.getClDptId();
				if(!dptList.contains(dptId)){
					dptList.add(dptId);
				}
				String hotelId= permsTStaffHotel.getClHotelId();
				if(!hotelList.contains(hotelId)){
					hotelList.add(hotelId);
				}
				
				permsTStaffHotel.setClStatus(Constants.STATUS_DEL);
				permsTStaffHotel.setClModifyTime(new Date());
				permsTStaffHotel.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTStaffHotelMapper.updateByPrimaryKeySelective(permsTStaffHotel);
//				删掉所有关联的角色信息和工作圈信息，之后再新建；
				PermsTPostResponsibility permsTPostResponsibility = permsTPostResponsibilityMapper.selectByPrimaryKey(permsTStaffHotel.getClPostId());
				String[] roleIdArr = permsTPostResponsibility.getClDefaultRole();
				if (roleIdArr != null && roleIdArr.length > 0) {
					for (String roleId : roleIdArr) {
						PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
						com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria pStaffRoleCriteria = permsTStaffRoleExample.createCriteria();
						pStaffRoleCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
						pStaffRoleCriteria.andClStaffIdEqualTo(staffVal);
						pStaffRoleCriteria.andClRoleIdEqualTo(roleId);
						List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
						if (permsTStaffRoleList != null && permsTStaffRoleList.size() > 0) {
							for (PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
								permsTStaffRole.setClStatus(Constants.STATUS_DEL);
								permsTStaffRole.setClModifyTime(new Date());
								permsTStaffRole.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
								permsTStaffRoleMapper.updateByPrimaryKeySelective(permsTStaffRole);
							}
						}
					}
				}
				String teamId = permsTPostResponsibility.getClDefaultTeam();
				if (StringUtils.isNotBlank(teamId)) {
					PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
					com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = permsTStaffTeamExample.createCriteria();
					pStaffTeamCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
					pStaffTeamCriteria.andClStaffIdEqualTo(staffVal);
					pStaffTeamCriteria.andClTeamIdEqualTo(teamId);
					List<PermsTStaffTeam> permsTStaffTeamList = permsTStaffTeamMapper.selectByExample(permsTStaffTeamExample);
					if (permsTStaffTeamList != null && permsTStaffTeamList.size() > 0) {
						for (PermsTStaffTeam permsTStaffTeam : permsTStaffTeamList) {
							permsTStaffTeam.setClStatus(Constants.STATUS_DEL);
							permsTStaffTeam.setClModifyTime(new Date());
							permsTStaffTeam.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
							permsTStaffTeamMapper.updateByPrimaryKeySelective(permsTStaffTeam);
						}
					}
				}
			}
			PermsTDptStaffExample permsTDptStaffExample = new PermsTDptStaffExample();
			for(String hotelId :hotelList){
				com.sstc.hmis.permission.dbaccess.data.PermsTDptStaffExample.Criteria dptCriteria= permsTDptStaffExample.createCriteria();
				dptCriteria.andClStaffIdEqualTo(staffVal);
				dptCriteria.andClDprIdIn(dptList);
				dptCriteria.andClHotelIdEqualTo(hotelId);
				List<PermsTDptStaff> permsTDptStaffList= permsTDptStaffMapper.selectByExample(permsTDptStaffExample);
				
				if (permsTDptStaffList != null && permsTDptStaffList.size() > 0) {
					for(PermsTDptStaff permsTDptStaff : permsTDptStaffList){
						PermsTStaffHotelExample permsTStaffHotelExample2 = new PermsTStaffHotelExample();
						Criteria pStaffHotelCriteria2 = permsTStaffHotelExample2.createCriteria();
						pStaffHotelCriteria2.andClStatusEqualTo(Constants.STATUS_NORMAL);
						pStaffHotelCriteria2.andClStaffIdEqualTo(staffVal);
						pStaffHotelCriteria2.andClDptIdEqualTo(permsTDptStaff.getClDprId());
						List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample2);
						
						if (permsTStaffHotelList == null || permsTStaffHotelList.size() == 0) {
							permsTDptStaff.setClStatus(Constants.STATUS_DEL);
							permsTDptStaff.setClModifyTime(new Date());
							permsTDptStaff.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
							permsTDptStaffMapper.updateByPrimaryKeySelective(permsTDptStaff);
						}
						
					}
				}
			}
		}
		
//		筛选过后permsTStaffHotelListNow存的是需要新建的酒店值；
		List<String> dptList= new ArrayList<String>();
		for (String postId : postIdListNow) {
			if (StringUtils.isNotBlank(postId)) {
				PermsTStaffHotelExample permsTStaffHotelExample2 = new PermsTStaffHotelExample();
				Criteria pStaffHotelCriteria2 = permsTStaffHotelExample2.createCriteria();
				pStaffHotelCriteria2.andClStatusNotEqualTo(Constants.STATUS_DEL);
				pStaffHotelCriteria2.andClStaffIdEqualTo(staffVal);
				pStaffHotelCriteria2.andClPostIdEqualTo(postId);
				List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample2);
				if (permsTStaffHotelList == null || permsTStaffHotelList.size() == 0) {
					
//					保存信息到PERMS_T_STAFF_HOTEL表中；
					PermsTStaffHotel permsTStaffHotelNew = new PermsTStaffHotel();
					PermsTDptStaff permsTDptStaff = new PermsTDptStaff();
//					查询PERMS_T_DPT_POST表获取职位关联的酒店/集团/部门信息；(这边赋值不能使用permsTPostResponsibility对象，缺少部门信息)
					PermsTDptPostExample permsTDptPostExample = new PermsTDptPostExample();
					com.sstc.hmis.permission.dbaccess.data.PermsTDptPostExample.Criteria pDptPostCriteria = permsTDptPostExample.createCriteria();
					pDptPostCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
					pDptPostCriteria.andClPostIdEqualTo(postId);
					List<PermsTDptPost> permsTDptPostList = permsTDptPostMapper.selectByExample(permsTDptPostExample);
					PermsTDptPost permsTDptPost = new PermsTDptPost();
					if (permsTDptPostList != null && permsTDptPostList.size() > 0) {
						permsTDptPost = permsTDptPostList.get(0);
						
						PermsTStaffHotelExample permsTStaffHotelExample3 = new PermsTStaffHotelExample();
						Criteria pStaffHotelCriteria3 = permsTStaffHotelExample3.createCriteria();
						pStaffHotelCriteria3.andClStatusEqualTo(Constants.STATUS_NORMAL);
						pStaffHotelCriteria3.andClStaffIdEqualTo(staffVal);
						pStaffHotelCriteria3.andClDptIdEqualTo(permsTDptPost.getClDptId());
						List<PermsTStaffHotel> permsTStaffHotelList2 = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample3);

						permsTStaffHotelNew.setClId(HashUtils.uuidGenerator());
						permsTStaffHotelNew.setClStaffId(staffVal);
						permsTStaffHotelNew.setClBlockup(Constants.BLOCKUP_NO);
						permsTStaffHotelNew.setClDptId(permsTDptPost.getClDptId());
						permsTStaffHotelNew.setClHotelId(permsTDptPost.getClHotelId());
						permsTStaffHotelNew.setClWorkHotelId(permsTDptPost.getClHotelId());
						permsTStaffHotelNew.setClPostId(postId);
						permsTStaffHotelNew.setClGrpId(permsTDptPost.getClGrpId());
						permsTStaffHotelNew.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
						//默认停用登录策略的
						permsTStaffHotelNew.setClIsLimit(Constants.LOGIN_UNLIMIT);
						//允许登录类型默认是允许
						permsTStaffHotelNew.setClLoginType(Constants.LOGIN_ALLOW);
						permsTStaffHotelMapper.insertSelective(permsTStaffHotelNew);
						
						if(!dptList.contains(permsTDptPost.getClDptId())){
							dptList.add(permsTDptPost.getClDptId());
							if (permsTStaffHotelList2 == null || permsTStaffHotelList2.size() == 0) {
								permsTDptStaff.setClId(HashUtils.uuidGenerator());
								permsTDptStaff.setClStaffId(staffVal);
								permsTDptStaff.setClDprId(permsTDptPost.getClDptId());
								permsTDptStaff.setClHotelId(permsTDptPost.getClHotelId());
								permsTDptStaff.setClGrpId(permsTDptPost.getClGrpId());
								permsTDptStaff.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
								permsTDptStaffMapper.insertSelective(permsTDptStaff);
							}
						}
					}
				}
				PermsTPostResponsibility permsTPostResponsibility = permsTPostResponsibilityMapper.selectByPrimaryKey(postId);
				if(permsTPostResponsibility != null){
//					查询默认角色/默认工作圈信息；
					String[] clDefaultRoleArr = permsTPostResponsibility.getClDefaultRole();
//					保存关联角色信息
					if (clDefaultRoleArr != null) {
						List<String> clDefaultRoleList = Arrays.asList(clDefaultRoleArr);
						PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
						com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria pStaffRoleCriteria = permsTStaffRoleExample.createCriteria();
						pStaffRoleCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
						pStaffRoleCriteria.andClRoleIdIn(clDefaultRoleList);
						pStaffRoleCriteria.andClStaffIdEqualTo(staffVal);
						List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
						if (permsTStaffRoleList != null && permsTStaffRoleList.size() > 0) {
							for (PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
								for(int i = 0; i < clDefaultRoleList.size(); i++){
									if (StringUtils.isNotBlank(clDefaultRoleList.get(i)) && StringUtils.isNotBlank(permsTStaffRole.getClRoleId())
											&& clDefaultRoleList.get(i).equals(permsTStaffRole.getClRoleId())) {
										clDefaultRoleList.set(i, "");
									}
								}
							}
						}
						for (String roleId : clDefaultRoleList) {
							if (StringUtils.isNotBlank(roleId)) {
								PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleId);
								PermsTStaffRole permsTStaffRole = new PermsTStaffRole();
								permsTStaffRole.setClId(HashUtils.uuidGenerator());
								permsTStaffRole.setClStaffId(staffVal);
								permsTStaffRole.setClRoleId(roleId);
								permsTStaffRole.setClStatus(Constants.STATUS_NORMAL);
								permsTStaffRole.setClBlockup(Constants.BLOCKUP_NO);
								permsTStaffRole.setClGrpId(permsTRole.getClGrpId());
								permsTStaffRole.setClHotelId(permsTRole.getClHotelId());
								permsTStaffRole.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
								permsTStaffRoleMapper.insertSelective(permsTStaffRole);
							}
						}
					}	
//				保存默认工作圈信息；
					String clDefaultTeam = permsTPostResponsibility.getClDefaultTeam();
					if (StringUtils.isNotBlank(clDefaultTeam)) {
						PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(clDefaultTeam);
						PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
						com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = permsTStaffTeamExample.createCriteria();
						pStaffTeamCriteria.andClTeamIdEqualTo(clDefaultTeam);
						pStaffTeamCriteria.andClStaffIdEqualTo(staffVal);
						pStaffTeamCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
						pStaffTeamCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
						List<PermsTStaffTeam> permsTStaffTeamList = permsTStaffTeamMapper.selectByExample(permsTStaffTeamExample);
						if (permsTStaffTeamList == null || permsTStaffTeamList.size() == 0) {
							PermsTStaffTeam permsTStaffTeam = new PermsTStaffTeam();
							permsTStaffTeam.setClId(HashUtils.uuidGenerator());
							permsTStaffTeam.setClStaffId(staffVal);
							permsTStaffTeam.setClTeamId(clDefaultTeam);
							permsTStaffTeam.setClStatus(Constants.STATUS_NORMAL);
							permsTStaffTeam.setClBlockup(Constants.BLOCKUP_NO);
							permsTStaffTeam.setClGrpId(permsTWorkTeam.getClGrpId());
							permsTStaffTeam.setClHotelId(permsTWorkTeam.getClHotelId());
							permsTStaffTeam.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
							permsTStaffTeamMapper.insertSelective(permsTStaffTeam);
						}
					}
				}
			}
		}
		return 1;
	}
/*
 * 获取员工工作酒店信息；	
 */
	@Override
	public List<StaffHotel> getStaffWorkHotelInfo(String id) {
		PermsTStaffHotelExample permsTStaffHotelExample = new PermsTStaffHotelExample();
		Criteria pStaffHotelCriteria = permsTStaffHotelExample.createCriteria();
		pStaffHotelCriteria.andClStaffIdEqualTo(id);
		pStaffHotelCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffHotelCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample);
		List<StaffHotel> staffHotelList = new ArrayList<>();
		for (PermsTStaffHotel permsTStaffHotel : permsTStaffHotelList) {
			try {
				staffHotelList.add(BeanUtils.copyDbBean2ServiceBean(permsTStaffHotel, new StaffHotel()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return staffHotelList;
	}
/*
 * 获取所有酒店的信息；
*/
	@Override
	public Map<String, String> getAllHotels() {
		Map<String, String> workHotelMap = new HashMap<>();
		
		PermsTGroupHotelExample example = new PermsTGroupHotelExample();
		PermsTGroupHotelExample.Criteria criteria = example.createCriteria();
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.selectByExample(example);
		if (permsTGroupHotelList != null && permsTGroupHotelList.size() > 0) {
			for (PermsTGroupHotel permsTGroupHotel : permsTGroupHotelList) {
					workHotelMap.put(permsTGroupHotel.getClId(), permsTGroupHotel.getClName());
			}
		}
		return workHotelMap;
	}
/**
 * 根据员工Id获取工作酒店的组织信息；
 */
	@Override
	public List<Staff> getorgGridInfos(String staffId) {
		List<Staff> staffList = new ArrayList<>();
		PermsTStaffHotelExample permsTStaffHotelExample = new PermsTStaffHotelExample();
		Criteria pStaffHotelCriteria = permsTStaffHotelExample.createCriteria();
		pStaffHotelCriteria.andClStaffIdEqualTo(staffId);
		pStaffHotelCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		pStaffHotelCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample);
		for (PermsTStaffHotel permsTStaffHotel : permsTStaffHotelList) {
			PermsTStaff permsTStaff = new PermsTStaff();
			permsTStaff.setClId(staffId);
			permsTStaff.setClDefaultPostId(permsTStaffHotel.getClPostId());
			permsTStaff.setClDefaultDptId(permsTStaffHotel.getClDptId());
			Staff staff = convertPermsTStaff2Staff(permsTStaff);
			List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaff);
			String orgInfo = "";
			if (orgInfoList != null && orgInfoList.size() > 0) {
				for (String str : orgInfoList) {
					if (StringUtils.isNotBlank(str)) {
						orgInfo = str;
						break;
					}
				}
			}
			if (StringUtils.isNotBlank(orgInfo)) {
				staff.setOrgInfo(orgInfo);
			}
//			staff.setPostSelId(permsTStaffHotel.getClPostId());
			staffList.add(staff);
		}
		if (staffList != null && staffList.size() > 1) {
			for (int i = 0; i < staffList.size() - 1; i++) {
	            for (int j = staffList.size() - 1; j > i; j--) {
	                Staff staffI = staffList.get(j);
					Staff staffJ = staffList.get(i);
					String defaultPostId = staffI.getDefaultPostId();
					String defaultPostId2 = staffJ.getDefaultPostId();
					if (StringUtils.isNotBlank(defaultPostId) && StringUtils.isNotBlank(defaultPostId2) && defaultPostId.equals(defaultPostId2)) {
						staffList.remove(j);
					}
	            }
	        }
		}
		return staffList;
	}

/*
 * (non-Javadoc)验证证件号；
 * @see com.sstc.hmis.p ermission.service.EmployeeService#validcertNumber(java.lang.String, java.lang.String, java.lang.String)
 */
	@Override
	public String validcertNumber(String certTypeCode, String certno, String id) {
		if (certTypeCode!=null &&certTypeCode.length()!=0  && certno !=null && certno.length()!=0) {
			PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria pStaffCriteria = permsTStaffExample.createCriteria();
			pStaffCriteria.andClCardTypeCodeEqualTo(certTypeCode);
			pStaffCriteria.andClCardNoEqualTo(certno);
			if (StringUtils.isNotBlank(id)) {
				pStaffCriteria.andClIdNotEqualTo(id);
			}
			pStaffCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
			pStaffCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			List<PermsTStaff> permsTStaffList = permsTStaffMapper.selectByExample(permsTStaffExample);
			if (permsTStaffList.size() > 0) {
				return "false";
			}
		}
		return "true";
	}
/*
 * 锁定数据；
 */
	@Override
	public String lockTeamData(String idvalue) {
		int res = 0;
		PermsTStaff permsTStaff = permsTStaffMapper.selectByPrimaryKey(idvalue);
		permsTStaff.setClModifyTime(new Date());
		permsTStaff.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
		Short statusNow = permsTStaff.getClStatus();
		if (statusNow == null || statusNow == Constants.STATUS_LOCK) {
			permsTStaff.setClStatus(Constants.STATUS_NORMAL);
			res = permsTStaffMapper.updateByPrimaryKeySelective(permsTStaff);
			return res == 1 ? "normal" : "fail";
		}else {
			permsTStaff.setClStatus(Constants.STATUS_LOCK);
			res = permsTStaffMapper.updateByPrimaryKeySelective(permsTStaff);
			return res == 1 ? "lock" : "fail";
		}
	}
/*
 * 删除关联角色信息；	
 */
	public String delLinkRole(String hotelValue, String staffId) {
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria pStaffRoleCriteria = permsTStaffRoleExample.createCriteria();
		pStaffRoleCriteria.andClHotelIdEqualTo(hotelValue);
		pStaffRoleCriteria.andClStaffIdEqualTo(staffId);
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		if (permsTStaffRoleList != null && permsTStaffRoleList.size() > 0) {
			for (PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
				permsTStaffRoleMapper.deleteByPrimaryKey(permsTStaffRole.getClId());
			}
		}
		return "success";
	}

	@Override
	public String hasPost(String postId) {
/*
 * 1：该职位不存在；
 * 		1.1：返回标识，页面提示；
 * 		1.2：查询该职位关联的所有员工信息并删除；
 * 		1.3:查询该职位关联的默认角色信息进行删除；		
 */
		PermsTPostResponsibilityExample permsTPostResponsibilityExample = new PermsTPostResponsibilityExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibilityExample.Criteria criteria = permsTPostResponsibilityExample.createCriteria();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		criteria.andClIdEqualTo(postId);
		List<PermsTPostResponsibility> permsTPostResponsibilitieList = permsTPostResponsibilityMapper.selectByExample(permsTPostResponsibilityExample);
		if (permsTPostResponsibilitieList == null || permsTPostResponsibilitieList.size() == 0) {
//			1.3:查询该职位关联的默认角色信息进行删除；		
			PermsTStaffHotelExample permsTStaffHotelExample = new PermsTStaffHotelExample();
			Criteria pStaffHotelCriteria = permsTStaffHotelExample.createCriteria();
			pStaffHotelCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			pStaffHotelCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
			pStaffHotelCriteria.andClPostIdEqualTo(postId);
			List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(permsTStaffHotelExample);
			if (permsTStaffHotelList != null && permsTStaffHotelList.size() > 0) {
				for (PermsTStaffHotel permsTStaffHotel : permsTStaffHotelList) {
					permsTStaffHotel.setClStatus(Constants.STATUS_DEL);
					permsTStaffHotel.setClModifyBy(LoginInfoHolder.getLoginAccount());
					permsTStaffHotel.setClModifyTime(new Date());
					permsTStaffHotelMapper.updateByPrimaryKeySelective(permsTStaffHotel);
				}
			}
//			1.1：返回标识，页面提示；
			return "no";
		}else {
			return "";
		}
	}

	@Override
	public Staff findStaffById(String staffId) {
		return convertPermsTStaff2Staff(permsTStaffMapper.selectByPrimaryKey(staffId));
	}

	@Override
	public List<String> findOrganizationByStaffId(@RequestBody Staff staff) {
		return permsTStaffMapper.selStaffOrgInfos2(convertStaff2PermsTStaff(staff));
	}

	@Override
	public List<Staff> findStaffListByRoleId(@RequestBody Role role) {
		List<PermsTStaff> PermsTStaffList = permsTStaffMapper.findStaffListByRoleId(convertRole2PermsTRole(role));
		List<Staff> staffList = new ArrayList<Staff>();
		for(PermsTStaff permsTStaff : PermsTStaffList){
			if(StringUtils.isNotBlank(permsTStaff.getClFamilyName())){
				permsTStaff.setClName(permsTStaff.getClFamilyName() + permsTStaff.getClName());
			}else {
				permsTStaff.setClName(permsTStaff.getClName());
			}
			PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
			permsTStaffHotel.setClStaffId(permsTStaff.getClId());
			permsTStaffHotel.setClWorkHotelId(role.getHotelId());
			List<String> orgList = permsTRoleMapper.findStaffListByRoleId(permsTStaffHotel);
			if(orgList != null && orgList.size() > 0){
				permsTStaff.setClCreateBy(orgList.get(0));
				staffList.add(convertPermsTStaff2Staff(permsTStaff));
			}
		}
		return staffList;
	}
	/*
	 * Role转换PermsTRole实体类
	 */
	private PermsTRole convertRole2PermsTRole(Role role){
		try {
			return BeanUtils.copyServiceBean2DbBean(role, new PermsTRole());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Staff> findStaffListByRoleIdLeft(@RequestBody Role role) {
		List<PermsTStaff> PermsTStaffList = permsTStaffMapper.findStaffListByRoleIdLeft(convertRole2PermsTRole(role));
		List<Staff> staffList = new ArrayList<Staff>();
		for(PermsTStaff permsTStaff : PermsTStaffList){
			if(StringUtils.isNotBlank(permsTStaff.getClFamilyName())){
				permsTStaff.setClName(permsTStaff.getClFamilyName() + permsTStaff.getClName());
			}else {
				permsTStaff.setClName(permsTStaff.getClName());
			}
			PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
			permsTStaffHotel.setClStaffId(permsTStaff.getClId());
			permsTStaffHotel.setClWorkHotelId(role.getHotelId());
			List<String> orgList = permsTRoleMapper.findStaffListByRoleId(permsTStaffHotel);
			if(orgList != null && orgList.size() > 0){
				permsTStaff.setClCreateBy(orgList.get(0));
				staffList.add(convertPermsTStaff2Staff(permsTStaff));
			}
			
		}
		return staffList;
	}

	@Override
	public List<Staff> listStaffWithOrgInfo(String queryInfo, String hotelId) {
		if(StringUtils.isBlank(hotelId)){
			return new ArrayList<>(0);
		}
		PermsTStaff staff = new PermsTStaff();
		staff.setClDefaultHotelId(hotelId);
		if(StringUtils.isNoneBlank(queryInfo)){
			staff.setClName(queryInfo);
		}
		List<PermsTStaff> list = permsTStaffMapper.listStaffWithOrgInfo(staff);
		return BeanUtils.copyDbBeanList2ServiceBeanList(list, Staff.class);
	}

	@Override
	public List<Staff> listStaffLinkman(String staffId, String hotelId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", staffId);
		paramMap.put("hotelId", hotelId);
		return BeanUtils.copyDbBeanList2ServiceBeanList(tUsedLinkmanMapper.getLinkmanInfoList(paramMap), Staff.class);
	}

	@Override
	public List<Staff> listStaffById(@RequestBody List<String> idList, String hotelId) {
		if(CollectionUtils.isNotEmpty(idList) && StringUtils.isNotBlank(hotelId)){
			return BeanUtils.copyDbBeanList2ServiceBeanList(permsTStaffMapper.listStaffById(idList,hotelId), Staff.class);
		}
		return new ArrayList<>(0);
	}

	@Override
	public int saveLinkman(String userId, String linkmanId) {
		TUsedLinkman tUserLinkman = new TUsedLinkman();
		tUserLinkman.setClId(HashUtils.uuidGenerator());
		tUserLinkman.setClUserId(userId);
		tUserLinkman.setClLinkmanId(linkmanId);
		return tUsedLinkmanMapper.insertSelective(tUserLinkman);
	}

	@Override
	public int getOftenLinkman(String userId, String linkmanId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("linkmanId", linkmanId);
		return tUsedLinkmanMapper.getOftenLinkman(paramMap);
	}

	@Override
	public void delOften(String userId, String linkmanId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("linkmanId", linkmanId);
		tUsedLinkmanMapper.delOften(paramMap);
	}

	@Override
	public List<Staff> getLinkmanInfoList(String userId, String hotelId, String grpId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		if (!StringUtils.isEmpty(hotelId)) {
			paramMap.put("hotelId", hotelId);			
		}
		if (!StringUtils.isEmpty(grpId)) {
			paramMap.put("grpId", grpId);			
		}
		return BeanUtils.copyDbBeanList2ServiceBeanList(tUsedLinkmanMapper.getLinkmanListForOften(paramMap), Staff.class);
	}

	
	@Override
	public PageResult<Staff> getStaffByPostCode(int index, int size, String postCode, String hotelId) {
		
		if(StringUtils.isNotBlank(postCode) && StringUtils.isNoneBlank(hotelId)){
			PermsTPostResponsibilityExample example = new PermsTPostResponsibilityExample();
			PermsTPostResponsibilityExample.Criteria criteria = example.createCriteria();
			criteria.andClPostCodeEqualTo(postCode);
			criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
			PermsTPostResponsibility post = permsTPostResponsibilityMapper.selectOneByExample(example);
			if(post != null && StringUtils.isNotBlank(post.getClId())){
				String id = post.getClId();
				try {
					return this.listSel(index, size, null, hotelId, Constants.STATUS_NORMAL, null, id, null, null, null);
				} catch (AppException e) {
					e.printStackTrace();
				}
			}
		}
		return new PageResult<>(index,size);
	}

	@Override
	public PageResult<Staff> pageListOutMe(int index, int size, Short nodeType, String orgId, Short status, String info,
			String teamId, String roleId, String loginUserId) throws AppException {
		PageResult<Staff> pageResult = new PageResult<Staff>();
//		PageHelper.startPage(index+1, size);
		PermsTQuery pTQuery = new PermsTQuery();
		if (StringUtils.isNotBlank(teamId)) {
			pTQuery.setClTeamId(teamId); 
		}
		if(nodeType != null && StringUtils.isNotBlank(orgId)){
			if (nodeType == Constants.NODE_TYPE_ORG || nodeType == Constants.NODE_TYPE_HOTEL) {
				pTQuery.setClHotelId(orgId);
			}else if (nodeType == Constants.NODE_TYPE_DPT) {
				pTQuery.setClDpartID(orgId);
			}else if (nodeType == Constants.NODE_TYPE_POST){
				pTQuery.setClPostId(orgId);
			}
		}
		if (StringUtils.isNotBlank(roleId)) {
			pTQuery.setClRoleId(roleId);
		}
		pTQuery.setClStatus(status);
		if (StringUtils.isNotBlank(info)) {
			info = info.trim();
			pTQuery.setClName(info);
		}
		if(StringUtils.equals(orgId, "-1")){
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			List<Staff> staffList = new ArrayList<>();
			for (PermsTStaff permsTStaff : list) {
				if (!loginUserId.equals(permsTStaff.getClId())) {
					Staff staff = convertPermsTStaff2Staff(permsTStaff);
					staffSetAllName(staff);
					PermsTStaff db_permsTStaff = permsTStaffMapper.selectByPrimaryKey(permsTStaff.getClId());
					PermsTStaff permsTStaffCondition = new PermsTStaff();
					permsTStaffCondition.setClId(permsTStaff.getClId());
					permsTStaffCondition.setClDefaultHotelId(db_permsTStaff.getClHotelId());
					List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaffCondition);
					String orgInfo = "";
					if (orgInfoList != null && orgInfoList.size() > 0) {
						orgInfo = orgInfoList.get(0);
					}
					staff.setOrgInfo(orgInfo);
					staffList.add(staff);
				}
			}
			// 分页处理
	        Page<Staff> resultPage = new Page<Staff>(index + 1, size);
	        resultPage.setReasonable(true);
	        resultPage.setTotal(staffList.size());
	        if (staffList.size() >= (index + 1) * size)
	        {
	            resultPage.addAll(staffList.subList(resultPage.getStartRow(), resultPage.getEndRow()));
	        }
	        else if (staffList.size() > index * size
	            && staffList.size() < (index + 1) * size)
	        {
	            resultPage.addAll(staffList.subList(resultPage.getStartRow(), staffList.size()));
	        }
	        pageResult.setResult(true);
	        pageResult.setResults(staffList.size());
	        pageResult.setRows(resultPage);
		}else {
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			List<Staff> staffList = new ArrayList<>();
			
			for (PermsTStaff permsTStaff : list) {
				if (!loginUserId.equals(permsTStaff.getClId())) {
					Staff staff = convertPermsTStaff2Staff(permsTStaff);
					staffSetAllName(staff);
					List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaff);
					String orgInfo = "";
					if (orgInfoList != null && orgInfoList.size() > 0) {
						orgInfo = orgInfoList.get(0);
					}
					staff.setOrgInfo(orgInfo);
					staffList.add(staff);
				}
				
			}
			// 分页处理
	        
	        Page<Staff> resultPage = new Page<Staff>(index + 1, size);
	        resultPage.setReasonable(true);
	        resultPage.setTotal(staffList.size());
	        if (staffList.size() >= (index + 1) * size)
	        {
	            resultPage.addAll(staffList.subList(resultPage.getStartRow(), resultPage.getEndRow()));
	        }
	        else if (staffList.size() > index * size
	            && staffList.size() < (index + 1) * size)
	        {
	            resultPage.addAll(staffList.subList(resultPage.getStartRow(), staffList.size()));
	        }
	        pageResult.setResult(true);
	        pageResult.setResults(staffList.size());
	        pageResult.setRows(resultPage);
		}
		
		return pageResult;
	}

	@Autowired
	private SmsSendTplProperties smsSendTplProperties;
	@Autowired
	private SmsSendSignProperties SmsSendSignProperties;
	
	@Override
	public Result resetPwd(String id) {
		Result result = Result.SUCCESS;
		PermsTStaff staff = permsTStaffMapper.selectByPrimaryKey(id);
		if(staff != null){
			String phone = staff.getClMobilePhone();
			String cacheKey = MessageFormat.format(CacheConstants.RESET_PWD_PHONE, phone);
			//短信发送时间间隔剩余时间，间隔时间内无法重复发送短信
			Long left = stringRedisTemplate.getExpire(cacheKey, TimeUnit.SECONDS);
			if(left == null || left == 0){
				String password = CommonUtils.getRadomPassword();
				Map<String,Object> param = new HashMap<>(1);
				param.put("password", password);
				SmsSendParams params = new SmsSendParams(param, phone, 
						SmsSendSignProperties.getSignName(), smsSendTplProperties.getPwdResetId());
				SendResult sendResult = smsSenderService.sendParams(params);
				if(sendResult != null){
					if(sendResult.isSuccess()){
						stringRedisTemplate.opsForValue().set(cacheKey, 
								password, PortalConstants.SMS_SEND_INTERVAL, TimeUnit.SECONDS);
						String salt = staff.getClSalt();
						String encodePwd = HashUtils.stringMd5Encode(password, salt);
						staff.setClPassword(encodePwd);
						staff.setClModifyBy(LoginInfoHolder.getLoginAccount());
						staff.setClModifyTime(new Date());
						permsTStaffMapper.updateByPrimaryKeySelective(staff);
					}else{
						return Result.errorResult(sendResult.getMsg());
					}
				}
			}else{
				result = Result.ERROR_REPEAT;
			}
		}
		return result;
	}

	@Override
	public Result updatePwd(String oldPwd, String newPwd, String userId) {
		PermsTStaff staff = permsTStaffMapper.selectByPrimaryKey(userId);
		if(staff != null){
			String salt = staff.getClSalt();
			String oldEncodePwd = HashUtils.stringMd5Encode(oldPwd, salt);
			String oldPassword = staff.getClPassword();
			if(StringUtils.equals(oldEncodePwd, oldPassword)){
				String newEncodePwd = HashUtils.stringMd5Encode(newPwd, salt);
				staff.setClPassword(newEncodePwd);
				staff.setClModifyBy(LoginInfoHolder.getLoginAccount());
				int line = permsTStaffMapper.updateByPrimaryKey(staff);
				if(line > 0){
					return Result.SUCCESS;
				}
			}
		}
		return Result.ERROR_PARAMS;
	}

	@Override
	public String getOrgInfoByStaffId(String staffId) {
		PermsTStaff permsTStaffCondition = new PermsTStaff();
		permsTStaffCondition.setClId(staffId);
		List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaffCondition);
		if (orgInfoList != null && orgInfoList.size() > 0) {
			return orgInfoList.get(0);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 查询员工带分页（新审批人管理新建按人员）
	 * @author yuankairui
	 */
	@Override
	public List<Staff> pageListForAddPer( Short nodeType, String orgId, String staffName) {
	
		List<Staff> staffList = new ArrayList<>();
		PermsTQuery pTQuery = new PermsTQuery();
		pTQuery.setClName(staffName);
		if(nodeType != null && StringUtils.isNotBlank(orgId)){
			if (nodeType == Constants.NODE_TYPE_ORG || nodeType == Constants.NODE_TYPE_HOTEL) {
				pTQuery.setClHotelId(orgId);
			}else if (nodeType == Constants.NODE_TYPE_DPT) {
				pTQuery.setClDpartID(orgId);
			}else if (nodeType == Constants.NODE_TYPE_POST){
				pTQuery.setClPostId(orgId);
			}
		}
		if(StringUtils.equals(orgId, "-1")){
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			
			for (PermsTStaff permsTStaff : list) {
				Staff staff = convertPermsTStaff2Staff(permsTStaff);
				staffSetAllName(staff);

				PermsTStaff db_permsTStaff = permsTStaffMapper.selectByPrimaryKey(permsTStaff.getClId());
				PermsTStaff permsTStaffCondition = new PermsTStaff();
				permsTStaffCondition.setClId(permsTStaff.getClId());
				permsTStaffCondition.setClDefaultHotelId(db_permsTStaff.getClHotelId());
				List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaffCondition);
				String orgInfo = "";
				if (orgInfoList != null && orgInfoList.size() > 0) {
					orgInfo = orgInfoList.get(0);
				}
				staff.setOrgInfo(orgInfo);
				staffList.add(staff);
			}
			

		}else {
			List<PermsTStaff> list = permsTStaffMapper.pageList(pTQuery);
			for (PermsTStaff permsTStaff : list) {
				Staff staff = convertPermsTStaff2Staff(permsTStaff);
				staffSetAllName(staff);
				List<String> orgInfoList = permsTStaffMapper.selStaffOrgInfos(permsTStaff);
				String orgInfo = "";
				if (orgInfoList != null && orgInfoList.size() > 0) {
					orgInfo = orgInfoList.get(0);
				}
				staff.setOrgInfo(orgInfo);
				staffList.add(staff);
			}
		}
		return staffList;
	}
}
