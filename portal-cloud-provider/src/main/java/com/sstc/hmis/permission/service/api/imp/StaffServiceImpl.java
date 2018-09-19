/**
 * 
 */
package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.mdata.common.service.api.easemob.IMUserAPI;
import com.sstc.hmis.mdata.service.api.GenCodeService;
import com.sstc.hmis.model.constants.ApiConstants;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.LinkmanInfo;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.data.StaffRole;
import com.sstc.hmis.permission.data.StaffTeam;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.permission.data.cas.SimpleVo;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDptStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTGroupHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPostResponsibilityMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffTeamMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTWorkTeamMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeam;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;
import com.sstc.hmis.permission.service.StaffService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.DateUtils;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

import io.swagger.client.model.Nickname;
import io.swagger.client.model.User;

/**
 * <p>
 * Title: StaffServiceImp
 * </p>
 * <p>
 * Description: TODO
 * </p>
 * <p>
 * Company: SSTC
 * </p>
 * 
 * @author Qxiaoxiang
 * @date 2017年3月30日 上午9:32:19
 */

@RestController
public class StaffServiceImpl implements StaffService {

	private static final Logger LOG = LoggerFactory.getLogger(StaffServiceImpl.class);

	@Autowired
	PermsTStaffMapper permTStaffMapper;

	@Autowired
	PermsTStaffHotelMapper permsTStaffHotelMapper;

	@Autowired
	PermsTStaffRoleMapper permsTStaffRoleMapper;

	@Autowired
	PermsTStaffTeamMapper permsTStaffTeamMapper;

	@Autowired
	PermsTRoleMapper permsTRoleMapper;

	@Autowired
	PermsTWorkTeamMapper permsTWorkTeamMapper;

	@Autowired
	PermsTPostResponsibilityMapper permsTPostResponsibilityMapper;

	@Autowired
	PermsTDptStaffMapper permsTDptStaffMapper;

	@Autowired
	PermsTGroupHotelMapper permsTGroupHotelMapper;

	@Autowired
	private GenCodeService gService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private IMUserAPI imUserAPI;

