/**
 * 
 */
package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.data.DptPost;
import com.sstc.hmis.permission.data.PostResponsibility;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDepartmentMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDptPostMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPostResponsibilityMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRoleMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTWorkTeamMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptPost;
import com.sstc.hmis.permission.dbaccess.data.PermsTDptPostExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTWorkTeam;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
  * <p> Title: PostResponsibilityService </p>
  * <p> Description:  岗位职责 </p>
  * <p> Company: SSTC </p> 
  * @author  CKang
   */
@RestController
public class PostResponsibilityServiceImpl extends BaseServiceImpl<PostResponsibility> implements PostResponsibilityService{
	
	@Autowired
	private PermsTPostResponsibilityMapper permsTPostResponsibilityMapper;
	@Autowired
	private PermsTRoleMapper permsTRoleMapper;
	@Autowired
	private PermsTWorkTeamMapper permsTWorkTeamMapper;
	@Autowired
	private PermsTDptPostMapper permsTDptPostMapper;
	@Autowired
	private PermsTDepartmentMapper permsTDepartmentMapper;
	
	
	
	/**
	 * 通过部门id查询所有的岗位
	 * @param deptId
	 * @return
	 */
	@Override
	public PageResult<PostResponsibility> findPostResponsibilityByDeptId(@RequestBody PostResponsibility postResponsibility2,int pageIndex, int pageSize){
		PageResult<PostResponsibility> retData = new PageResult<PostResponsibility>();
		// 分页
		PageHelper.startPage(pageIndex + 1, pageSize);
		
		PermsTPostResponsibility permsTPostResponsibility2  = this.convertpostResponsibility2PermsTPostResponsibility(postResponsibility2);
		Page<PermsTPostResponsibility> page = (Page<PermsTPostResponsibility>)permsTPostResponsibilityMapper.findPostResponsibilityByDeptId(permsTPostResponsibility2);
		
		List<PostResponsibility> list = new ArrayList<PostResponsibility>();
		
		for(PermsTPostResponsibility permsTPostResponsibility : page.getResult()){
			PostResponsibility postResponsibility = this.convertPermsTPostResponsibility2PostResponsibility(permsTPostResponsibility);
			
			// 根据职位id查询员工数量
			String id = postResponsibility.getId();
			int staffCount = permsTPostResponsibilityMapper.findStaffCountByPostResponsibilityId(id);
			if(staffCount > 0) {
				postResponsibility.setStaffCount(staffCount);
			}
			
			// 取出角色id数组
			String[] defaultRoleArr = postResponsibility.getDefaultRole();
			String[] defaultRoleNameArr = new String[defaultRoleArr.length];
			for(int i=0; i<defaultRoleArr.length; i++) {
				PermsTRole permsrole = permsTRoleMapper.selectByPrimaryKey(defaultRoleArr[i]);
				if(permsrole != null){
					String roleName = permsrole.getClName();
					defaultRoleNameArr[i] = roleName;
				}
			}
			if(defaultRoleNameArr != null && defaultRoleNameArr.length > 0) {
				postResponsibility.setDefaultRoleName(defaultRoleNameArr);
			}
			
			// 默认工作圈id
			String defaultTeamId = postResponsibility.getDefaultTeam();
			if(StringUtils.isNoneBlank(defaultTeamId)){
				PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(defaultTeamId);
				if(StringUtils.isNotBlank(permsTWorkTeam.getClName())){
					postResponsibility.setDefaultTeamName(permsTWorkTeam.getClName());
				}
			}
			// 组织
			String postId = permsTPostResponsibility.getClId();
			String organization =  permsTPostResponsibilityMapper.findOrganizationByPostId(postId);
			postResponsibility.setOrganization(organization);
			
			list.add(postResponsibility);
		}
		retData.setResult(true);
		retData.setResults(page.getTotal());
		retData.setRows(list);
		return retData;
	}
	
