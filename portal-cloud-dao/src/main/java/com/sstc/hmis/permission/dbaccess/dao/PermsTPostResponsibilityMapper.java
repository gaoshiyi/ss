package com.sstc.hmis.permission.dbaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibilityExample;
@Mapper
public interface PermsTPostResponsibilityMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int countByExample(PermsTPostResponsibilityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int deleteByExample(PermsTPostResponsibilityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int deleteByPrimaryKey(String clId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int insert(PermsTPostResponsibility record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int insertSelective(PermsTPostResponsibility record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	List<PermsTPostResponsibility> selectByExample(PermsTPostResponsibilityExample example);
	
	/**
	 * 根据部门id查询职位信息
	 * @param deptId
	 * @return
	 */
	List<PermsTPostResponsibility> findPostResponsibilityByDeptId(PermsTPostResponsibility permsTPostResponsibility);
	
	
	List<PermsTPostResponsibility> findPostResponsibilityByDeptIdCopy(PermsTPostResponsibility permsTPostResponsibility);
	
	/**
	 * 通过职位id查询员工数量
	 * @param postId
	 * @return
	 */
	int findStaffCountByPostResponsibilityId(String postId);
	
	/**
	 * 验证同一酒店下，是否存在相同的职位名称
	 * @author CKang
	 * @date 2017年4月20日 上午9:58:30
	 * @param postId
	 * @param hotelId
	 * @return
	 */
	int validPostName(PermsTPostResponsibility permsTPostResponsibility);
	
	/**
	 * 验证同一酒店下，是否存在相同的职位名称,去除当前职位以外
	 * @author CKang
	 * @date 2017年5月3日 上午9:46:27
	 * @param permsTPostResponsibility
	 * @return
	 */
	int validPostNameNotId(PermsTPostResponsibility permsTPostResponsibility);
	
	/**
	 * 根据职位id查询组织
	 * @author CKang
	 * @date 2017年4月18日 下午2:28:15
	 * @param postId
	 * @return
	 */
	String findOrganizationByPostId(String postId);
	
	/**
	 * 根据部门id查询组织
	 * @author CKang
	 * @date 2017年4月25日 下午2:23:09
	 * @param deptId
	 * @return
	 */
	String findOrganizationByDeptId(String deptId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	PermsTPostResponsibility selectByPrimaryKey(String clId);
	

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int updateByExampleSelective(@Param("record") PermsTPostResponsibility record,
			@Param("example") PermsTPostResponsibilityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int updateByExample(@Param("record") PermsTPostResponsibility record,
			@Param("example") PermsTPostResponsibilityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int updateByPrimaryKeySelective(PermsTPostResponsibility record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_post_responsibility
	 * @mbggenerated  Fri Apr 07 11:38:15 CST 2017
	 */
	int updateByPrimaryKey(PermsTPostResponsibility record);
	
	int findPostCountByRoleId(PermsTPostResponsibility record);
	
	/**
	 * 通过部门查询职位信息
	 * @author CKang
	 * @date 2017年5月24日 下午2:44:55
	 * @param clDeptId
	 * @return
	 */
	List<PermsTPostResponsibility> findPostByDeptId(String clDeptId);
	
	/**
	 * 通过用户id查询职位信息
	 * @author CKang
	 * @date 2017年5月26日 上午11:16:48
	 * @param staffId
	 * @return
	 */
	List<PermsTPostResponsibility> findPostByStaffId(String staffId);

	/**
	 * @param example
	 * @return
	 */
	PermsTPostResponsibility selectOneByExample(PermsTPostResponsibilityExample example);
	
	/**
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称
	 * @author denghaibo
	 * @date 2017年4月20日 上午9:58:30
	 * @param postId
	 * @param hotelId
	 * @return
	 */
	int validDeptPostName(PermsTPostResponsibility permsTPostResponsibility);
	
	/**
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称  去除自己 修改使用
	 * @author denghaibo
	 * @date 2017年4月20日 上午9:58:30
	 * @param postId
	 * @param hotelId
	 * @return
	 */
	int validDeptPostNameNotId(PermsTPostResponsibility permsTPostResponsibility);
}