	@Override
	public Staff login(@RequestBody Staff staff) throws AppException {
		Staff staffInfo = new Staff();
		String account = staff.getAccount();
		String password = staff.getPassword();
		if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
			staffInfo.setReturnCode(3);
			return staffInfo;
		}
		PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
		Criteria criteria = permsTStaffExample.createCriteria();
		Criteria orCriteria = permsTStaffExample.or();
		if (StringUtils.isNotBlank(account)) {
			criteria.andClAccountEqualTo(account);
			criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			criteria.andClStatusNotEqualTo(Constants.STATUS_AUDIT);
			orCriteria.andClMobilePhoneEqualTo(account);
			orCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			orCriteria.andClStatusNotEqualTo(Constants.STATUS_AUDIT);
		}
		List<PermsTStaff> permsTStaffList = permTStaffMapper.selectByExample(permsTStaffExample);
		int size = permsTStaffList.size();
		if (size == 0) {
			LOG.info("帐号{}不存在", staffInfo.getAccount());
			staffInfo.setReturnCode(3);
			return staffInfo;
		} else if (size == 1) {
			PermsTStaff permsTStaff2 = permsTStaffList.get(0);
			String clSalt = permsTStaff2.getClSalt();
			String encodeDb = permsTStaff2.getClPassword();
			String encode = HashUtils.stringMd5Encode(password, clSalt);
			if (encode.equals(encodeDb)) {
				if (permsTStaff2.getClExpiredDate() != null && permsTStaff2.getClExpiredDate().before(new Date())) {
					LOG.info("帐号{}已经过期，无法登录", staffInfo.getAccount());
					staffInfo.setReturnCode(ApiConstants.ACCOUNT_EXPIRED);
					return staffInfo;
				}
				Result result = loginRuleLimit(permsTStaff2.getClId(), Constants.LOGIN_TYPE_APP);
				int status = result.getStatus();
				if (status != 200) {
					LOG.info("帐号{}登陆受登录策略限制", staffInfo.getAccount());
					staffInfo.setReturnCode(ApiConstants.LOGIN_LIMIT);
					return staffInfo;
				} else {
					// 设置未受登录限制的酒店ID设为小秘默认工作酒店（不确定小秘其他接口是否会操作更新此数据）
					PermsTStaffHotel staffHotel = (PermsTStaffHotel) result.getResult();
					permsTStaff2.setClHotelId(staffHotel.getClWorkHotelId());
					permsTStaff2.setClDefaultDptId(staffHotel.getClDptId());
					permsTStaff2.setClDefaultPostId(staffHotel.getClPostId());
				}
				permsTStaff2.setClCashPwd(null);
				permsTStaff2.setClSalt(null);
				permsTStaff2.setClPassword(null);
				staffInfo = BeanUtils.copyDbBean2ServiceBean(permsTStaff2, Staff.class);
				String hotelId = permsTStaff2.getClDefaultHotelId();
				PermsTGroupHotel hotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelId);
				if (hotel != null) {
					staffInfo.setDefaultHotelName(hotel.getClName());
				} else {
					LOG.error("ID为{}的酒店信息不存在");
				}
				// 设置国家省州市名称
				if (StringUtils.isNotEmpty(staffInfo.getCountryId())) {
					staffInfo.setCountryName(
							(String) gService.getGencodeDataById(staffInfo.getCountryId()).get("StrVal2"));
				}
				if (StringUtils.isNotEmpty(staffInfo.getProvinceId())) {
					staffInfo.setProvinceName(
							(String) gService.getGencodeDataById(staffInfo.getProvinceId()).get("StrVal2"));
				}
				if (StringUtils.isNotEmpty(staffInfo.getCityId())) {
					staffInfo.setCityName((String) gService.getGencodeDataById(staffInfo.getCityId()).get("StrVal2"));
				}
				return staffInfo;
			} else {
				staffInfo.setReturnCode(3);
				return staffInfo;
			}
		} else {
			LOG.error("帐号{}数据异常，存在多个次帐号数据", staffInfo.getAccount());
			Staff staff2 = new Staff();
			staff2.setId("2");
			return staff2;
		}
	}

	/*
	 * Staff转换PermsTStaff实体类
	 */
	private PermsTStaff convertStaff2PermsTStaff(Staff staff) {
		try {
			return BeanUtils.copyServiceBean2DbBean(staff, new PermsTStaff());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int regist(@RequestBody Staff staff) throws AppException {
		// TODO 注册需初始化状态为待审核状态
		// TODO 检查账号是否重复
		String account = staff.getAccount();
		String password = staff.getPassword();
		String grpId = staff.getGrpId();
		String hotelId = staff.getHotelId();
		String mobilePhone = staff.getMobilePhone();
		String familyName = staff.getFamilyName();
		String name = staff.getName();

		if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(grpId)
				&& StringUtils.isNotBlank(hotelId) && StringUtils.isNotBlank(mobilePhone)
				&& StringUtils.isNotBlank(familyName) && StringUtils.isNotBlank(name)) {
			String salt = HashUtils.uuidGenerator();
			String passwordMd5 = HashUtils.stringMd5Encode(password, salt);
			PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
			Criteria criteria = permsTStaffExample.createCriteria();
			criteria.andClAccountEqualTo(account);
			// 根据名字查找信息获取‘盐’会不会不太准确
			List<PermsTStaff> permsTStaffList = permTStaffMapper.selectByExample(permsTStaffExample);
			if (permsTStaffList.size() > 0) {
				// 用户名重复
				throw new AppException(3, "用户名重复");
			}
			staff.setPassword(passwordMd5);
			staff.setSalt(salt);
			staff.setId(HashUtils.uuidGenerator());
			staff.setDefaultHotelId(hotelId);
			staff.setCreateBy(account);
			int res = permTStaffMapper.insertSelective(convertStaff2PermsTStaff(staff));
			// 数据成功保存后，紧接保存PERMS_T_STAFF_HOTEL数据；
			PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
			permsTStaffHotel.setClId(HashUtils.uuidGenerator());
			permsTStaffHotel.setClStaffId(staff.getId());
			permsTStaffHotel.setClBlockup(Constants.BLOCKUP_NO);
			permsTStaffHotel.setClWorkHotelId(hotelId);
			permsTStaffHotel.setClCreateBy(account);
			permsTStaffHotel.setClHotelId(hotelId);
			permsTStaffHotel.setClGrpId(grpId);
			int resStaffHotel = permsTStaffHotelMapper.insertSelective(permsTStaffHotel);

			if (res == 1 && resStaffHotel == 1) {
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
				return 0;
			}
		}
		throw new AppException(3, "注册失败");
	}

	/*
	 * 通过集团ID，酒店ID，部门ID，职位ID查询出员工列表
	 */
	@Override
	public List<Staff> listByStaffHotel(@RequestBody StaffHotel staffHotel) {
		PermsTStaffHotelExample example = new PermsTStaffHotelExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample.Criteria createCriteria = example
				.createCriteria();
		createCriteria.andClGrpIdEqualTo(staffHotel.getGrpId());
		createCriteria.andClHotelIdEqualTo(staffHotel.getHotelId());
		String dptId = staffHotel.getDptId();
		String postId = staffHotel.getPostId();
		if (StringUtils.isNotBlank(dptId)) {
			createCriteria.andClDptIdEqualTo(dptId);
		}
		if (StringUtils.isNotBlank(postId)) {
			createCriteria.andClPostIdEqualTo(postId);
		}
		// 查询员工工作酒店信息得到所有id
		List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(example);

		// 根据每个id查询酒店员工信息封装到List集合中
		List<Staff> staffList = new ArrayList<Staff>();
		for (PermsTStaffHotel permsTStaffHotel : permsTStaffHotelList) {
			String staffId = permsTStaffHotel.getClStaffId();
			PermsTStaff permsTStaff = permTStaffMapper.selectByPrimaryKey(staffId);
			Staff staff = BeanUtils.copyDbBean2ServiceBean(permsTStaff, Staff.class);
			staffList.add(staff);
		}

		return staffList;
	}

	/**
	 * 根据属性查询对象，需要根据需求自行扩展
	 * 
	 * @param t
	 * @return
	 */
	@Override
	public Staff find(@RequestBody Staff t) {
		PermsTStaffExample example = new PermsTStaffExample();
		PermsTStaffExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andClIdEqualTo(t.getId());
		createCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		createCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		PermsTStaff permsTStaff = permTStaffMapper.selectByPrimaryKey(t.getId());
		if (permsTStaff != null) {
			Short blockup = permsTStaff.getClBlockup();
			Short status = permsTStaff.getClStatus();
			if(blockup == Constants.BLOCKUP_NO && status != Constants.STATUS_DEL){
				return BeanUtils.copyDbBean2ServiceBean(permsTStaff, Staff.class);
			}
		}
		return null;
	}

	@Override
	public Staff queryByUserId(String account) {
		PermsTStaffExample example = new PermsTStaffExample();
		PermsTStaffExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andClAccountEqualTo(account);
		createCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		List<PermsTStaff> list = permTStaffMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			PermsTStaff permstaff = list.get(0);
			try {
				Staff staff = new Staff();
				BeanUtils.copyDbBean2ServiceBean(permstaff, staff);
				return staff;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取员工工作圈信息；
	 * 
	 * @param staffInfo
	 * @return
	 */
	@Override
	public List<StaffTeam> getWorkTeamInfos(@RequestBody Staff staffInfo) {
		PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = permsTStaffTeamExample
				.createCriteria();
		pStaffTeamCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffTeamCriteria.andClStaffIdEqualTo(staffInfo.getId());
		List<PermsTStaffTeam> permsTStaffTeamList = permsTStaffTeamMapper.selectByExample(permsTStaffTeamExample);
		List<StaffTeam> staffTeamList = new ArrayList<>();
		if (permsTStaffTeamList != null && permsTStaffTeamList.size() > 0) {
			for (PermsTStaffTeam permsTStaffTeam : permsTStaffTeamList) {
				try {
					StaffTeam staffTeam = BeanUtils.copyDbBean2ServiceBean(permsTStaffTeam, new StaffTeam());
					PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(staffTeam.getTeamId());
					if (permsTWorkTeam != null) {
						staffTeam.setTeamName(permsTWorkTeam.getClName());
					}
					staffTeamList.add(staffTeam);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return staffTeamList;
	}

	/**
	 * 获取员工角色信息；
	 * 
	 * @param staffInfo
	 * @return
	 */
	@Override
	public List<StaffRole> getStaffRoleInfos(@RequestBody Staff staffInfo) {
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria pStaffRoleCriteria = permsTStaffRoleExample
				.createCriteria();
		pStaffRoleCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffRoleCriteria.andClStaffIdEqualTo(staffInfo.getId());
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		List<StaffRole> staffRoleList = new ArrayList<>();
		if (permsTStaffRoleList != null && permsTStaffRoleList.size() > 0) {
			for (PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
				try {
					StaffRole staffRole = BeanUtils.copyDbBean2ServiceBean(permsTStaffRole, new StaffRole());
					PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(permsTStaffRole.getClRoleId());
					if (permsTRole != null) {
						staffRole.setRoleName(permsTRole.getClName());
					}
					staffRoleList.add(staffRole);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return staffRoleList;
	}

	/**
	 * 完善资料
	 * 
	 * @author CKang
	 * @date 2017年5月12日 下午3:21:32
	 * @return
	 */
	@Override
	public int updateStaffByStaffId(@RequestBody Staff staffInfo) {
		return permTStaffMapper.updateByPrimaryKeySelective(convertStaff2PermsTStaff(staffInfo));
	}

	/**
	 * 通过id查询员工信息
	 * 
	 * @author CKang
	 * @date 2017年5月12日 下午3:37:24
	 * @param staffId
	 * @return
	 */
	@Override
	public Staff findStaffByStaffId(String staffId) {
		PermsTStaff staff = permTStaffMapper.selectByPrimaryKey(staffId);
		return BeanUtils.copyDbBean2ServiceBean(staff, Staff.class);
	}

	@Override
	public int getUserLoginSuccessTimes(@RequestBody Staff staffInfo) {
		Map<String, Object> condition = new HashMap<>(1);
		condition.put("account", staffInfo.getAccount());
		return permTStaffMapper.countLoginTimes(condition);
	}

	@Override
	public int insertLoginInfo(@RequestBody Staff staffInfo) {
		Map<String, Object> condition = new HashMap<>(2);
		condition.put("account", staffInfo.getAccount());
		condition.put("staffId", staffInfo.getId());
		return permTStaffMapper.insertLoginInfo(condition);
	}

	/**
	 * @author CKang
	 * @date 2017年5月25日 上午10:50:48
	 * @param staffId
	 *            员工id
	 * @param deptId
	 *            部门id
	 * @param hotelId
	 *            酒店id
	 * @param postId
	 *            职位id
	 * @param type
	 *            类型
	 * @param expireTime
	 *            过期时间
	 * @param modifyBy
	 *            修改人
	 */
	@Override
	public void auditStaff(String staffId, String deptId, String hotelId, String postId, Short type, Date expireTime,
			String modifyBy) {
		PermsTStaff db_permsTStaff = permTStaffMapper.selectByPrimaryKey(staffId);
		// 修改staff表
		PermsTStaff permsTStaff = new PermsTStaff();
		permsTStaff.setClId(staffId);
		permsTStaff.setClStatus(Constants.STATUS_NORMAL);
		permsTStaff.setClType(type);
		permsTStaff.setClExpiredDate(expireTime);
		permsTStaff.setClModifyTime(new Date());
		permsTStaff.setClModifyBy(modifyBy);
		permsTStaff.setClDefaultPostId(postId);
		permTStaffMapper.updateByPrimaryKeySelective(permsTStaff);

		// 插入工作酒店表
		PermsTStaffHotel permsTStaffHotel = new PermsTStaffHotel();
		permsTStaffHotel.setClId(HashUtils.uuidGenerator());
		permsTStaffHotel.setClStaffId(staffId);
		permsTStaffHotel.setClBlockup(Constants.BLOCKUP_NO);
		permsTStaffHotel.setClPostId(postId);
		permsTStaffHotel.setClDptId(deptId);
		permsTStaffHotel.setClWorkHotelId(hotelId);
		permsTStaffHotel.setClHotelId(hotelId);
		permsTStaffHotel.setClIsLimit(Constants.LOGIN_UNLIMIT);
		permsTStaffHotel.setClGrpId(db_permsTStaff.getClGrpId());
		permsTStaffHotel.setClCreateTime(new Date());
		permsTStaffHotel.setClCreateBy(db_permsTStaff.getClCreateBy());
		permsTStaffHotel.setClStatus(Constants.STATUS_NORMAL);
		permsTStaffHotelMapper.insertSelective(permsTStaffHotel);

		// 获取职位表中的默认角色
		PermsTPostResponsibility db_permsTPostResponsibility = permsTPostResponsibilityMapper
				.selectByPrimaryKey(postId);
		String[] roleIdx = db_permsTPostResponsibility.getClDefaultRole();
		// 插入员工角色关联表
		for (int i = 0; i < roleIdx.length; i++) {
			PermsTStaffRole permsTStaffRole = new PermsTStaffRole();
			permsTStaffRole.setClId(HashUtils.uuidGenerator());
			permsTStaffRole.setClStaffId(staffId);
			permsTStaffRole.setClRoleId(roleIdx[i]);
			permsTStaffRole.setClStatus(Constants.STATUS_NORMAL);
			permsTStaffRole.setClBlockup(Constants.BLOCKUP_NO);
			permsTStaffRole.setClGrpId(db_permsTStaff.getClGrpId());
			permsTStaffRole.setClHotelId(hotelId);
			permsTStaffRole.setClCreateTime(new Date());
			permsTStaffRole.setClCreateBy(db_permsTStaff.getClCreateBy());
			permsTStaffRoleMapper.insertSelective(permsTStaffRole);
		}

		// 插入部门员工关联表
		PermsTDptStaff permsTDptStaff = new PermsTDptStaff();
		permsTDptStaff.setClId(HashUtils.uuidGenerator());
		permsTDptStaff.setClStaffId(staffId);
		permsTDptStaff.setClDprId(deptId);
		permsTDptStaff.setClStatus(Constants.STATUS_NORMAL);
		permsTDptStaff.setClBlockup(Constants.BLOCKUP_NO);
		permsTDptStaff.setClGrpId(db_permsTStaff.getClGrpId());
		permsTDptStaff.setClHotelId(hotelId);
		permsTDptStaff.setClCreateTime(new Date());
		permsTDptStaff.setClCreateBy(db_permsTStaff.getClCreateBy());
		permsTDptStaffMapper.insertSelective(permsTDptStaff);

		// 插入员工工作圈关联表
		if (StringUtils.isNotBlank(db_permsTPostResponsibility.getClDefaultTeam())) {
			PermsTStaffTeam permsTStaffTeam = new PermsTStaffTeam();
			permsTStaffTeam.setClId(HashUtils.uuidGenerator());
			permsTStaffTeam.setClStaffId(staffId);
			permsTStaffTeam.setClTeamId(db_permsTPostResponsibility.getClDefaultTeam());
			permsTStaffTeam.setClStatus(Constants.STATUS_NORMAL);
			permsTStaffTeam.setClBlockup(Constants.BLOCKUP_NO);
			permsTStaffTeam.setClGrpId(db_permsTStaff.getClGrpId());
			permsTStaffTeam.setClHotelId(hotelId);
			permsTStaffTeam.setClCreateTime(new Date());
			permsTStaffTeam.setClCreateBy(db_permsTStaff.getClCreateBy());
			permsTStaffTeamMapper.insertSelective(permsTStaffTeam);
		}

	}

	@Override
	public List<SimpleVo> findUserStaffHotel(String staffId) {
		List<SimpleVo> list = new ArrayList<>();
		if (StringUtils.isBlank(staffId)) {
			return list;
		}
		PermsTStaffHotelExample example = new PermsTStaffHotelExample();
		PermsTStaffHotelExample.Criteria criteria = example.createCriteria();
		criteria.andClStaffIdEqualTo(staffId);
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTStaffHotel> staffHotelList = permsTStaffHotelMapper.selectByExample(example);
		List<String> idList = new ArrayList<>();
		for (PermsTStaffHotel permsTStaffHotel : staffHotelList) {
			idList.add(permsTStaffHotel.getClWorkHotelId());
		}
		if (CollectionUtils.isEmpty(idList)) {
			return list;
		}
		PermsTGroupHotelExample hotelExample = new PermsTGroupHotelExample();
		PermsTGroupHotelExample.Criteria criteria2 = hotelExample.createCriteria();
		criteria2.andClIdIn(idList);
		criteria2.andClStatusEqualTo(Constants.STATUS_NORMAL);
		hotelExample.setOrderByClause(" cl_type asc ");

		List<PermsTGroupHotel> hotelList = permsTGroupHotelMapper.selectByExample(hotelExample);
		for (PermsTGroupHotel permsTGroupHotel : hotelList) {
			list.add(new SimpleVo(permsTGroupHotel.getClId(), permsTGroupHotel.getClName(),
					permsTGroupHotel.getClRegion()));
		}
		return list;
	}

	@Override
	public List<Staff> getStaffInfo(@RequestBody List<String> userIdList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdList", userIdList);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permTStaffMapper.getAllStaffList(paramMap), Staff.class);
	}

	@Override
	public List<String> getStaffIdListByJobId(String jobId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobId", jobId);
		return permTStaffMapper.getUserIdByJobId(paramMap);
	}

	@Override
	public LinkmanInfo getStaffById(@RequestBody Map<String, Object> paramMap) throws Exception {
		return BeanUtils.copyDbBean2ServiceBean(permTStaffMapper.getStaffById(paramMap), new LinkmanInfo());
	}

	@Override
	public void updateStaffStatus(@RequestBody Map<String, Object> paramMap) {
		permTStaffMapper.updateStaffStatus(paramMap);
	}

	@Override
	public void updateStaffPsd(@RequestBody Map<String, Object> paramMap) {
		permTStaffMapper.updateStaffPsd(paramMap);
	}

	@Override
	public List<Staff> findStaffByPhoneAccount(@RequestBody Map<String, Object> paramMap) {
		return BeanUtils.copyDbBeanList2ServiceBeanList(permTStaffMapper.findStaffByPhoneAccount(paramMap),
				Staff.class);
	}

	@Override
	public int insertSelective(@RequestBody Staff staff) throws Exception {
		return permTStaffMapper.insertSelective(BeanUtils.copyServiceBean2DbBean(staff, new PermsTStaff()));
	}

	@Override
	public int updateByPrimaryKeySelective(@RequestBody Staff staff) throws Exception {
		return permTStaffMapper.updateByPrimaryKeySelective(BeanUtils.copyServiceBean2DbBean(staff, new PermsTStaff()));
	}

	@Override
	public Integer casSsoLogin(String userName, String password, Integer deviceType) {

		Result result = Result.ERROR_PARAMS;
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			PermsTStaffExample example = new PermsTStaffExample();
			PermsTStaffExample.Criteria criteria = example.createCriteria();
			criteria.andClAccountEqualTo(userName);
			criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			List<PermsTStaff> staffList = permTStaffMapper.selectByExample(example);
			for (PermsTStaff staff : staffList) {
				String encodePwd = staff.getClPassword();
				String staffSalt = staff.getClSalt();
				String loginEncodePwd = HashUtils.stringMd5Encode(password, staffSalt);
				if (StringUtils.equals(encodePwd, loginEncodePwd)) {
					Short status = staff.getClStatus();
					switch (status) {
					case Constants.STATUS_NORMAL:
						result = loginRuleLimit(staff.getClId(), deviceType);
						break;
					case Constants.STATUS_LOCK:
						result = new Result(PortalConstants.LOGIN_CODE_LOCK, "帐号锁定中");
						break;
					case Constants.STATUS_AUDIT:
						result = new Result(PortalConstants.LOGIN_CODE_AUDIT, "帐号审核中");
						break;
					}
				}
			}
		}
		return result.getStatus();
	}

	/**
	 * 检测登陆策略
	 * 
	 * @return
	 */
	private Result loginRuleLimit(String staffId, Integer deviceType) {
		if (StringUtils.isBlank(staffId)) {
			return Result.ERROR_PARAMS;
		}
		PermsTStaffHotelExample example = new PermsTStaffHotelExample();
		PermsTStaffHotelExample.Criteria criteria = example.createCriteria();
		criteria.andClStaffIdEqualTo(staffId);
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTStaffHotel> staffHotelList = permsTStaffHotelMapper.selectByExample(example);
		Result result = new Result(PortalConstants.LOGIN_CODE_LIMIT, "登录策略限制登录");
		PermsTStaffHotel allowLoginHotel = null;
		for (PermsTStaffHotel staffHotel : staffHotelList) {
			short isLimit = staffHotel.getClIsLimit();
			// 该酒店未启用登录策略
			if (isLimit == Constants.LOGIN_UNLIMIT) {
				result = Result.SUCCESS;
				allowLoginHotel = staffHotel;
				break;
			}

			Date startDate = staffHotel.getClBeginDate();
			Date endDate = staffHotel.getClEndDate();
			String[] timeIntervals = staffHotel.getClTimeInterval();
			String[] weeks = staffHotel.getClWeek();
			Short loginType = staffHotel.getClLoginType();
			Integer[] deviceTypes = staffHotel.getClLimitType();
			// 1、在时间区间（true） 并且 允许登录（true） 并且 允许PC类型设备登录（true） ，则可登录
			// 2、在时间区间（true） 并且 不允许登录（false）并且 允许PC类型设备登录（true），则不可以登录
			// 3、不在时间区间（false） 并且 不允许登录 （false）并且 允许PC类型设备登录（true），则不可以登录
			boolean isInRange = isInRange(startDate, endDate, timeIntervals, weeks);
			if (isInRange && (loginType == null || loginType == Constants.LOGIN_ALLOW)) {
				if ((deviceTypes != null && ArrayUtils.contains(deviceTypes, deviceType)) || deviceTypes == null) {
					result = Result.SUCCESS;
					allowLoginHotel = staffHotel;
					break;
				}
			} else if (!isInRange && loginType == Constants.LOGIN_NOT_ALLOW) {
				result = Result.SUCCESS;
				allowLoginHotel = staffHotel;
				break;
			}
		}
		if (allowLoginHotel != null) {
			result.setResult(allowLoginHotel);
		}
		return result;
	}

	/**
	 * 是否符合设置的策略，不包含是否允许登录判断
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param timeIntervals
	 *            时间区间
	 * @param weeks
	 *            周集合
	 * @return true:符合策略 false:不符合
	 */
	private boolean isInRange(Date startDate, Date endDate, String[] timeIntervals, String[] weeks) {
		// 日期 and 时间 and 周
		if (isInRangeDate(startDate, endDate) && isInRangeTime(timeIntervals) && isInRangeWeek(weeks)) {
			return true;
		}
		return false;
	}

	private boolean isInRangeDate(Date startDate, Date endDate) {
		long sysTime = System.currentTimeMillis();
		if (startDate == null || endDate == null) {
			return true;
		} else if ((startDate.getTime() <= sysTime && endDate.getTime() >= sysTime)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 多组时间区间满足任意一组即符合 （or）
	 * 
	 * @param timeIntervals
	 *            时间策略
	 * @return
	 */
	private boolean isInRangeTime(String[] timeIntervals) {
		boolean flag = true;
		if (timeIntervals != null) {
			int length = timeIntervals.length;
			String checkTime = DateUtils.format(new Date(), DateUtils.DATE_FMT_TIME_1);
			for (int i = 0; i < length; i += 2) {
				if (i + 1 <= length) {
					String start = timeIntervals[i];
					String end = timeIntervals[i + 1];
					if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
						continue;
					} else if (StringUtils.isBlank(start) && StringUtils.isNotBlank(end)
							&& DateUtils.inTimeRange(checkTime, "00:00", end, DateUtils.DATE_FMT_DATE_TIME_3)) {
						return true;
					} else if (StringUtils.isNotBlank(start) && StringUtils.isBlank(end)
							&& DateUtils.inTimeRange(checkTime, start, "23:59", DateUtils.DATE_FMT_DATE_TIME_3)) {
						return true;
					} else if (DateUtils.inTimeRange(checkTime, start, end, DateUtils.DATE_FMT_DATE_TIME_3)) {
						return true;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	private boolean isInRangeWeek(String[] weeks) {
		int today = DateUtils.week2Int(new Date());
		if (weeks == null || ArrayUtils.contains(weeks, String.valueOf(today))) {
			return true;
		}
		return false;
	}

	@Override
	public Result shift(PrincipalVo info) {
		List<Map<String, Object>> listData = gService.getAllSelectEnum(GenCodeConstants.SYSTEM_HCOM,
				GenCodeConstants.SHIFT, LoginInfoHolder.getLoginGrpId(), LoginInfoHolder.getLoginHotelId());
		Result result = Result.SUCCESS;
		Map<String, Object> data = new HashMap<>(2);
		// 班次只取前五条启动状态的数据
		listData = listData.stream().filter(s -> StringUtils.equals("1.0", String.valueOf(s.get("NumVal9")))).limit(5)
				.collect(Collectors.toList());

		data.put("shift", listData);

		Staff staff = LoginInfoHolder.getLoginInfo();
		if(info != null){
			staff.setShiftId(info.getShiftId());
		}

		final String shiftId = staff.getShiftId();
		Map<String, Object> current = null;
		try {
			current = listData.stream().filter(map -> StringUtils.equals((String) map.get("id"), shiftId)).limit(1)
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			//过滤班次列表，过滤掉非当前时间的班次。
			List<Map<String,Object>> currentList = listData.stream().filter(map -> {
				String start = (String) map.get("StrVal3");
				String end = (String) map.get("StrVal4");
				//当前系统时间在此班次内
				if (StringUtils.isNoneBlank(start) && StringUtils.isNotBlank(end)
						&& isInRangeTime(new String[] { start, end })) {
					return true;
				} else {
					return false;
				}
			}).limit(1).collect(Collectors.toList());
			if(currentList != null && currentList.size() > 0){
				current = currentList.get(0);
			}else if(current == null && listData.size() > 0) {
				current = listData.get(0);
			}
		}
		data.put("current", current);
		result.setResult(data);
		return result;
	}

	@Override
	public List<Staff> getStaffInfoByAccnos(@RequestBody List<String> accnos, String hotelId) {
		if (null != accnos && !accnos.isEmpty()) {
			List<PermsTStaff> permsTStaffList = permTStaffMapper.getStaffInfoByAccnos(accnos, hotelId);
			if (null != permsTStaffList && !permsTStaffList.isEmpty()) {
				return BeanUtils.copyDbBeanList2ServiceBeanList(permsTStaffList, Staff.class);
			}
		}
		return new ArrayList<Staff>();
	}

	@Override
	public List<String> getStaffRoleId(String staffId, String hotelId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("staffId", staffId);
		paramMap.put("hotelId", hotelId);
		List<String> roleIdList = permTStaffMapper.getStaffRoleId(paramMap);
		return roleIdList;
	}

	@Override
	public List<String> getRolePermissionList(@RequestBody List<String> roleIdList, String hotelId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleIdList", roleIdList);
		paramMap.put("hotelId", hotelId);
		List<String> permsTRolePermissionList = permTStaffMapper.getRolePermissionList(paramMap);
		return permsTRolePermissionList;
	}

	@Override
	public List<Staff> getStaffInfoByDeptId(String deptId, String hotelId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptId", deptId);
		paramMap.put("hotelId", hotelId);
		List<PermsTStaff> permsTStaffList = permTStaffMapper.getStaffInfoByDeptId(paramMap);
		List<Staff> staffList = new ArrayList<Staff>();
		if (permsTStaffList != null && permsTStaffList.size() > 0) {
			staffList = BeanUtils.copyDbBeanList2ServiceBeanList(permsTStaffList, Staff.class);
		}
		return staffList;
	}

	@Override
	public void registerHuanxin() {
		PermsTStaffExample example = new PermsTStaffExample();
		PermsTStaffExample.Criteria criteria = example.createCriteria();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClStatusNotEqualTo(Constants.STATUS_AUDIT);
        // criteria.andClHotelIdEqualTo("5ab7b6890aff46f38c93fef5fb29e0ee");
        // criteria.andClHotelIdEqualTo("44933fc89af54238a04bdbd5c8c6a4e9");
		List<PermsTStaff> permsTStaffList = permTStaffMapper.selectByExample(example);
		for (PermsTStaff staff : permsTStaffList) {
			User registerUser = new User();
			registerUser.setUsername(staff.getClId());
			registerUser.setPassword(HashUtils.encodeForHuanXin(staff.getClId()));
			imUserAPI.createNewIMUserSingle(registerUser);
			Nickname nickName = new Nickname();
			String userNickName = StringUtils.EMPTY;
			if (!StringUtils.isEmpty(staff.getClFamilyName())) {
				userNickName += staff.getClFamilyName();
			}
			if (!StringUtils.isEmpty(staff.getClName())) {
				userNickName += staff.getClName();
			}
			nickName.setNickname(userNickName);
			imUserAPI.modifyIMUserNickNameWithAdminToken(staff.getClId(), nickName);
		}
		
	}
    
    @Override
    public void registerHuanxinByHotelId(String hotelId)
    {
        PermsTStaffExample example = new PermsTStaffExample();
        PermsTStaffExample.Criteria criteria = example.createCriteria();
        criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
        criteria.andClStatusNotEqualTo(Constants.STATUS_AUDIT);
        // criteria.andClHotelIdEqualTo("5ab7b6890aff46f38c93fef5fb29e0ee");
        criteria.andClHotelIdEqualTo(hotelId);
        List<PermsTStaff> permsTStaffList = permTStaffMapper.selectByExample(example);
        for (PermsTStaff staff : permsTStaffList)
        {
            User registerUser = new User();
            registerUser.setUsername(staff.getClId());
            registerUser.setPassword(HashUtils.encodeForHuanXin(staff.getClId()));
            imUserAPI.createNewIMUserSingle(registerUser);
            Nickname nickName = new Nickname();
            String userNickName = StringUtils.EMPTY;
            if (!StringUtils.isEmpty(staff.getClFamilyName()))
            {
                userNickName += staff.getClFamilyName();
            }
            if (!StringUtils.isEmpty(staff.getClName()))
            {
                userNickName += staff.getClName();
            }
            nickName.setNickname(userNickName);
            imUserAPI.modifyIMUserNickNameWithAdminToken(staff.getClId(), nickName);
        }
        
    }
    
	@Override
	public Staff findByIdOrAccount(@RequestBody Staff staff) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idOrAccount", staff.getId());
		PermsTStaff permsTStaff = permTStaffMapper.findByIdOrAccount(paramMap);
		if (permsTStaff == null) {
			return new Staff();
		}
		
		return BeanUtils.copyDbBean2ServiceBean(permsTStaff, Staff.class);
	}
}