	@Override
	public PageResult<PostResponsibility> findPostResponsibilityByDeptIdCopy(
			@RequestBody PostResponsibility postResponsibility2,int pageIndex, int pageSize){
		PageResult<PostResponsibility> retData = new PageResult<PostResponsibility>();
		// 分页
		PageHelper.startPage(pageIndex + 1, pageSize);
		
		PermsTPostResponsibility permsTPostResponsibility2  = this.convertpostResponsibility2PermsTPostResponsibility(postResponsibility2);
		Page<PermsTPostResponsibility> page = (Page<PermsTPostResponsibility>)permsTPostResponsibilityMapper.findPostResponsibilityByDeptIdCopy(permsTPostResponsibility2);
		
		List<PostResponsibility> list = new ArrayList<PostResponsibility>();
		
		for(PermsTPostResponsibility permsTPostResponsibility : page.getResult()){
			PostResponsibility postResponsibility = this.convertPermsTPostResponsibility2PostResponsibility(permsTPostResponsibility);
			
			// 根据职位id查询员工数量
			String id = postResponsibility.getId();
			int staffCount = permsTPostResponsibilityMapper.findStaffCountByPostResponsibilityId(id);
			if(staffCount > 0) {
				postResponsibility.setStaffCount(staffCount);
			}
			
			// 取出角色id数组
			String[] defaultRoleArr = postResponsibility.getDefaultRole();
			String[] defaultRoleNameArr = new String[defaultRoleArr.length];
			for(int i=0; i<defaultRoleArr.length; i++) {
				PermsTRole permsrole = permsTRoleMapper.selectByPrimaryKey(defaultRoleArr[i]);
				if(permsrole != null){
					String roleName = permsrole.getClName();
					defaultRoleNameArr[i] = roleName;
				}
			}
			if(defaultRoleNameArr != null && defaultRoleNameArr.length > 0) {
				postResponsibility.setDefaultRoleName(defaultRoleNameArr);
			}
			
			// 默认工作圈id
			String defaultTeamId = postResponsibility.getDefaultTeam();
			if(StringUtils.isNoneBlank(defaultTeamId)){
				PermsTWorkTeam permsTWorkTeam = permsTWorkTeamMapper.selectByPrimaryKey(defaultTeamId);
				if(StringUtils.isNotBlank(permsTWorkTeam.getClName())){
					postResponsibility.setDefaultTeamName(permsTWorkTeam.getClName());
				}
			}
			// 组织
			String postId = permsTPostResponsibility.getClId();
			String organization =  permsTPostResponsibilityMapper.findOrganizationByPostId(postId);
			postResponsibility.setOrganization(organization);
			postResponsibility.setName(permsTPostResponsibility.getClEnglishName());
			list.add(postResponsibility);
		}
		retData.setResult(true);
		retData.setResults(page.getTotal());
		retData.setRows(list);
		return retData;
	}	
	
