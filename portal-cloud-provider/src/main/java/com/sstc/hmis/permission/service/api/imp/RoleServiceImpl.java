package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.logger.threadlocal.LoggerInfoHolder;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.RolePermission;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.dbaccess.dao.PermsTGroupHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRolePermissionMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffRoleMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTRoleExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTRoleExample.Criteria;
import com.sstc.hmis.permission.dbaccess.data.PermsTRolePermission;
import com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

@RestController
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Autowired
	private PermsTRoleMapper permsTRoleMapper;
	@Autowired
	private PermsTStaffRoleMapper permsTStaffRoleMapper;
	@Autowired
	private PermsTRolePermissionMapper permsTRolePermissionMapper;
	@Autowired
	private PermsTGroupHotelMapper permsTGroupHotelMapper;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	

	/*
	 * 查询所有角色信息
	 */
	@Override
	public PageResult<Role> findAllRole(@RequestBody Role role ,int pageIndex, int pageSize) {
		PageResult<Role> retData = new PageResult<Role>();
		// 分页
		PageHelper.startPage(pageIndex + 1, pageSize);
		
		PermsTRole permsTRole = this.convertRole2PermsTRole(role);
		List<Role> list = new ArrayList<Role>();
		Page<PermsTRole> page = (Page<PermsTRole>)permsTRoleMapper.findAllRole(permsTRole);
		String hotelId = role.getBelongId();
		if(StringUtils.isBlank(hotelId)){
			hotelId = LoginInfoHolder.getLoginHotelId();
		}
		PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelId);
		String name = permsTGroupHotel.getClName();
		for(PermsTRole permsTRole2 : page.getResult()){
			// 通过归属集团或酒店id查询名称
			Role role2 = this.convertPermsTRole2Role(permsTRole2);
			if(StringUtils.isNotBlank(name)) {
				role2.setBelongName(name);
			}
			list.add(role2);
		}
		retData.setResult(true);
		retData.setResults(page.getTotal());
		retData.setRows(list);
		return retData;
	}

	/*
	 * PermsTRole转换Role实体类
	 */
	private Role convertPermsTRole2Role(PermsTRole permsTRole){
		try {
			return BeanUtils.copyDbBean2ServiceBean(permsTRole, new Role());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
	
	/*
	 * PermsTRolePermission转换RolePermission实体类
	 */
	private RolePermission convertPermsTRolePermission2RolePermission(PermsTRolePermission permsTRolePermission){
		try {
			return BeanUtils.copyDbBean2ServiceBean(permsTRolePermission, new RolePermission());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 通过角色id删除
	 */
	@Override
	public void deleteRoleById(String id) {
	/*	// 删除员工角色关联信息表
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
		criteria1.andClRoleIdEqualTo(id);
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		if(permsTStaffRoleList.size() > 0) {
			for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList){
//				String staffRoleId = permsTStaffRole.getClId();
				permsTStaffRole.setClStatus(Constants.STATUS_DEL);
//				permsTStaffRoleMapper.deleteByPrimaryKey(staffRoleId);
				permsTStaffRoleMapper.updateByPrimaryKeySelective(permsTStaffRole);
			}
		}
		
		// 删除角色和权限关联关系表
		PermsTRolePermissionExample permsTRolePermissionExample = new PermsTRolePermissionExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample.Criteria criteria2 = permsTRolePermissionExample.createCriteria();
		criteria2.andClRoleIdEqualTo(id);
		List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.selectByExample(permsTRolePermissionExample);
		if(permsTRolePermissionList.size() > 0) {
			for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList){
//				String rolePermissionId = permsTRolePermission.getClId();
//				permsTRolePermissionMapper.deleteByPrimaryKey(rolePermissionId);
				permsTRolePermission.setClStatus(Constants.STATUS_DEL);
				permsTRolePermissionMapper.updateByPrimaryKeySelective(permsTRolePermission);
			}
		}*/
		
		// 删除角色表信息
		PermsTRole role = new PermsTRole();
		role.setClId(id);
		role.setClStatus(Constants.STATUS_DEL);
		permsTRoleMapper.updateByPrimaryKeySelective(role);
	}

	/*
	 * 批量删除
	 */
	@Override
	public void batchDeleteRole(String ids) {
		String[] arr = ids.split(",");
		for(int i=0; i<arr.length; i++) {
			this.deleteRoleById(arr[i]);
		}
	}

	/*
	 * 右侧角色加载
	 */
	@Override
	public List<Role> loadRightRoleList(String staffId,@RequestBody List<String> hotelIds) {
		PermsTStaffRole sr = new PermsTStaffRole();
		sr.setClStaffId(staffId);
		if(hotelIds != null && hotelIds.size()> 0){
			sr.setHotelIds(hotelIds);
		} else {
			sr.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
		}

		List<PermsTRole> permsRoleList = permsTRoleMapper.findRoleByStaffIdRight(sr);
		List<Role> roleList = new ArrayList<Role>();
		for(PermsTRole permsTRole : permsRoleList){
			Role role = this.convertPermsTRole2Role(permsTRole);
			PermsTGroupHotel permsTGroupHotel  = permsTGroupHotelMapper.selectByPrimaryKey(role.getHotelId());
			role.setBelongName(permsTGroupHotel.getClName());
			roleList.add(role);
		}
		return roleList;
	}
	
	/*
	 * 左侧角色加载
	 */
	@Override
	public List<Role> loadLeftRoleList(String staffId,@RequestBody List<String> hotelIds) {
		PermsTStaffRole sr = new PermsTStaffRole();
		sr.setClStaffId(staffId);
		if(hotelIds != null && hotelIds.size()> 0){
			sr.setHotelIds(hotelIds);
		} else {
			sr.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
		}
		List<PermsTRole> permsRoleList = permsTRoleMapper.findRoleByStaffIdLeft(sr);
		List<Role> roleList = new ArrayList<Role>();
		for(PermsTRole permsTRole : permsRoleList){
			Role role = this.convertPermsTRole2Role(permsTRole);
			PermsTGroupHotel permsTGroupHotel  = permsTGroupHotelMapper.selectByPrimaryKey(role.getHotelId());
			role.setBelongName(permsTGroupHotel.getClName());
			roleList.add(role);
		}
		return roleList;
	}

	/*
	 * 用户添加角色信息
	 */
	@Override
	public void saveStaffRoleList(String staffId, String[] roleIds) {
		// 根据用户id删除用户角色表信息（解除该用户下的所有角色）
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
		criteria1.andClStaffIdEqualTo(staffId);
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
			String clId = permsTStaffRole.getClId();
			permsTStaffRoleMapper.deleteByPrimaryKey(clId);
		}
		// 保存该用户下新添加的角色列表信息
		for(int i=0; i<roleIds.length; i++) {
			PermsTStaffRole sr = new PermsTStaffRole();
			sr.setClId(HashUtils.uuidGenerator()); // id
			sr.setClStaffId(staffId); // 用户id
			sr.setClRoleId(roleIds[i]); // 角色id
			sr.setClStatus(Constants.STATUS_NORMAL); 
			sr.setClBlockup(Constants.BLOCKUP_NO);
			PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleIds[i]);
			sr.setClHotelId(permsTRole.getClHotelId());
			sr.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
			//sr.setClCreateTime(new Date());
			sr.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
			permsTStaffRoleMapper.insertSelective(sr);
		}
	}
	
	
	
	/*
	 * 角色添加用户信息
	 */
	@Override
	public void saveRoleStaffList(String roleId, String[] staffIds) {
		// 根据角色id删除用户信息（解决该角色下的所有用户信息）
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
		criteria1.andClRoleIdEqualTo(roleId);
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
			String clId = permsTStaffRole.getClId();
			permsTStaffRoleMapper.deleteByPrimaryKey(clId);
		}
		// 保存该角色下新添加的员工列表信息
		for(int i=0; i<staffIds.length; i++) {
			PermsTStaffRole sr = new PermsTStaffRole();
			sr.setClId(HashUtils.uuidGenerator()); // id
			sr.setClStaffId(staffIds[i]); // 用户id
			sr.setClRoleId(roleId); // 角色id
			sr.setClStatus(Constants.STATUS_NORMAL); 
			sr.setClBlockup(Constants.BLOCKUP_NO);
			sr.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
			sr.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
			//sr.setClCreateTime(new Date());
			sr.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
			permsTStaffRoleMapper.insertSelective(sr);
		}
	}
	
	@Override
	public void saveRoleStaffList2(String roleId, String[] staffIds, String hotelId) {
		// 根据角色id删除用户信息（解决该角色下的所有用户信息）
				PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
				com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
				criteria1.andClRoleIdEqualTo(roleId);
				criteria1.andClHotelIdEqualTo(hotelId);
				List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
				for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
					String clId = permsTStaffRole.getClId();
					permsTStaffRoleMapper.deleteByPrimaryKey(clId);
				}
				// 保存该角色下新添加的员工列表信息
				if(staffIds != null && staffIds.length > 0){
					for(int i=0; i<staffIds.length; i++) {
						PermsTStaffRole sr = new PermsTStaffRole();
						sr.setClId(HashUtils.uuidGenerator()); // id
						sr.setClStaffId(staffIds[i]); // 用户id
						sr.setClRoleId(roleId); // 角色id
						sr.setClStatus(Constants.STATUS_NORMAL); 
						sr.setClBlockup(Constants.BLOCKUP_NO);
						
						sr.setClHotelId(hotelId);
						sr.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
						//sr.setClCreateTime(new Date());
						sr.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
						permsTStaffRoleMapper.insertSelective(sr);
					}
				}
				
	}

	/*
	 * 添加角色信息
	 */
	@Override
	public Result saveRole(@RequestBody Role role, String[] permissionId){
		String belongId = role.getBelongId();
		String chinaName = role.getName();
		if(StringUtils.isNotBlank(belongId)) {
			PermsTRoleExample example = new PermsTRoleExample();
			Criteria criteria = example.createCriteria();
			criteria.andClBelongIdEqualTo(belongId);
			criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
			criteria.andClNameEqualTo(chinaName);
			int count = permsTRoleMapper.countByExample(example);
			if(count > 0){
				return Result.ERROR_REPEAT;
			}
		}
		
		// 保存角色表信息
		if(role != null) {
			role.setId(HashUtils.uuidGenerator());
			role.setStatus(Constants.STATUS_NORMAL); // 正常
			role.setBlockup(Constants.BLOCKUP_NO); // 未停用
			role.setGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
			role.setCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
			PermsTRole permsRole = this.convertRole2PermsTRole(role);
			int line = permsTRoleMapper.insertSelective(permsRole);
			if(line > 0){
				LoggerInfoHolder.setLoggerInfoData(null, role);
				if(permissionId != null && permissionId.length > 0) {
					// 保存角色和权限关联关系表信息
					short hasGovern = new Short("0");
					for(int i=0; i<permissionId.length; i++){
						PermsTRolePermission permission = new PermsTRolePermission();
						permission.setClId(HashUtils.uuidGenerator());
						permission.setClRoleId(role.getId());
						permission.setClPermissionId(permissionId[i]);
						permission.setClStatus(Constants.STATUS_NORMAL); // 正常
						permission.setClBlockup(Constants.BLOCKUP_NO); // 未停用
						permission.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
						permission.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
						permission.setClHasGovern(hasGovern);
						permission.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
						permsTRolePermissionMapper.insertSelective(permission);
					}
				}
				return Result.SUCCESS;
			}else{
				LoggerInfoHolder.setFailResult();
			}
		}
		return Result.ERROR_PARAMS;
	}

	/*
	 * 修改角色信息
	 */
	@Override
	public void updateRole(@RequestBody Role role, String[] permissionId)throws AppException {
		String roleId = role.getId();
		String belongId = role.getBelongId();
		String chinaName = role.getName();
		int effectLine = 0;
		if(StringUtils.isNotBlank(roleId)){
			//判断角色名是否重复
			if(StringUtils.isNotBlank(belongId)) {
				PermsTRoleExample example = new PermsTRoleExample();
				Criteria criteria = example.createCriteria();
				criteria.andClBelongIdEqualTo(belongId);
				List<Short> status = new ArrayList<Short>();
				status.add(Constants.STATUS_NORMAL);
				status.add(Constants.STATUS_LOCK);
				criteria.andClStatusIn(status);
				criteria.andClNameEqualTo(chinaName);
				criteria.andClIdNotEqualTo(roleId);
				effectLine = permsTRoleMapper.countByExample(example);
				if(effectLine > 0){
					LOG.debug("酒店{}下已存在角色{}");
					throw new AppException(400, "同一酒店下不能有相同的角色名称");
				}
			}
			// 根据角色id修改角色表信息
			PermsTRole permsRole = this.convertRole2PermsTRole(role);
			permsRole.setClModifyTime((new Date()));
			permsRole.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
			effectLine = permsTRoleMapper.updateByPrimaryKeySelective(permsRole);
			if(permissionId != null && permissionId.length > 0) {
				// 1,将上一次的所有权限设为删除状态
				PermsTRolePermission PermsTRolePermission1 = new PermsTRolePermission();
				PermsTRolePermission1.setClStatus(Constants.STATUS_DEL);
				PermsTRolePermissionExample permsTRolePermissionExample1 = new PermsTRolePermissionExample();
				PermsTRolePermissionExample.Criteria criteria1 = permsTRolePermissionExample1.createCriteria();
				criteria1.andClRoleIdEqualTo(roleId);
				criteria1.andClStatusEqualTo(Constants.STATUS_NORMAL);
				effectLine = permsTRolePermissionMapper.updateByExampleSelective(PermsTRolePermission1, permsTRolePermissionExample1);
				LOG.debug("更新原权限数据{}条",effectLine);
				
				// 2,将没有变的权限重置回正常状态
				PermsTRolePermission PermsTRolePermission2 = new PermsTRolePermission();
				PermsTRolePermission2.setClStatus(Constants.STATUS_NORMAL);
				PermsTRolePermission2.setClModifyTime(new Date());
				PermsTRolePermission2.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				PermsTRolePermissionExample permsTRolePermissionExample2 = new PermsTRolePermissionExample();
				PermsTRolePermissionExample.Criteria criteria2 = permsTRolePermissionExample2.createCriteria();
				criteria2.andClRoleIdEqualTo(roleId); 
				List<String> permissionIdList = new ArrayList<>(Arrays.asList(permissionId));
				criteria2.andClPermissionIdIn(permissionIdList);
				permsTRolePermissionMapper.updateByExampleSelective(PermsTRolePermission2, permsTRolePermissionExample2);
				LOG.debug("未变化的权限{}条",effectLine);
				
				// 3,查询正常的permissionId然后从选择的permissionIds中剔除，剩余的就是新增的赋权
				PermsTRolePermissionExample permsTRolePermissionExample4 = new PermsTRolePermissionExample();
				PermsTRolePermissionExample.Criteria criteria4 = permsTRolePermissionExample4.createCriteria();
				criteria4.andClRoleIdEqualTo(roleId);
				criteria4.andClStatusEqualTo(Constants.STATUS_NORMAL);
				List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.selectByExample(permsTRolePermissionExample4);
				List<String> removeList = null;
				List<String> permIdList = new ArrayList<>();
				permIdList.addAll(permissionIdList);
				if(permsTRolePermissionList.size() > 0){
					removeList = new ArrayList<String>();
					for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList){
						removeList.add(permsTRolePermission.getClPermissionId());
					}
					permIdList.removeAll(removeList);
				}
				
				int addSize = permIdList.size();
				if(addSize > 0){
					List<PermsTRolePermission> newAddPermission = new ArrayList<>(addSize);
					for(int i = 0; i < addSize; i++){
						PermsTRolePermission permission = new PermsTRolePermission();
						permission.setClId(HashUtils.uuidGenerator());
						permission.setClRoleId(roleId);
						permission.setClPermissionId(permIdList.get(i));
						permission.setClStatus(Constants.STATUS_NORMAL); 
						permission.setClBlockup(Constants.BLOCKUP_NO); // 未停用
						permission.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
						permission.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
						permission.setClHasGovern((short) 0);
						permission.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
						newAddPermission.add(permission);
					}
					effectLine = permsTRolePermissionMapper.batchInsertSelective(newAddPermission);
					LOG.debug("本次新增权限数据{}条",effectLine);
				}
				
				// 4,物理删除取消的权限
				PermsTRolePermissionExample permsTRolePermissionExample3 = new PermsTRolePermissionExample();
				com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample.Criteria criteria3 = permsTRolePermissionExample3.createCriteria();
				criteria3.andClRoleIdEqualTo(roleId);
				criteria3.andClStatusEqualTo(Constants.STATUS_DEL);
				permsTRolePermissionMapper.deleteByExample(permsTRolePermissionExample3);
				LOG.debug("本次删除权限数据{}条",effectLine);
			}else {
				PermsTRolePermissionExample permsTRolePermissionExample5 = new PermsTRolePermissionExample();
				com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample.Criteria criteria5 = permsTRolePermissionExample5.createCriteria();
				criteria5.andClRoleIdEqualTo(roleId);
				permsTRolePermissionMapper.deleteByExample(permsTRolePermissionExample5);
			}
		}
		
	}

	/*
	 * 编辑前
	 */
	@Override
	public Role editBeforeRole(String roleId) {
		// 通过角色id查询角色信息
		PermsTRole permsRole = permsTRoleMapper.selectByPrimaryKey(roleId);
		Role role = this.convertPermsTRole2Role(permsRole);
		// 通过角色id查询权限id
		List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.findPermissionIdByRoleId(roleId);
		List<RolePermission> list = new ArrayList<RolePermission>();
		for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList){
			RolePermission rolePermission = this.convertPermsTRolePermission2RolePermission(permsTRolePermission);
			list.add(rolePermission);
		}
		role.setRolePermissionList(list);
		return role;
	}
	
	/*
	 * 通过角色id查询用户id列表
	 */
	@Override
	public List<String> findStaffIdListByRoleId(String roleId) {
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
		criteria1.andClRoleIdEqualTo(roleId);
		criteria1.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<String> staffIdList = new ArrayList<String>();
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
			String staffId = permsTStaffRole.getClStaffId();
			staffIdList.add(staffId);
		}
		return staffIdList;
	}


	/* (non-Javadoc)
	 * @see com.sstc.hmis.cas.common.service.CasRoleService#findUserRole(java.lang.String)
	 */
	@Override
	public Set<String> findUserRole(@RequestBody Staff staff) {
		Set<String> roleIds = new HashSet<String>(); 
		if(staff != null && StringUtils.isNoneBlank(staff.getId())){
			PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
			PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
			criteria1.andClStatusEqualTo(Constants.STATUS_NORMAL);
			criteria1.andClStaffIdEqualTo(staff.getId());
			criteria1.andClHotelIdEqualTo(staff.getHotelId());
			List<PermsTStaffRole> list = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
			
			//员工的角色ID列表
			for (PermsTStaffRole permsTStaffRole : list) {
				roleIds.add(permsTStaffRole.getClRoleId());
			}
		}
		return roleIds;
	}

	/*
	 * 更新角色状态
	 */
	@Override
	public String updateRoleStatus(String roleId) {
		// 定义标识
		String flag = "";
		PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleId);
		Short clStatus = permsTRole.getClStatus();
		String db_roleId = permsTRole.getClId();
		if(clStatus == 0) {
			permsTRole.setClStatus(Constants.STATUS_LOCK);
			// 修改用户角色关联表状态
			PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
			criteria1.andClRoleIdEqualTo(db_roleId);
			List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
			for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
				permsTStaffRole.setClStatus(Constants.STATUS_LOCK);
				permsTStaffRole.setClModifyTime(new Date());
				permsTStaffRole.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTStaffRoleMapper.updateByPrimaryKeySelective(permsTStaffRole);
			}
			// 修改角色权限关联表状态
			PermsTRolePermissionExample permissionExample = new PermsTRolePermissionExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample.Criteria criteria2 = permissionExample.createCriteria();
			criteria2.andClRoleIdEqualTo(db_roleId);
			List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.selectByExample(permissionExample);
			for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList) {
				permsTRolePermission.setClStatus(Constants.STATUS_LOCK);
				permsTRolePermission.setClModifyTime(new Date());
				permsTRolePermission.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTRolePermissionMapper.updateByPrimaryKeySelective(permsTRolePermission);
			}
			flag = "lock";
		} else if(clStatus == 2){
			permsTRole.setClStatus(Constants.STATUS_NORMAL);
			permsTRole.setClModifyTime(new Date());
			permsTRole.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
			// 修改用户角色关联表状态
			PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
			criteria1.andClRoleIdEqualTo(db_roleId);
			List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
			for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
				permsTStaffRole.setClStatus(Constants.STATUS_NORMAL);
				permsTStaffRole.setClModifyTime(new Date());
				permsTStaffRole.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTStaffRoleMapper.updateByPrimaryKeySelective(permsTStaffRole);
			}
			// 修改角色权限关联表状态
			PermsTRolePermissionExample permissionExample = new PermsTRolePermissionExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTRolePermissionExample.Criteria criteria2 = permissionExample.createCriteria();
			criteria2.andClRoleIdEqualTo(db_roleId);
			List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.selectByExample(permissionExample);
			for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList) {
				permsTRolePermission.setClStatus(Constants.STATUS_NORMAL);
				permsTRolePermission.setClModifyTime(new Date());
				permsTRolePermission.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTRolePermissionMapper.updateByPrimaryKeySelective(permsTRolePermission);
			}
			flag = "normal";
		}
		
		// 修改角色表状态
		permsTRoleMapper.updateByPrimaryKeySelective(permsTRole);
		return flag;
	
	
	}

	@Override
	public List<Role> findRoleByBelongId(String belongId,String roleName,String postIds) {
		PermsTRoleExample example = new PermsTRoleExample();
		Criteria criteria = example.createCriteria();
		criteria.andClBelongIdEqualTo(belongId);
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		if(StringUtils.isNotBlank(roleName)){
			criteria.andClNameLike("%"+roleName.trim()+"%");
		}
		if(StringUtils.isNotBlank(postIds)){
			String[] postIdx = postIds.split(",");
			List<String> ids = new ArrayList<String>();
			ids = Arrays.asList(postIdx);
			criteria.andClIdNotIn(ids);
		}
		List<Role> roleList = new ArrayList<Role>();
		List<PermsTRole> permsTRoleList = permsTRoleMapper.selectByExample(example);
		for(PermsTRole permsTRole : permsTRoleList){
			String clBelongId = permsTRole.getClBelongId();
			// 通过归属集团或酒店id查询名称
			PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(clBelongId);
			String name = permsTGroupHotel.getClName();
			Role role = this.convertPermsTRole2Role(permsTRole);
			role.setBelongName(name);
			roleList.add(role);
		}
		return roleList;
	}

	@Override
	public List<Role> findRoleByIds(String[] roleIds) {
		List<Role> roleList = new ArrayList<Role>();
		for(int i=0; i<roleIds.length; i++){
			PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleIds[i]);
			String clBelongId = permsTRole.getClBelongId();
			// 通过归属集团或酒店id查询名称
			PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(clBelongId);
			String name = permsTGroupHotel.getClName();
			Role role = this.convertPermsTRole2Role(permsTRole);
			role.setBelongName(name);
			roleList.add(role);
		}
		return roleList;
	}

	/**
	 * 查询本酒店以外的权限信息
	 * @author CKang
	 * @date 2017年4月21日 上午11:09:17
	 * @param role
	 * @return
	 */
	@Override
	public List<Role> findNotLocalRoleList(@RequestBody Role role) {
			PermsTRole permsTRole = this.convertRole2PermsTRole(role);
			List<String> idx = role.getIds();
			if(idx != null && role.getIds().size() > 0){
				permsTRole.setIds(role.getIds());
			}
			
			List<Role> roleList = new ArrayList<Role>();
			List<PermsTRole> permsTRoleList = permsTRoleMapper.findNotLocalRoleList(permsTRole);
			for(PermsTRole permsTRole2 : permsTRoleList){
				roleList.add(this.convertPermsTRole2Role(permsTRole2));
			}
			roleList = this.noRepeat(roleList);
			return roleList;
	}

	/*
	 * 复制角色
	 */
	@Override
	public void copyRole(String roleIds) throws AppException {
		String[] roleIdx = roleIds.split(",");
		for(int i=0; i<roleIdx.length; i++){
			PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleIdx[i]);
			String belongId = permsTRole.getClBelongId();
			String chinaName = permsTRole.getClName();
			//String englishName = role.getEnglishName();
			if(StringUtils.isNotBlank(belongId)) {
				PermsTRoleExample example = new PermsTRoleExample();
				Criteria criteria = example.createCriteria();
				
				criteria.andClBelongIdEqualTo(LoginInfoHolder.getLoginInfo().getHotelId());
				List<Short> stauts = new ArrayList<Short>();
				stauts.add(Constants.STATUS_NORMAL);
				stauts.add(Constants.STATUS_LOCK);
				criteria.andClStatusIn(stauts);
				List<PermsTRole> permsTRoleList = permsTRoleMapper.selectByExample(example);
				for(PermsTRole permsTRole2 : permsTRoleList){
					String db_chinaName = permsTRole2.getClName();
					if(StringUtils.equals(chinaName,db_chinaName)) {
						throw new AppException(400, "同一酒店下不能有相同的角色名称");
					}
				}
			}
		}
		
		
		
		
		
		for(int i=0; i<roleIdx.length; i++){
			PermsTRole permsTRole = permsTRoleMapper.selectByPrimaryKey(roleIdx[i]);
			
			// 保存角色信息
			permsTRole.setClId(HashUtils.uuidGenerator());
			permsTRole.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
			//permsTRole.setClCreateTime(new Date());
			permsTRole.setClBelongId(LoginInfoHolder.getLoginInfo().getHotelId());
			permsTRole.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
			permsTRole.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
			permsTRoleMapper.insertSelective(permsTRole);
			// 保存角色和权限关系表信息
			List<PermsTRolePermission> permsTRolePermissionList = permsTRolePermissionMapper.findPermissionIdByRoleId(roleIdx[i]);
			for(PermsTRolePermission permsTRolePermission : permsTRolePermissionList){
				permsTRolePermission.setClId(HashUtils.uuidGenerator());
				permsTRolePermission.setClRoleId(permsTRole.getClId());
				//permsTRolePermission.setClCreateTime(new Date());
				permsTRolePermission.setClCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
				permsTRolePermission.setClHotelId(LoginInfoHolder.getLoginInfo().getHotelId());
				permsTRolePermission.setClGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
				permsTRolePermission.setClStatus(Constants.STATUS_NORMAL);
				permsTRolePermission.setClBlockup(Constants.BLOCKUP_NO);
				permsTRolePermission.setClHasGovern(new Short("0"));
				permsTRolePermissionMapper.insertSelective(permsTRolePermission);
			}
		}
		
		
	}
	/**
	 * 去掉角色名称相同的对象
	 * @author CKang
	 * @date 2017年4月21日 下午4:18:38
	 * @return
	 */
	@Override
	public List<Role> noRepeat(@RequestBody List<Role> list) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, String> map2 = new HashMap<String, String>();
		for(Role role : list){
			 Integer count = 1;
	         if(map.get(role.getName()) != null){
	        	 count = map.get(role.getName())+1;
	         }
	         map.put(role.getName(),count);
	         map2.put(role.getName(),role.getId()+","+count.toString());
		}
		List<Role> list2 = new ArrayList<Role>();
		for(Map.Entry<String, String> entry : map2.entrySet()){
			String value = entry.getValue();
			String [] ary= value.split(",");
			if(Integer.parseInt(ary[1]) >= 1){
				for(Role Role : list){
					if(Role.getId().equals(ary[0])){
						list2.add(Role);
					}
				}
			}
		}
		return list2;
	}

	/**
	 * 通过角色id查询有效的员工的数量
	 * @author CKang
	 * @date 2017年4月26日 下午4:50:44
	 * @param role
	 * @return
	 */
	@Override
	public int findStaffCountByRoleId(@RequestBody Role role) {
		return permsTRoleMapper.findStaffCountByRoleId(convertRole2PermsTRole(role));
	}

	@Override
	public List<String> findStaffIdListByRoleIdAndHotelId(String roleId, String hotelId) {
		PermsTStaffRoleExample permsTStaffRoleExample = new PermsTStaffRoleExample();
		PermsTStaffRoleExample.Criteria criteria1 = permsTStaffRoleExample.createCriteria();
		criteria1.andClRoleIdEqualTo(roleId);
		criteria1.andClHotelIdEqualTo(hotelId);
		criteria1.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<String> staffIdList = new ArrayList<String>();
		List<PermsTStaffRole> permsTStaffRoleList = permsTStaffRoleMapper.selectByExample(permsTStaffRoleExample);
		for(PermsTStaffRole permsTStaffRole : permsTStaffRoleList) {
			String staffId = permsTStaffRole.getClStaffId();
			staffIdList.add(staffId);
		}
		return staffIdList;
	}

	@Override
	public Role findRoleById(String roleId) {
		return convertPermsTRole2Role(permsTRoleMapper.selectByPrimaryKey(roleId));
	}


}
