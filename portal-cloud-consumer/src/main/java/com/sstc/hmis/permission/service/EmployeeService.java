package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.Role;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;

@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/employeeService")
public interface EmployeeService {
	/**
	 * 带查询条件的Grid表格展示；
	 */
//	@RequestMapping(value = "/list", method = RequestMethod.POST)
//	PageResult<Staff> list(@RequestParam("index")int index, @RequestParam("size")int size, @RequestParam("nodeType")Short nodeType,  
//			@RequestParam("statusVal")Short statusVal, @RequestParam("infoVal")String infoVal) throws AppException;

	/**
	 * 获取员工管理信息；
	 */
	@RequestMapping(value = "/getEmployeeInfo", method = RequestMethod.POST)
	Staff getEmployeeInfo(@RequestParam("id") String id);

	/**
	 * 带查询条件的Grid表格展示2；
	 */
	@RequestMapping(value = "/listSel", method = RequestMethod.POST)
	PageResult<Staff> listSel(@RequestParam("index")int index, @RequestParam("size")int size, @RequestParam("nodeType")Short nodeType, 
			@RequestParam("orgId")String orgId, @RequestParam("statusVal")Short statusVal, @RequestParam("infoVal")String infoVal,
			@RequestParam("postId2")String postId2, @RequestParam("teamId")String teamId, 
			@RequestParam("orgIdTab")String orgIdTab, @RequestParam("nodeTypeTab")Short nodeTypeTab) throws AppException;;

	/**
	 * 保存成功后返回Id；
	 */
	@RequestMapping(value = "/insertBackId", method = RequestMethod.POST)
	Result insertBackId(@RequestBody Staff staff) throws AppException;

	/**
	 * 修改成功后返回Id；
	 */
	@RequestMapping(value = "/updateStaffInfo1", method = RequestMethod.POST)
	Result updateStaffInfo(@RequestBody Staff staff) throws AppException;

	/**
	 * 根据Id获取登录酒店信息；
	 */
	@RequestMapping(value = "/getWorkHotelInfosMap", method = RequestMethod.POST)
	Map<String, String> getWorkHotelInfos(@RequestParam("hotelIdVal")String hotelIdVal);

	/**
	 * 保存登陆策略信息；
	 */
	@RequestMapping(value = "/saveStrateryInfo", method = RequestMethod.POST)
	int saveStrateryInfo(@RequestBody StaffHotel staffHotel);;

	/**
	 * 根据酒店Id和员工Id动态获取登陆策略值；
	 */
	@RequestMapping(value = "/getStaffHotelInfoList", method = RequestMethod.POST)
	List<StaffHotel> getStaffHotelInfo(@RequestParam("staffId")String staffId, @RequestParam("workHotelId")String workHotelId);

	/**
	 * 根据职位Id和员工Id保存工作酒店信息；
	 */
	@RequestMapping(value = "/savedWorkHotel", method = RequestMethod.POST)
	int savedWorkHotel(@RequestParam("postIdArr")String[] postIdArr, @RequestParam("staffVal")String staffVal);

	/**
	 * 获取员工工作酒店信息；
	 */
	@RequestMapping(value = "/getStaffWorkHotelInfoList", method = RequestMethod.POST)
	List<StaffHotel> getStaffWorkHotelInfo(@RequestParam("id")String id);

	/**
	 * 获取所有酒店的信息；
	 */
	@RequestMapping(value = "/getAllHotels", method = RequestMethod.POST)
	Map<String, String> getAllHotels();

	/**
	 * 根据员工Id获取工作酒店的组织信息；
	 */
	@RequestMapping(value = "/getorgGridInfos", method = RequestMethod.POST)
	List<Staff> getorgGridInfos(@RequestParam("staffId")String staffId);

	/**
	 * 验证证件号；
	 */
	@RequestMapping(value = "/validcertNumber", method = RequestMethod.POST)
	String validcertNumber(@RequestParam("certTypeCode")String certTypeCode, @RequestParam("certno")String certno, @RequestParam("id")String id);

