package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.mdata.common.service.api.easemob.ChatGroupAPI;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.GrpMember;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDepartmentMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTGroupHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffTeamMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTWorkTeamMapper;
import com.sstc.hmis.permission.dbaccess.data.PermGrpMember;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTRoleExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaff;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeam;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeamExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeamExample.Criteria;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.service.RoleService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

import io.swagger.client.model.Group;
import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.NewOwner;
/**
 * 邀请成员展示Grid表
 * 
 * @author xuzhichuan 创建时间 2017年4月6日
 */
@RestController
public class WorkTeamServiceImpl extends BaseServiceImpl<WorkTeam> implements WorkTeamService{
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkTeamServiceImpl.class);
	
	@Autowired
	private PermsTWorkTeamMapper pWorkTeamMapper;
	@Autowired
	private PermsTStaffTeamMapper pStaffTeamMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermsTStaffHotelMapper pStaffHotelMapper;
	@Autowired
	private PermsTStaffMapper pStaffMapper;
	@Autowired
	private PermsTDepartmentMapper pDepartmentMapper;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PermsTRoleMapper permsTRoleMapper;
	@Autowired
	private PermsTGroupHotelMapper permsTGroupHotelMapper;
	@Autowired
	private PermsTStaffTeamMapper permsTStaffTeamMapper;
	
	@Autowired
	private ChatGroupAPI chatGroupAPI;
	
	@Autowired
	private PermsTWorkTeamMapper permsTWorkTeamMapper;

	
	private WorkTeam convertPermsTWorkTeam2WorkTeam(PermsTWorkTeam permsTWorkTeam){
		try {
			return BeanUtils.copyDbBean2ServiceBean(permsTWorkTeam, new WorkTeam());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PermsTWorkTeam convertWorkTeam2PermsTWorkTeam(WorkTeam workTeam){
		try {
			return BeanUtils.copyServiceBean2DbBean(workTeam, new PermsTWorkTeam());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public WorkTeam find(@RequestBody WorkTeam t) {
		String id = t.getId();
		PermsTWorkTeam permsTWorkTeam = pWorkTeamMapper.selectByPrimaryKey(id);
		return convertPermsTWorkTeam2WorkTeam(permsTWorkTeam);
	}
	
	@Override
	public int update(@RequestBody WorkTeam t) {
		
		// add by 刘溶容 2018/01/11 for 集成环信 begin
		// 更新
		// 判断当前工作圈是否已存在群
		PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(t.getId());
		String groupId = permsTWorkTeam.getClEasemobGroupId();
		LOG.debug("===========================溶容的日志：" + groupId);
		if (t.getStatus() == null || t.getStatus() != Constants.STATUS_DEL) {
			if (!StringUtils.isEmpty(groupId)) {
				// 存在群 则更新环信群组名称
				if (!permsTWorkTeam.getClName().equals(t.getName())) {
					ModifyGroup mg = new ModifyGroup();
					mg.setGroupname(t.getName());
					chatGroupAPI.modifyChatGroup(groupId, mg);
				}
			}
		} else if (t.getStatus() == Constants.STATUS_DEL) {
			// 删除
			if (!StringUtils.isEmpty(groupId)) {
				chatGroupAPI.deleteChatGroup(groupId);
			}
		}
		// add by 刘溶容 2018/01/11 for 集成环信 end
		
		getTypeInfo(t);
		PermsTWorkTeamExample permsTWorkTeamExample = new PermsTWorkTeamExample();
		Criteria pWorkTeamWriteria = permsTWorkTeamExample.createCriteria();
		pWorkTeamWriteria.andClNameEqualTo(t.getName());
		pWorkTeamWriteria.andClBelongIdEqualTo(t.getBelongId());
		pWorkTeamWriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		pWorkTeamWriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pWorkTeamWriteria.andClIdNotEqualTo(t.getId()); //不等于自己
		List<PermsTWorkTeam> permsTWorkTeamList = pWorkTeamMapper.selectByExample(permsTWorkTeamExample);
		if(permsTWorkTeamList!=null && permsTWorkTeamList.size()>0) {
			return 2; //归属酒店下有同名的工作圈
		}
		int res = pWorkTeamMapper.updateByPrimaryKeySelective(convertWorkTeam2PermsTWorkTeam(t));
//		逻辑删除关联PERMS_T_STAFF_TEAM表
		Short status = t.getStatus();
		if (status != null && t.getStatus() == Constants.STATUS_DEL) {
			PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = permsTStaffTeamExample.createCriteria();
			pStaffTeamCriteria.andClTeamIdEqualTo(t.getId());
			pStaffTeamCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			pStaffTeamCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
			List<PermsTStaffTeam> permsTStaffTeamList = pStaffTeamMapper.selectByExample(permsTStaffTeamExample);
			if (permsTStaffTeamList != null && permsTStaffTeamList.size() > 0) {
				for (PermsTStaffTeam permsTStaffTeam : permsTStaffTeamList) {
					permsTStaffTeam.setClStatus(Constants.STATUS_DEL);
					pStaffTeamMapper.updateByPrimaryKey(permsTStaffTeam);
				}
			}
		}
		
		if (res == 1) {
			return 1;
		}else {
			return 0;
		}
	}

	
	@Override
	public int insert(@RequestBody WorkTeam t) {
		PermsTWorkTeamExample permsTWorkTeamExample = new PermsTWorkTeamExample();
		Criteria pWorkTeamWriteria = permsTWorkTeamExample.createCriteria();
		pWorkTeamWriteria.andClNameEqualTo(t.getName());
		pWorkTeamWriteria.andClBelongIdEqualTo(t.getBelongId());
		pWorkTeamWriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		pWorkTeamWriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		List<PermsTWorkTeam> permsTWorkTeamList = pWorkTeamMapper.selectByExample(permsTWorkTeamExample);
		if (permsTWorkTeamList != null && permsTWorkTeamList.size() > 0) {
			return 2;
		}
		
		Staff loginStaff = LoginInfoHolder.getLoginInfo();
		String uuId = HashUtils.uuidGenerator();
		String nameCh = t.getName();
		String englishName = t.getEnglishName();
		if (StringUtils.isNotBlank(nameCh)) {
			t.setName(nameCh.trim());
		}
		if (StringUtils.isNotBlank(englishName)) {
			t.setEnglishName(englishName.trim());
		}
		t.setId(uuId);
		getTypeInfo(t);
		t.setGrpId(loginStaff.getGrpId());
		t.setHotelId(t.getBelongId());
		t.setCreateBy(loginStaff.getAccount());
		t.setMaster(loginStaff.getAccount());
		t.setBlockup(Constants.BLOCKUP_NO);
		t.setStatus(Constants.STATUS_NORMAL);
		PermsTWorkTeam permsTWorkTeam = convertWorkTeam2PermsTWorkTeam(t);
		int res = pWorkTeamMapper.insertSelective(permsTWorkTeam);
		
		if (res == 1) {
			
			return 1;
		}
		return 0;
	}
	/*
	 * 从前台传过来的类型数据、applyJoin需要进一步处理；在保存/修改时候通用方法；
	 */
	private void getTypeInfo(WorkTeam t) {
		t.setName(t.getName().trim());
		t.setEnglishName(t.getEnglishName().trim());
		t.setModifyTime(new Date());
		t.setModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
		if (t.getApplyJoin() == null || t.getApplyJoin() != 0) {
			t.setApplyJoin(Constants.JOIN_APPLY_NO);
		}
//		拆分分类信息；
		String typeInfo = t.getTypeId();
		if (StringUtils.isNotBlank(typeInfo) && typeInfo.contains("+")) {
			String[] typeInfoArr = typeInfo.split("[+]");
			if (typeInfoArr != null && typeInfoArr.length > 0) {
				t.setTypeId(typeInfoArr[0]);
				t.setTypeCode(typeInfoArr[1]);
			}
		}
	}
	
	@Override
	public List<WorkTeam> list(@RequestBody WorkTeam t) throws AppException {
		PermsTWorkTeamExample example = new PermsTWorkTeamExample();
		PermsTWorkTeamExample.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(t.getHotelId())){
			criteria.andClHotelIdEqualTo(t.getHotelId());
		}
		if(StringUtils.isNotBlank(t.getId())){
			criteria.andClIdEqualTo(t.getId());
		}
		if(StringUtils.isNotBlank(t.getEnglishName())){
			criteria.andClEnglishNameLike("%"+t.getEnglishName()+"%");
		}
		if(StringUtils.isNotBlank(t.getIntro())){
			criteria.andClIntroEqualTo(t.getIntro());
		}
		if(StringUtils.isNotBlank(t.getTypeCode())){
			criteria.andClTypeCodeEqualTo(t.getTypeCode());
		}
		if(StringUtils.isNotBlank(t.getTypeId())){
			criteria.andClTypeIdEqualTo(t.getTypeId());
		}
		
		if(t.getStatus() != null){
			criteria.andClStatusEqualTo(t.getStatus());
		}else{
			criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		}
		if(StringUtils.isNotBlank(t.getName())){
			criteria.andClNameLike("%"+t.getName()+"%");
		}
		List<PermsTWorkTeam> permsTWorkTeamList = pWorkTeamMapper.selectByExample(example);
		List<WorkTeam> workTeamList = new ArrayList<WorkTeam>();
		for(PermsTWorkTeam permsTWorkTeam : permsTWorkTeamList){
			workTeamList.add(convertPermsTWorkTeam2WorkTeam(permsTWorkTeam));
		}
		return workTeamList;
	}
	/*
	 * 有条件的查询展示内容,包含'分页'功能(non-Javadoc)
	 */
	@Override
	public PageResult<WorkTeam> list(int index, int size, @RequestBody WorkTeam t) {
		
		Thread thread = Thread.currentThread();
		LOG.debug("接口实现的线程ID:{},Name:{}",thread.getId(),thread.getName());
		
		PageResult<WorkTeam> pageResult = new PageResult<WorkTeam>();
//		查询条件；
		Short status = t.getStatus();
		Short applyJoin = t.getApplyJoin();
//		获取分类Id;
		String typeInfo = t.getTypeId();
//		这个值可能是 ‘中文名称/英文名称/创建人’
		String chName = t.getName();
		PermsTWorkTeamExample pWorkTeamExample = new PermsTWorkTeamExample();
		pWorkTeamExample.setOrderByClause("cl_create_time desc");
		Criteria criteria = pWorkTeamExample.createCriteria();
		Criteria or1 = pWorkTeamExample.or();
		Criteria or2 = pWorkTeamExample.or();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClBlockupNotEqualTo(Constants.BLOCKUP_YES);
		or1.andClStatusNotEqualTo(Constants.STATUS_DEL);
		or1.andClBlockupNotEqualTo(Constants.BLOCKUP_YES);
		or2.andClStatusNotEqualTo(Constants.STATUS_DEL);
		or2.andClBlockupNotEqualTo(Constants.BLOCKUP_YES);
        
        Staff staff = LoginInfoHolder.getLoginInfo();
        if (staff != null && StringUtils.isNoneBlank(staff.getHotelId()) && !staff.getHotelId().equals(staff.getGrpId()))
        {// BUG-6732 使用A酒店用户登录权限系统，不可见B酒店工作圈
            criteria.andClHotelIdEqualTo(staff.getHotelId());
            or1.andClHotelIdEqualTo(staff.getHotelId());
            or2.andClHotelIdEqualTo(staff.getHotelId());
        }
        
		if (StringUtils.isNotBlank(typeInfo)) {
			String[] typeArr = typeInfo.split("[+]");
			String typeId = typeArr[0];
			criteria.andClTypeIdEqualTo(typeId);
			or1.andClTypeIdEqualTo(typeId);
			or2.andClTypeIdEqualTo(typeId);
		}
		if (status != null) {
			criteria.andClStatusEqualTo(status);
			or1.andClStatusEqualTo(status);
			or2.andClStatusEqualTo(status);
		}
		if (applyJoin != null) {
			criteria.andClApplyJoinEqualTo(applyJoin);
			or1.andClApplyJoinEqualTo(applyJoin);
			or2.andClApplyJoinEqualTo(applyJoin);
		}
//		圈名/圈主模糊查询
		if (StringUtils.isNotBlank(chName)) {
			chName = chName.trim();
			String value = "%" + chName + "%";
//			英文；
			criteria.andClEnglishNameLike(value);
//			中文；
			or1.andClNameLike(value);
//			圈主名；
			or2.andClMasterLike(value);
		}
//		分页；
		PageHelper.startPage(index+1, size);
		Page<PermsTWorkTeam> permsTWorkTeamList = (Page<PermsTWorkTeam>)pWorkTeamMapper.selectByExample(pWorkTeamExample);
		List<WorkTeam> workTeamList = new ArrayList<WorkTeam>();
		if (permsTWorkTeamList != null && permsTWorkTeamList.size() > 0) {
			for (PermsTWorkTeam permsTWorkTeam : permsTWorkTeamList) {
//				拼接分类码表值；
				permsTWorkTeam.setClTypeId(permsTWorkTeam.getClTypeId() + "+" + permsTWorkTeam.getClTypeCode());
				WorkTeam workTeam = convertPermsTWorkTeam2WorkTeam(permsTWorkTeam);
				String teamId = workTeam.getId();
//				根据Id查询员工工作圈员工数量；
				PermsTStaffTeamExample pStaffTeamExample = new PermsTStaffTeamExample();
				com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria criteria2 = pStaffTeamExample.createCriteria();
				criteria2.andClTeamIdEqualTo(teamId);
//				赋值成员人数；
				workTeam.setMemberCount(pStaffTeamMapper.selectByExample(pStaffTeamExample).size());
				String[] administratorArr = workTeam.getAdministrator();
				if (administratorArr == null) {
					administratorArr = new String[]{};
					workTeam.setAdministrator(administratorArr);
				}
				workTeamList.add(workTeam);
			}
//			bean类型转换成功则两个集合元素数量相同；
			if (workTeamList.size() == permsTWorkTeamList.size()) {
//				分页设置；
				pageResult.setResult(true);
				pageResult.setResults(permsTWorkTeamList.getTotal());
				pageResult.setRows(workTeamList);
			}
		}
		return pageResult;
	}
	
	/*
	 * 获取工作圈已有成员信息；
	 */
	@Override
	public PageResult<GrpMember> selGrpMember(String teamId,String roleId) throws AppException {
		List<String> staffIdList = new ArrayList<>();
		if (StringUtils.isNotBlank(teamId)) {
			staffIdList = getGrpMemberIds(teamId);
		}
		if (StringUtils.isNotBlank(roleId)) {
			staffIdList = roleService.findStaffIdListByRoleId(roleId);
		}
		PageResult<GrpMember> pageResult = getSelMemberInfos(staffIdList);
		if (pageResult != null) {
			List<GrpMember> grpMembers = pageResult.getRows();
			if (grpMembers != null && grpMembers.size() > 0) {
				for (GrpMember grpMember : grpMembers) {
					grpMember.setTeamId(teamId);
				}
				pageResult.setRows(grpMembers);
			}
		}
		return pageResult;
	}
	/*
	 * 通过工作圈Id获取工作圈中现有成员的Id信息；
	 */
	private List<String> getGrpMemberIds(String teamId) {
//		不存在搜索限制条件；
		List<String> staffIdList = new ArrayList<>();
		PermsTStaffTeamExample pStaffTeamExample = new PermsTStaffTeamExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = pStaffTeamExample.createCriteria();
		pStaffTeamCriteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		pStaffTeamCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		pStaffTeamCriteria.andClTeamIdEqualTo(teamId);
		List<PermsTStaffTeam> permsTStaffTeamList = pStaffTeamMapper.selectByExample(pStaffTeamExample);
//		获取关联工作圈员工的
		for (PermsTStaffTeam permsTStaffTeam : permsTStaffTeamList) {
			staffIdList.add(permsTStaffTeam.getClStaffId());
		}
			return staffIdList;
	}
/*
 * 保存工作圈添加员工信息到工作圈关联信息表；
 */
	@Override
	public int saveGrpMembers(@RequestBody List<GrpMember> grpMemberList) {
		String teamId = grpMemberList.get(0).getTeamId();
		
		// add by 刘溶容 集成环信 2018/01/10 begin
		// 检索操作前工作圈人员列表
		PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(teamId);
		String groupId = permsTWorkTeam.getClEasemobGroupId();
		boolean hasGroupFlag = StringUtils.isNotEmpty(groupId);
		String owerId;
		List<String> beforeUserIdList = new ArrayList<String>();
		if (!hasGroupFlag) {
			owerId = grpMemberList.get(0).getStaffId();
		} else {
			// 依据群组ID获取群主userName;
			List<String> groupIdList = new ArrayList<String>();
			groupIdList.add(groupId);
			String groupList = chatGroupAPI.getChatGroupDetails(groupIdList);
			Map<String, Object> groupJSONMap = JSON.parseObject(groupList);
			List<Map<String, Object>> resultGroupList = (List<Map<String, Object>>) groupJSONMap.get("data");
			owerId = (String) resultGroupList.get(0).get("owner");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", teamId);
			beforeUserIdList = pStaffTeamMapper.getBeforeUserIdList(paramMap);
		}
		// 新建群组
		if (StringUtils.isEmpty(groupId)) {
			Group group = new Group();
			group.setDesc(permsTWorkTeam.getClName());
			group.setGroupname(permsTWorkTeam.getClName());
			group.setMaxusers(1000);
			group.setOwner(grpMemberList.get(0).getStaffId());
			group.setPublic(true);
			String groupJSON = chatGroupAPI.createChatGroup(group);
			Map<String, Object> groupJSONMap = JSON.parseObject(groupJSON);
			Map<String, Object> groupIdMap = (Map<String, Object>) groupJSONMap.get("data");
			groupId = (String) groupIdMap.get("groupid");
			// 更新工作圈表，设置群组ID
			permsTWorkTeam.setClEasemobGroupId(groupId);
			permsTWorkTeamMapper.updateByPrimaryKeySelective(permsTWorkTeam);
		}
		// add by 刘溶容 集成环信 2018/01/10 end
		
//		先删除数据;
		PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria pStaffTeamCriteria = permsTStaffTeamExample.createCriteria();
		pStaffTeamCriteria.andClTeamIdEqualTo(teamId);
		pStaffTeamMapper.deleteByExample(permsTStaffTeamExample);
//		再重新进行保存；
		PermsTStaffTeam permsTStaffTeam = new PermsTStaffTeam();
		// add by 刘溶容 for 环信集成 2018/01/10 begin
		List<String> afterUserIdList = new ArrayList<String>();
		// add by 刘溶容 for 环信集成 2018/01/10 end
		for (GrpMember grpMember : grpMemberList) {
			permsTStaffTeam.setClId(HashUtils.uuidGenerator());
			permsTStaffTeam.setClStaffId(grpMember.getStaffId());
			permsTStaffTeam.setClTeamId(grpMember.getTeamId());
			permsTStaffTeam.setClStatus(Constants.STATUS_NORMAL);
			permsTStaffTeam.setClBlockup(Constants.BLOCKUP_NO);
			permsTStaffTeam.setClGrpId(grpMember.getGrpId());
			permsTStaffTeam.setClHotelId(grpMember.getHotId());
			permsTStaffTeam.setClCreateBy(grpMember.getCreateBy());
			pStaffTeamMapper.insertSelective(permsTStaffTeam);
			// add by 刘溶容 集成环信 2018/01/10 begin
			if (!hasGroupFlag) {
				// 新建的群
				if (!owerId.equals(grpMember.getStaffId())) {
					afterUserIdList.add(grpMember.getStaffId());
				}
			} else {
				// 工作圈已经存在群了,将所有新的用户封装成list，下面和原成员求交集
				afterUserIdList.add(grpMember.getStaffId());
			}
			// add by 刘溶容 集成环信 2018/01/10 end
		}
		// add by 刘溶容 for 环信 2018/01/10 begin
		List<String> deleteOldUserIdList = new ArrayList<String>();
		List<String> nowUserIdList = new ArrayList<String>();
		if (hasGroupFlag) {
//			List<String> compList = beforeUserIdListForCount;
//			compList.retainAll(afterUserIdList);
			// 有交集， 判断群主是否在当前交集部分
			if (beforeUserIdList.indexOf(owerId) >= 0) {
				
				// 追加员工
				for (String nowUserId : afterUserIdList) {
					if (beforeUserIdList.indexOf(nowUserId) < 0) {
						String result = chatGroupAPI.addSingleUserToChatGroup(groupId, nowUserId);
						LOG.debug("======================溶容的日志：" + result);
					}
				}
				
				// 删除员工
				for (String oldUserId : beforeUserIdList) {
					if (afterUserIdList.indexOf(oldUserId) < 0) {
						String result = chatGroupAPI.removeSingleUserFromChatGroup(groupId, oldUserId);
						LOG.debug("======================溶容的日志：" + result);
					}
				}
				
				
				// 交集包含群主，则不需要转让群组操作
				// 将所有之前的群组用户删除
//				for (String beforeUserId : beforeUserIdList) {
//					if (!owerId.equals(beforeUserId)) {
//						deleteOldUserIdList.add(beforeUserId);
//					}
//				}
//				for (String afterUserId : afterUserIdList) {
//					if (!owerId.equals(afterUserId)) {
//						nowUserIdList.add(afterUserId);
//					}
//				}
//				String delResult = chatGroupAPI.removeBatchUsersFromChatGroup(groupId, deleteOldUserIdList);
				
				// 环信群组插入新用户
//				UserNames userNames = new UserNames();
//				UserName userName = new UserName();
//				userName.addAll(nowUserIdList);
//				userNames.setUsernames(userName);
//				LOG.debug("============================溶容的日志:批量插入开始" + userNames.getUsernames().size());
//				String result = chatGroupAPI.addBatchUsersToChatGroup(groupId, userNames);
//				for (String nowUserId : nowUserIdList) {
//					String result = chatGroupAPI.addSingleUserToChatGroup(groupId, nowUserId);
//					LOG.debug("======================溶容的日志：" + result);
//				}
			} else {
				// 交集不包含群主，则群需要转交
				owerId = afterUserIdList.get(0);
				NewOwner newOwner = new NewOwner();
				newOwner.setNewowner(owerId);
				// 群组转让
				chatGroupAPI.transferChatGroupOwner(groupId, newOwner);
				// 追加员工
				for (String nowUserId : afterUserIdList) {
					if (beforeUserIdList.indexOf(nowUserId) < 0) {
						String result = chatGroupAPI.addSingleUserToChatGroup(groupId, nowUserId);
						LOG.debug("======================溶容的日志：" + result);
					}
				}
				
				// 删除员工
				for (String oldUserId : beforeUserIdList) {
					if (afterUserIdList.indexOf(oldUserId) < 0) {
						String result = chatGroupAPI.removeSingleUserFromChatGroup(groupId, oldUserId);
						LOG.debug("======================溶容的日志：" + result);
					}
				}
//				for (String beforeUserId : beforeUserIdListForCount) {
//					if (!owerId.equals(beforeUserId)) {
//						deleteOldUserIdList.add(beforeUserId);
//					}
//				}
//				for (String afterUserId : afterUserIdList) {
//					if (!owerId.equals(afterUserId)) {
//						nowUserIdList.add(afterUserId);
//					}
//				}
//				chatGroupAPI.removeBatchUsersFromChatGroup(groupId, deleteOldUserIdList);
//				// 环信群组插入新用户
////				UserNames userNames = new UserNames();
////				UserName userName = new UserName();
////				userName.addAll(nowUserIdList);
////				userNames.setUsernames(userName);
//				
//				for (String nowUserId : nowUserIdList) {
////					UserName userName = new UserName();
////					userName.add(nowUserId);
//					chatGroupAPI.addSingleUserToChatGroup(groupId, nowUserId);
//				}
//				chatGroupAPI.addBatchUsersToChatGroup(groupId, userNames);
			}
//			if (compList.size() > 0) {
//				
//			}
		} else {
			// 新建群组则直接将最新用户插入环信群组即可
//			UserNames userNames = new UserNames();
//			UserName userName = new UserName();
//			userName.addAll(afterUserIdList);
//			userNames.setUsernames(userName);
//			chatGroupAPI.addBatchUsersToChatGroup(groupId, userNames);
			for (String nowUserId : afterUserIdList) {
//				UserName userName = new UserName();
//				userName.add(nowUserId);
				chatGroupAPI.addSingleUserToChatGroup(groupId, nowUserId);
			}
		}
		// add by 刘溶容 for 环信  2018/01/10 end
		return 1;
	}
	
	/*
	 * 获取员工工作酒店信息下的员工Id
	 */
	private List<String> getStaffIds(PermsTStaffHotelExample pStaffHotelExample) {
		List<String> staffIdList = new ArrayList<>();
		List<PermsTStaffHotel> pStaffHotelList = pStaffHotelMapper.selectByExample(pStaffHotelExample);
		for (PermsTStaffHotel permsTStaffHotel : pStaffHotelList) {
			staffIdList.add(permsTStaffHotel.getClStaffId());
		}
		return staffIdList;
	}
	
	public GrpMember convertPermGrpMember2Member(PermGrpMember permGrpMember) {
		try {
			return BeanUtils.copyDbBean2ServiceBean(permGrpMember, new GrpMember());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 根据Id查询成员信息；
	 */
	@Override
	public PageResult<GrpMember> selMemberInfos(@RequestBody List<String> staffIdList) throws AppException {
		return getSelMemberInfos(staffIdList);
	}
	
	private PageResult<GrpMember> getSelMemberInfos(List<String> staffIdList) throws AppException {
		PageResult<GrpMember> pageResult = new PageResult<GrpMember>();
		if (staffIdList == null || staffIdList.size() == 0) {
			return pageResult;
		}
		List<PermGrpMember> grpMemberInfoList = pWorkTeamMapper.selGrpMemberInfo(staffIdList);
		List<GrpMember> grpMemberList = new ArrayList<>();
		for (PermGrpMember permGrpMember : grpMemberInfoList) {
			PermsTStaff staff = new PermsTStaff();
			staff.setClId(permGrpMember.getClStaffId());
			staff.setClDefaultHotelId(permGrpMember.getClDefaultHotelNameId());
			staff.setClDefaultDptId(permGrpMember.getClDepartmentNameId());
			staff.setClDefaultPostId(permGrpMember.getClPostId());
			List<String> orgInfoList = pStaffMapper.selStaffOrgInfos(staff);
			String clFamilyName = permGrpMember.getClFamilyName();
			if (StringUtils.isNotBlank(clFamilyName)) {
				permGrpMember.setClChName(clFamilyName + permGrpMember.getClChName());
			}
			GrpMember grpMember = convertPermGrpMember2Member(permGrpMember);
//			拼接组织信息；
			if (orgInfoList != null && orgInfoList.size() > 0) {
				grpMember.setOrgInfo(orgInfoList.get(0));
			}
			grpMemberList.add(grpMember);
		}
//		返回结果；
		pageResult.setResult(true);
		pageResult.setResults(grpMemberInfoList.size());
		pageResult.setRows(grpMemberList);	
		return pageResult;
	}
	
/*
 * 查询未加入工作圈的员工；
 */
	@Override
	public List<GrpMember> selGrpMemberAll(String teamId,String roleId,String orgId,Integer nodeType,
			String empName,String[] selStaffIdArr) throws AppException {
		short shortValue = 0;
		if (nodeType != null) {
			shortValue = nodeType.shortValue();
		}
		PageResult<Staff> pageResult = employeeService.pageList(0, Integer.MAX_VALUE, shortValue, orgId, Constants.STATUS_NORMAL, empName, null,roleId);
		List<Staff> staffList = pageResult.getRows();
		List<GrpMember> grpMemberList = new ArrayList<>();
		int flag = 0;
		if (staffList != null && staffList.size() > 0) {
			for (Staff staff : staffList) {
				if (selStaffIdArr != null && selStaffIdArr.length > 0) {
					for (String staffId : selStaffIdArr) {
						if (staff.getId().equals(staffId)) {
							flag = 1;
							break;
						}else {
							flag = 0;
						}
					}
				}
				if (flag == 1) {
					continue;
				}
				GrpMember grpMember = new GrpMember();
				grpMember.setStaffId(staff.getId());
				String name = staff.getName();
				grpMember.setChName(name);
				grpMember.setFamilyName(staff.getFamilyName());
				grpMember.setDefaultHotelNameId(staff.getDefaultHotelId());
				grpMember.setDepartmentNameId(staff.getDefaultDptId());
				grpMember.setPostId(staff.getDefaultPostId());
				grpMember.setGrpId(staff.getGrpId());
				grpMember.setHotId(staff.getDefaultHotelId());
				grpMember.setCreateBy(staff.getAccount());
				grpMember.setOrgInfo(staff.getOrgInfo());
				grpMember.setTeamId(teamId);
				grpMemberList.add(grpMember);
			}
		}
		PageResult<GrpMember> pageResultHas = selGrpMember(teamId, roleId);
		List<GrpMember> grpMemberHasList = pageResultHas.getRows();
		Iterator<GrpMember> grpMemeberIterator = grpMemberList.iterator();
		while (grpMemeberIterator.hasNext()) {
			GrpMember grpMember = (GrpMember) grpMemeberIterator.next();
			for (GrpMember grpMemberHas : grpMemberHasList) {
				if (grpMember.getStaffId().equals(grpMemberHas.getStaffId())) {
					grpMemeberIterator.remove();
				}
			}
		}
		return grpMemberList;
	}
	/*
	 * 获取未被选择的成员信息；
	 */
	@Override
	public PageResult<GrpMember> selMemberLeftInfos(
			@RequestBody List<String> idList,String roleId,String orgId,
			Integer nodeType,String empName,String[] selStaffIdArr) throws AppException {
//		所有员工的信息；
		PermsTStaffExample permsTStaffExample = new PermsTStaffExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffExample.Criteria pStaffCriteria = permsTStaffExample.createCriteria();
		pStaffCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
//		搜索条件到员工名称；
		if (StringUtils.isNotBlank(empName)) {
			pStaffCriteria.andClNameLike("%"+empName.trim()+"%");
		}
		if (selStaffIdArr != null && selStaffIdArr.length > 0) {
			pStaffCriteria.andClIdNotIn(Arrays.asList(selStaffIdArr));
		}
		List<PermsTStaff> permsTStaffList = pStaffMapper.selectByExample(permsTStaffExample);
//		所有成员的Id
		List<String> grpMemberAllList = new ArrayList<>();
		for (PermsTStaff permsTStaff : permsTStaffList) {
			grpMemberAllList.add(permsTStaff.getClId());
		}
//		搜索条件到岗位；
		PermsTStaffHotelExample pStaffHotelExample = new PermsTStaffHotelExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample.Criteria pStaffHotelCriteria = pStaffHotelExample.createCriteria();
		List<String> staffIdList = new ArrayList<>();
		if (grpMemberAllList.size() > 0) {
//			如果查询条件到员工名称，则需要在员工名称的基础上筛出符合组织条件的员工；
			pStaffHotelCriteria.andClStaffIdIn(grpMemberAllList);
		}
		pStaffHotelCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		pStaffHotelCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		if (nodeType != null) {
			if (nodeType == Constants.NODE_TYPE_ORG || nodeType == Constants.NODE_TYPE_HOTEL) {
//				集团/酒店；
				pStaffHotelCriteria.andClWorkHotelIdEqualTo(orgId);
				staffIdList = getStaffIds(pStaffHotelExample);
			}else if (nodeType == Constants.NODE_TYPE_DPT) {
//				部门；
				List<String> departsId = pDepartmentMapper.selDeparts(orgId);
				pStaffHotelCriteria.andClDptIdIn(departsId);
				staffIdList = getStaffIds(pStaffHotelExample);
			}else if (nodeType == Constants.NODE_TYPE_POST){
//				岗位；
				pStaffHotelCriteria.andClPostIdEqualTo(orgId);
				staffIdList = getStaffIds(pStaffHotelExample);
			}
		}
//		移除存在员工Id；
		grpMemberAllList.removeAll(idList);
//		取满足组织条件的员工id和名称的员工id集合的交集；
//		不存在搜索条件；
		if (StringUtils.isBlank(orgId) && nodeType == null) {
			
		}else {
//			存在搜索条件
			grpMemberAllList.retainAll(staffIdList);
		}
		PageResult<GrpMember> result = getSelMemberInfos(grpMemberAllList);
		return result;
	}
	/*
	 * 获取编辑工作圈信息；
	 */
	@Override
	public WorkTeam getWorkTeamEditInfo(String id) {
		PermsTWorkTeam permsTWorkTeam = pWorkTeamMapper.selectByPrimaryKey(id);
		if (permsTWorkTeam != null) {
			permsTWorkTeam.setClTypeId(permsTWorkTeam.getClTypeId() + "+" + permsTWorkTeam.getClTypeCode());			
			return convertPermsTWorkTeam2WorkTeam(permsTWorkTeam);
		}
		return new WorkTeam();
	}
	/*
	 * 查询所有的工作圈信息
	 */
	@Override
	public List<WorkTeam> findAllWorkTeam() {
		List<PermsTWorkTeam> permsTWorkTeamList = pWorkTeamMapper.selectByExample(null);
		List<WorkTeam> workTeamList = new ArrayList<WorkTeam>();
		for(PermsTWorkTeam permsTWorkTeam : permsTWorkTeamList){
			WorkTeam workTeam = this.convertPermsTWorkTeam2WorkTeam(permsTWorkTeam);
			workTeamList.add(workTeam);
		}
		return workTeamList;
	}
/*
 * (non-Javadoc)锁定数据
 * @see com.sstc.hmis.permission.service.WorkTeamService#lockGrid(java.lang.String)
 */
	@Override
	public String lockGrid(String idvalue) {
		WorkTeam workTeam = getWorkTeamEditInfo(idvalue);
		workTeam.setModifyTime(new Date());
		workTeam.setModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
		if (workTeam != null) {
			int res = 0;
			String typeInfo = workTeam.getTypeId();
			if (StringUtils.isNotBlank(typeInfo) && typeInfo.contains("+")) {
				String[] typeArr = typeInfo.split("[+]");
				workTeam.setTypeId(typeArr[0]);
			}
			Short statusNow = workTeam.getStatus();
			if (statusNow == Constants.STATUS_LOCK) {
				workTeam.setStatus(Constants.STATUS_NORMAL);
				res = pWorkTeamMapper.updateByPrimaryKeySelective(convertWorkTeam2PermsTWorkTeam(workTeam));
				if (res == 1) {
					return "normal";
				}
			}else {
				workTeam.setStatus(Constants.STATUS_LOCK);
				res = pWorkTeamMapper.updateByPrimaryKeySelective(convertWorkTeam2PermsTWorkTeam(workTeam));
				if (res == 1) {
					return "lock";
				}
			}
		}
		return "fail";
	}
/*
 * 根据角色获取角色所属酒店信息；
 */
	@Override
	public Map<String, String> getHotelInfoByRoleId(String roleId) {
		Map<String, String> hotelInfoMap = new HashMap<String, String>();
		PermsTRoleExample permsTRoleExample = new PermsTRoleExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTRoleExample.Criteria criteria = permsTRoleExample.createCriteria();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
		criteria.andClIdEqualTo(roleId);
		List<PermsTRole> permsTRoleList = permsTRoleMapper.selectByExample(permsTRoleExample);
		if (permsTRoleList != null && permsTRoleList.size() > 0) {
			PermsTRole permsTRole = permsTRoleList.get(0);
			String clHotelId = permsTRole.getClHotelId();
			PermsTGroupHotelExample permsTGroupHotelExample = new PermsTGroupHotelExample();
			com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample.Criteria pGroupCriteria = permsTGroupHotelExample.createCriteria();
			pGroupCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			pGroupCriteria.andClBlockupEqualTo(Constants.BLOCKUP_NO);
			pGroupCriteria.andClIdEqualTo(clHotelId);
			List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.selectByExample(permsTGroupHotelExample);
			if (permsTGroupHotelList != null && permsTGroupHotelList.size() > 0) {
				PermsTGroupHotel permsTGroupHotel = permsTGroupHotelList.get(0);
				hotelInfoMap.put(permsTGroupHotel.getClId(), permsTGroupHotel.getClName());
			}
		}
		return hotelInfoMap;
	}
	@Override
	public List<String> getWortemMemebers(String workTeamId) {
		List<String> memberIdList = new ArrayList<>();
		PermsTStaffTeamExample permsTStaffTeamExample = new PermsTStaffTeamExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTStaffTeamExample.Criteria criteria = permsTStaffTeamExample.createCriteria();
		criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		criteria.andClTeamIdEqualTo(workTeamId);
		List<PermsTStaffTeam> permsTStaffTeamList = permsTStaffTeamMapper.selectByExample(permsTStaffTeamExample);
		if (permsTStaffTeamList != null && permsTStaffTeamList.size() > 0) {
			for (PermsTStaffTeam permsTStaffTeam : permsTStaffTeamList) {
				memberIdList.add(permsTStaffTeam.getClStaffId());
			}
		}
		return memberIdList;
	}

	/**
	 * 根据name查询工作圈
	 * @author yuankairui
	 */
	@Override
	public PageResult<WorkTeam> queryTeamList(int index, int size, String name, String hotelId) {
			PageResult<WorkTeam> pageResult = new PageResult<WorkTeam>();
			PageHelper.startPage(index+1, size);
			PermsTWorkTeamExample example = new PermsTWorkTeamExample();
			PermsTWorkTeamExample.Criteria criteria = example.createCriteria();
			Criteria or = example.or();
			if(StringUtils.isNotBlank(hotelId)){
				criteria.andClHotelIdEqualTo(hotelId);
			}
			if(StringUtils.isNotBlank(name)){
				or.andClEnglishNameLike("%"+name+"%");
				or.andClNameLike("%"+name+"%");
			}
			example.setOrderByClause("cl_create_time desc");
			List<PermsTWorkTeam> permsTWorkTeamList = pWorkTeamMapper.selectByExample(example);
			List<WorkTeam> workTeamList = new ArrayList<WorkTeam>();
			for(PermsTWorkTeam permsTWorkTeam : permsTWorkTeamList){
//				拼接分类码表值；
				permsTWorkTeam.setClTypeId(permsTWorkTeam.getClTypeId() + "+" + permsTWorkTeam.getClTypeCode());
				workTeamList.add(convertPermsTWorkTeam2WorkTeam(permsTWorkTeam));
			}
			pageResult.setRows(workTeamList);
			return pageResult;
	}

}
