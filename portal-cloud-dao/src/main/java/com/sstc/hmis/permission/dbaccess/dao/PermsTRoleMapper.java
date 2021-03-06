package com.sstc.hmis.permission.dbaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sstc.hmis.permission.dbaccess.data.PermsTRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTRoleExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
@Mapper
public interface PermsTRoleMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int countByExample(PermsTRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int deleteByExample(PermsTRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int deleteByPrimaryKey(String clId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int insert(PermsTRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int insertSelective(PermsTRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	List<PermsTRole> selectByExample(PermsTRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	PermsTRole selectByPrimaryKey(String clId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int updateByExampleSelective(@Param("record") PermsTRole record, @Param("example") PermsTRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int updateByExample(@Param("record") PermsTRole record, @Param("example") PermsTRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int updateByPrimaryKeySelective(PermsTRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table perms.perms_t_role
	 * @mbggenerated  Wed Apr 05 13:58:26 CST 2017
	 */
	int updateByPrimaryKey(PermsTRole record);
	
	List<PermsTRole> findAllRole(PermsTRole role);
	
	
	List<PermsTRole> findRoleByStaffIdRight(PermsTStaffRole staffRole);
	
	List<PermsTRole> findRoleByStaffIdLeft(PermsTStaffRole staffRole);
	
	List<PermsTRole> findNotLocalRoleList(PermsTRole record);
	
	int findStaffCountByRoleId(PermsTRole record);
	

	
	List<String> findStaffListByRoleId(PermsTStaffHotel permsTStaffHotel);
	
	
}