	/*
	 * 锁定数据；
	 */
	@RequestMapping(value = "/lockTeamData", method = RequestMethod.POST)
	String lockTeamData(@RequestParam("idvalue")String idvalue);

	/**
	 * 分页查询员工信息
	 * @param index 分页起始
	 * @param size 数量
	 * @param orgId 组织ID
	 * @param nodeType 组织类型 参考 {@link Constants} NODE_TYPE_*
	 * @param status 状态   参考 {@link Constants} STATUS_*
	 * @param info 名字/手机号/工号
	 * @param teamId 工作圈ID
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping(value = "/pageList", method = RequestMethod.POST)
	PageResult<Staff> pageList(@RequestParam("index")int index, @RequestParam("size")int size, @RequestParam("nodeType")Short nodeType, 
			@RequestParam("orgId")String orgId, @RequestParam("status")Short status, @RequestParam("info")String info,
			@RequestParam("teamId")String teamId, @RequestParam("roleId")String roleId) throws AppException;

	/**
	 * 剔除自己分页查询员工信息
	 * @param index 分页起始
	 * @param size 数量
	 * @param orgId 组织ID
	 * @param nodeType 组织类型 参考 {@link Constants} NODE_TYPE_*
	 * @param status 状态   参考 {@link Constants} STATUS_*
	 * @param info 名字/手机号/工号
	 * @param teamId 工作圈ID
	 * @param roleId 角色ID
	 * @param loginUserId 当前登录用户ID
	 * @return
	 */
	@RequestMapping(value = "/pageListOutMe", method = RequestMethod.POST)
	PageResult<Staff> pageListOutMe(@RequestParam("index")int index, @RequestParam("size")int size, @RequestParam("nodeType")Short nodeType, 
			@RequestParam("orgId")String orgId, @RequestParam("status")Short status, @RequestParam("info")String info,
			@RequestParam("teamId")String teamId, @RequestParam("roleId")String roleId, @RequestParam("loginUserId") String loginUserId) throws AppException;
	
	
	/*
	 * 解决836Bug
	 */
	@RequestMapping(value = "/hasPost", method = RequestMethod.POST)
	String hasPost(@RequestParam("postId")String postId);

	@RequestMapping(value = "/findStaffById", method = RequestMethod.POST)
	Staff findStaffById(@RequestParam("staffId")String staffId);

	/**
	 * 通过员工id查询组织
	 * 
	 * @author CKang
	 * @date 2017年5月16日 下午5:12:29
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/findOrganizationByStaffId", method = RequestMethod.POST)
	public List<String> findOrganizationByStaffId(@RequestBody Staff staff);

	/**
	 * 通过角色id查询员工信息
	 * 
	 * @author CKang
	 * @date 2017年5月17日 下午4:48:25
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findStaffListByRoleId", method = RequestMethod.POST)
	public List<Staff> findStaffListByRoleId(@RequestBody Role role);

	@RequestMapping(value = "/findStaffListByRoleIdLeft", method = RequestMethod.POST)
	public List<Staff> findStaffListByRoleIdLeft(@RequestBody Role role);

	/**
	 * @param staff
	 * @param hotel
	 * @return
	 */
	@RequestMapping(value = "/deleteStaff", method = RequestMethod.POST)
	Result deleteStaff(@RequestBody Staff staff, @RequestParam("hotelId") String hotelId);
	
	/**
	 * 模糊查询员工信息及组织结构信息
	 * @param queryInfo 模糊查询信息：中文名/英文名/手机号码/工号
	 * @param hotelId 酒店ID
	 * @return 员工信息列表
	 */
	@RequestMapping(value = "/listStaffWithOrgInfo", method = RequestMethod.POST)
	List<Staff> listStaffWithOrgInfo(@RequestParam("queryInfo")String queryInfo,@RequestParam("hotelId")String hotelId);
	/**
	 * 查询员工的常用联系人
	 * @param staffId 员工ID
	 * @param hotelId 酒店ID
	 * @return
	 */
	@RequestMapping(value = "/listStaffLinkman", method = RequestMethod.POST)
	List<Staff> listStaffLinkman(@RequestParam("staffId")String staffId,@RequestParam("hotelId")String hotelId);
	
