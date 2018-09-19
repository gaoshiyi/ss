package com.sstc.hmis.permission.dbaccess.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermission;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermissionExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
import com.sstc.hmis.permission.dbaccess.data.domain.PermParent;
import com.sstc.hmis.permission.dbaccess.data.domain.PermsTPermissionVo;
@Mapper
public interface PermsTPermissionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int countByExample(PermsTPermissionExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int deleteByExample(PermsTPermissionExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int deleteByPrimaryKey(String clId);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int insert(PermsTPermission record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int insertSelective(PermsTPermission record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	List<PermsTPermission> selectByExample(PermsTPermissionExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	PermsTPermission selectByPrimaryKey(String clId);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int updateByExampleSelective(@Param("record") PermsTPermission record,
			@Param("example") PermsTPermissionExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int updateByExample(@Param("record") PermsTPermission record, @Param("example") PermsTPermissionExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int updateByPrimaryKeySelective(PermsTPermission record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_permission
	 * @mbggenerated  Thu May 11 10:48:33 CST 2017
	 */
	int updateByPrimaryKey(PermsTPermission record);
	/**
	 * @author CKang
	 * @param roleId
	 * @return
	 */
	List<PermsTPermission> loadcheckedPermissionList(String roleId);
	/**
	 * @param clStaffId
	 * @return
	 */
	List<PermsTPermission> findPermissionByStaffId(PermsTStaffRole permsTStaffRole);

	/**
	 * @return
	 */
	List<TreeNode> selectPermTree();

	/**
	 * @param permsTPermission
	 * @return
	 */
	Page<PermsTPermissionVo> pageList(PermsTPermission permsTPermission);
	/**
	 * @param condition
	 * @return
	 */
	List<PermsTPermission> selectAllPermsByRoleIds(Map<String, Object> condition);
	/**
	 * @return
	 */
	List<PermsTPermission> findAllPerms();
	/**
	 * @param condition
	 * @return
	 */
	List<PermsTPermission> listUserMenu(Map<String, Object> condition);
	/**
	 * 权限ID查询父级权限
	 * @param idList 权限ID列表
	 * @return
	 */
	List<PermParent> selectPermSystemId(@Param("idList")List<String> idList);
	/**
	 * @param permId
	 * @return
	 */
	List<PermsTPermission> getSystemPermissionById(@Param("permId") String permId);
	
}