	/*
	 * PermsTPostResponsibility转换PostResponsibility实体类
	 */
	private PostResponsibility convertPermsTPostResponsibility2PostResponsibility(PermsTPostResponsibility permsTPostResponsibility){
		try {
			return BeanUtils.copyDbBean2ServiceBean(permsTPostResponsibility, new PostResponsibility());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * PostResponsibility转换PermsTPostResponsibility实体类
	 */
	private PermsTPostResponsibility convertpostResponsibility2PermsTPostResponsibility(PostResponsibility postResponsibility){
		try {
			return BeanUtils.copyServiceBean2DbBean(postResponsibility, new PermsTPostResponsibility());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * PostResponsibility转换PermsTPostResponsibility实体类
	 */
	private PermsTDptPost convertDptPost2PermsTDptPost(DptPost dptPost){
		try {
			return BeanUtils.copyServiceBean2DbBean(dptPost, new PermsTDptPost());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 添加职位信息
	 */
	@Override
	public void savePostResponsibility(@RequestBody PostResponsibility postResponsibility, String deptId) {
		postResponsibility.setId(HashUtils.uuidGenerator());
		postResponsibility.setStatus(Constants.STATUS_NORMAL);
		postResponsibility.setBlockup(Constants.BLOCKUP_NO);
		postResponsibility.setHotelId(permsTPostResponsibilityMapper.findOrganizationByDeptId(deptId));
		postResponsibility.setGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
		postResponsibility.setCreateBy(LoginInfoHolder.getLoginInfo().getAccount());
		permsTPostResponsibilityMapper.insertSelective(this.convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		DptPost dptPost = new DptPost();
		dptPost.setId(HashUtils.uuidGenerator());
		dptPost.setPostId(postResponsibility.getId());
		dptPost.setDptId(deptId);	 
		dptPost.setStatus(Constants.STATUS_NORMAL);
		dptPost.setBlockup(Constants.BLOCKUP_NO);
		dptPost.setGrpId(LoginInfoHolder.getLoginInfo().getGrpId());
		dptPost.setHotelId(permsTPostResponsibilityMapper.findOrganizationByDeptId(deptId));
		dptPost.setCreateBy(LoginInfoHolder.getLoginInfo().getAccount());

		permsTDptPostMapper.insertSelective(this.convertDptPost2PermsTDptPost(dptPost));
	}
	
	/*
	 * 通过职位id查询职位信息
	 */
	@Override
	public PostResponsibility findPostResponsibilityById(String postId) {
		PermsTPostResponsibility permsTPostResponsibility = permsTPostResponsibilityMapper.selectByPrimaryKey(postId);
		PostResponsibility postResponsibility = this.convertPermsTPostResponsibility2PostResponsibility(permsTPostResponsibility);
		return postResponsibility;
	}

	/*
	 * 修改职位信息
	 */
	@Override
	public void updatePostResponsibility(@RequestBody PostResponsibility postResponsibility, String deptId) {
		// 修改职位表信息
		PermsTPostResponsibility permsTPostResponsibility  = this.convertpostResponsibility2PermsTPostResponsibility(postResponsibility);
		permsTPostResponsibility.setClModifyTime(new Date());
		permsTPostResponsibility.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
		permsTPostResponsibilityMapper.updateByPrimaryKeySelective(permsTPostResponsibility);
		//修改职位部门关系表
		String postId = postResponsibility.getId();
		PermsTDptPostExample dptPostExample = new PermsTDptPostExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTDptPostExample.Criteria criteria = dptPostExample.createCriteria();
		criteria.andClPostIdEqualTo(postId);
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTDptPost> permsTDptPostList = permsTDptPostMapper.selectByExample(dptPostExample);
		for(PermsTDptPost permsTDptPost : permsTDptPostList){
			permsTDptPost.setClDptId(deptId);
			permsTDptPost.setClModifyTime(new Date());
			permsTDptPost.setClModifyBy(LoginInfoHolder.getLoginInfo().getAccount());
			permsTDptPostMapper.updateByPrimaryKeySelective(permsTDptPost);
		}
	}

	/*
	 * 验证职位名称是否存在
	 */
	@Override
	public String validPostName(String postNameId,String hotelId,String deptId) {
		PostResponsibility postResponsibility = new PostResponsibility();
		postResponsibility.setPostId(postNameId);
		//postResponsibility.setHotelId(hotelId);
		postResponsibility.setDptId(deptId);
		Integer count = permsTPostResponsibilityMapper.validPostName(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		if(count != null && count > 0) {
			return "同一部门下不能存在相同的职位名称";
		}
		return null;
	}
	
	@Override
	public String validPostNameNotId(String id, String postNameId, String hotelId, String deptId) {
		PostResponsibility postResponsibility = new PostResponsibility();
		postResponsibility.setId(id);
		postResponsibility.setPostId(postNameId);
		//postResponsibility.setHotelId(hotelId);
		postResponsibility.setDptId(deptId);
		Integer count = permsTPostResponsibilityMapper.validPostNameNotId(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		if(count != null && count > 0) {
			return "同一部门下不能存在相同的职位名称";
		}
		return null;
	}

	/*
	 * 通过id删除职位信息
	 */
	@Override
	public void deletePostById(String id) {
		PostResponsibility postResponsibility = new PostResponsibility();
		postResponsibility.setId(id);
		postResponsibility.setStatus(Constants.STATUS_DEL);
		permsTPostResponsibilityMapper.updateByPrimaryKeySelective(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		// 职位部门关系表
		PermsTDptPostExample dptPostExample = new PermsTDptPostExample();
		com.sstc.hmis.permission.dbaccess.data.PermsTDptPostExample.Criteria criteria = dptPostExample.createCriteria();
		criteria.andClPostIdEqualTo(id);
		List<PermsTDptPost> permsTDptPostList = permsTDptPostMapper.selectByExample(dptPostExample);
		for(PermsTDptPost permsTDptPost : permsTDptPostList){
			permsTDptPost.setClStatus(Constants.STATUS_DEL);
			permsTDptPostMapper.updateByPrimaryKeySelective(permsTDptPost);
		}
	}

	/*
	 * 批量删除职位信息
	 */
	@Override
	public void batchDeletePost(String ids) {
		String[] arr = ids.split(",");
		for(int i=0; i<arr.length; i++) {
			this.deletePostById(arr[i]);
		}
	}


	@Override
	public List<Department> findDeptByPostId(String postId) {
		List<PermsTDepartment> permsTDepartmentList = permsTDepartmentMapper.findDeptByPostId(postId);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTDepartmentList, Department.class);
	}


	@Override
	public int findStaffCountByPostId(String postId) {
		return permsTPostResponsibilityMapper.findStaffCountByPostResponsibilityId(postId);
	}


	/**
	 * 通过角色id查询职位数量
	 * @author CKang
	 * @date 2017年5月9日 上午8:58:54
	 * @param role
	 * @return
	 */
	@Override
	public int findPostCountByRoleId(@RequestBody PostResponsibility postResponsibility) {
		return permsTPostResponsibilityMapper.findPostCountByRoleId(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
	}

	@Override
	public List<PostResponsibility> findPostByDeptId(String deptId) {
		List<PermsTPostResponsibility> permsTPostResponsibilityList = permsTPostResponsibilityMapper.findPostByDeptId(deptId);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTPostResponsibilityList, PostResponsibility.class);
	}

	/**
	 * 通过用户id查询职位信息
	 * @author CKang
	 * @date 2017年5月26日 上午11:16:48
	 * @param staffId
	 * @return
	 */
	@Override
	public List<PostResponsibility> findPostByStaffId(String staffId) {
		List<PermsTPostResponsibility> permsTPostResponsibilityList = permsTPostResponsibilityMapper.findPostByStaffId(staffId);
		List<PostResponsibility> postResponsibilityList = new ArrayList<PostResponsibility>();
		for(PermsTPostResponsibility permsTPostResponsibility : permsTPostResponsibilityList){
			PostResponsibility pr = convertPermsTPostResponsibility2PostResponsibility(permsTPostResponsibility);
			pr.setName(pr.getEnglishName());
			postResponsibilityList.add(pr);
		}
		return postResponsibilityList;
	}

	/*
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称
	 */
	@Override
	public String validDeptPostName(String postNameId,String name,String deptId) {
		PostResponsibility postResponsibility = new PostResponsibility();
		postResponsibility.setPostId(postNameId);
		//postResponsibility.setHotelId(hotelId);
		postResponsibility.setDptId(deptId);
		postResponsibility.setEnglishName(name);
		Integer count = permsTPostResponsibilityMapper.validDeptPostName(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		if(count != null && count > 0) {
			return "同一部门同一职级下不能存在相同的名称";
		}
		return null;
	}

	/*
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称  去除自己 修改使用
	 */
	@Override
	public String validDeptPostNameNotId(String id, String postNameId, String name, String deptId) {
		PostResponsibility postResponsibility = new PostResponsibility();
		postResponsibility.setId(id);
		postResponsibility.setPostId(postNameId);
		postResponsibility.setEnglishName(name);
		postResponsibility.setDptId(deptId);
		Integer count = permsTPostResponsibilityMapper.validDeptPostNameNotId(convertpostResponsibility2PermsTPostResponsibility(postResponsibility));
		if(count != null && count > 0) {
			return "同一部门同一职级下不能存在相同的名称";
		}
		return null;
	}
}