	/**
	 * 根据员工ID查询员工信息
	 * @param idList 员工ID列表
	 * @param hotelId 员工工作酒店ID
	 * @return
	 */
	@RequestMapping(value = "/listStaffById", method = RequestMethod.POST)
	List<Staff> listStaffById(@RequestBody List<String> idList,@RequestParam("hotelId")String hotelId);
	/**
	 * 保存员工常用联系人信息
	 * @param userId 员工ID
	 * @param hotelId 酒店ID
	 * @param grpId 集团ID
	 */
	@RequestMapping(value = "/saveLinkman", method = RequestMethod.POST)
	int saveLinkman(@RequestParam("userId") String userId, @RequestParam("linkmanId") String linkmanId);
	
	/**
	 * 查询是否已添加常用联系人
	 * @param userId 当前登录用户ID
	 * @param linkmanId 联系人ID
	 */
	@RequestMapping(value = "/getOftenLinkman", method = RequestMethod.POST)
	int getOftenLinkman(@RequestParam("userId") String userId, @RequestParam("linkmanId") String linkmanId);
	
	/**
	 * 删除常用联系人
	 * @param userId 当前登录用户Id
	 * @param linkmanId 联系人ID
	 */
	@RequestMapping(value = "/delOften", method = RequestMethod.POST)
	void delOften(@RequestParam("userId") String userId, @RequestParam("linkmanId") String linkmanId);
	
	/**
	 * 获取常用联系人
	 * @param userId 当前登录用户ID
	 * @param hotelId 酒店ID
	 * @param grpId 集团ID
	 * @return 查询结果
	 */
	@RequestMapping(value = "/getLinkmanInfoList", method = RequestMethod.POST)
	List<Staff> getLinkmanInfoList(@RequestParam("userId") String userId, @RequestParam("hotelId") String hotelId, @RequestParam("grpId") String grpId);
	
	/**
	 * 根据职位编码查询员工列表
	 * @param postCode 职位编码
	 * @param hotelId 酒店ID
	 * @return
	 */
	@RequestMapping(value = "/getStaffByPostCode", method = RequestMethod.POST)
	PageResult<Staff> getStaffByPostCode(@RequestParam("index")int index, @RequestParam("size")int size,@RequestParam("postCode")String postCode,@RequestParam("hotelId")String hotelId);

	/**
	 * 管理员操作密码重置
	 * @param id 用户ID
	 * @return
	 */
	@RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
	Result resetPwd(@RequestParam("id") String id);

	/**
	 * 更新密码
	 * @param newPwd 
	 * @param oldPwd 
	 * @return
	 */
	@RequestMapping(value = "/updatePwd",method = RequestMethod.POST)
	Result updatePwd(@RequestParam("oldPwd")String oldPwd, 
			@RequestParam("newPwd")String newPwd,@RequestParam("userId")String userId);
	
	/**
	 * 依据员工ID获取所属组织信息
	 * @param staffId 员工ID
	 */
	@RequestMapping(value = "/getOrgInfoByStaffId", method = RequestMethod.POST)
	String getOrgInfoByStaffId(@RequestParam("staffId") String staffId);
	/**
	 * 查询员工（新审批人管理新建按人员）
	 * @param index
	 * @param size
	 * @param nodeType
	 * @param orgId
	 * @param staffName
	 * @return
	 */
	@RequestMapping(value = "/pageListForAddPer", method = RequestMethod.POST)
	List<Staff> pageListForAddPer(@RequestParam("nodeType") Short nodeType,@RequestParam("orgId") String orgId,@RequestParam("staffName") String staffName);
}
