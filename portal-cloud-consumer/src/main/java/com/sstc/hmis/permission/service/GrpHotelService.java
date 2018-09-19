package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.ContactsInfo;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.GroupInfo;
import com.sstc.hmis.permission.data.LinkHotelInfo;
import com.sstc.hmis.permission.data.LinkmanDeptInfo;
import com.sstc.hmis.permission.data.LinkmanInfo;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.data.WorkGroupInfo;

/**
 * 
 * 酒店管理Service
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version Date: 2017年4月12日 下午2:30:14
 * @serial 1.0
 * @since 2017年4月12日 下午2:30:14
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/grpHotelService")
public interface GrpHotelService {

	/**
	 * 查询员工当前工作酒店的组织机构，酒店ID从登录信息中获取
	 * @param orgLevel 查询深度 ，参考 {@link Constants} ORG_PATH_*
	 * @return 组织树
	 */
	@RequestMapping(value = "/list1" , method = RequestMethod.POST)
	public List<TreeNode> list(@RequestParam("orgLevel")Short orgLevel);

	/**
	 * 查询员工当前工作酒店的组织机构，如果当前酒店为集团会加入特殊逻辑，权限系统逻辑，其他功能请使用 list5
	 * @param hotelId 酒店ID
	 * @param orgLevel 组织级别 ，参考 {@link Constants} ORG_PATH_*
	 * @return 组织树
	 */
	@RequestMapping(value = "/list2" , method = RequestMethod.POST)
	public List<TreeNode> list2(@RequestParam("orgLevel")Short orgLevel, @RequestParam("hotelId")String hotelId);
	
	/**
	 * 查询酒店下部门树
	 * @param hotelId 酒店ID
	 * @return
	 */
	@RequestMapping(value = "/list3" , method = RequestMethod.POST)
	public List<TreeNode> list(@RequestParam("hotelId")String hotelId);
	
	/**
	 * 查询员工当前工作酒店的组织机构
	 * @param hotelId 酒店ID
	 * @param orgLevel 组织级别 ，参考 {@link Constants} ORG_PATH_*
	 * @return 组织树
	 */
	@RequestMapping(value = "/list5" , method = RequestMethod.POST)
	public List<TreeNode> list(@RequestParam("orgLevel")Short orgLevel, @RequestParam("hotelId")String hotelId);
	
	/**
	 * 查询酒店组织树
	 * @param orgLevel 组织深度
	 * @param hotelId 酒店ID
	 * @param disableLevel 禁止选中的组织级别
	 * @return
	 */
	@RequestMapping(value = "/list4" , method = RequestMethod.POST)
	public List<TreeNode> list(@RequestParam("orgLevel")Short orgLevel, @RequestParam("hotelId")String hotelId, @RequestParam("disableLevel")Integer disableLevel);

	@RequestMapping(value = "/updateStatusByPrimaryKeys" , method = RequestMethod.POST)
	public int updateStatusByPrimaryKeys(@RequestBody List<String> ids, @RequestParam("status")int status) throws AppException;
	
	/**
	 * 查询除当前登录酒店以外的酒店信息
	 * @author CKang
	 * @date 2017年4月21日 上午9:59:22
	 * @return
	 */
	@RequestMapping(value = "/findGrpHotelNotByHotelId" , method = RequestMethod.POST)
	public List<GroupHotel> findGrpHotelNotByHotelId();
	
	@RequestMapping(value = "/findGroupHotelById" , method = RequestMethod.POST)
	public GroupHotel findGroupHotelById(@RequestParam("id") String id);

	
	@RequestMapping(value = "/findStaffHotelByStaffId" , method = RequestMethod.POST)
	public List<GroupHotel> findStaffHotelByStaffId(@RequestBody StaffHotel staffHotel);
	
	/**
	 * 查询集团下的所有酒店
	 * @author CKang
	 * @date 2017年5月10日 下午3:16:30
	 * @param grpId
	 * @return
	 */
	@RequestMapping(value = "/findhotelList" , method = RequestMethod.POST)
	public List<GroupHotel> findhotelList(@RequestParam("grpId") String grpId);
	

	/**
	 * 根据组织信息查询组织所属的酒店信息
	 * @param orgId 组织ID，可以是集团、酒店、部门、职位的ID
	 * @param orgType 组织类型
	 * @return
	 */
	@RequestMapping(value = "/findGrpHotelByOrgInfo" , method = RequestMethod.POST)
	public GroupHotel findGrpHotelByOrgInfo(@RequestParam("orgId")String orgId, @RequestParam("orgType")Short orgType);
	
	@RequestMapping(value = "/getGroupInfoByHotelId" , method = RequestMethod.POST)
	List<GroupInfo> getGroupInfoByHotelId(Map<String, Object> paramMap);
	
	/**
	 * 依据部门ID获取人员列表
	 * @param paramMap 参数Map
	 * @return 查询结果
	 */
	@RequestMapping(value = "/getUserIdsByDeptId" , method = RequestMethod.POST)
	List<String> getUserIdsByDeptId(Map<String, Object> paramMap);
	
	@RequestMapping(value = "/getWorkGroupList" , method = RequestMethod.POST)
	List<WorkGroupInfo> getWorkGroupList(Map<String, Object> paramMap);
	
	@RequestMapping(value = "/getUserListByWorkGroupId" , method = RequestMethod.POST)
	List<LinkmanInfo> getUserListByWorkGroupId(Map<String, Object> paramMap);
	
	@RequestMapping(value = "/getAllUser" , method = RequestMethod.POST)
	List<LinkmanInfo> getAllUser(Map<String, Object> paramMap);
	
	@RequestMapping(value = "/getLinkmanHotelInfoList" , method = RequestMethod.POST)
	List<LinkHotelInfo> getLinkmanHotelInfoList(Map<String, Object> paramMap);
	
	/**
	 * 获取酒店下部门职位信息列表
	 * @param paramMap 参数Map
	 */
	@RequestMapping(value = "/getLinkmanDeptInfoList" , method = RequestMethod.POST)
	List<LinkmanDeptInfo> getLinkmanDeptInfoList(Map<String, Object> paramMap);
	
	/**
	 * 过滤联系人列表
	 * @param paramMap 参数Map
	 */
	@RequestMapping(value = "/getContactsInfoList" , method = RequestMethod.POST)
	List<ContactsInfo> getContactsInfoList(Map<String, Object> paramMap);

	/**
	 * @param hotel
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<GroupHotel> list(@RequestBody GroupHotel hotel) throws AppException;

	/**
	 * @param hotel
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public GroupHotel find(@RequestBody GroupHotel hotel) throws AppException;

	/**
	 * @param index
	 * @param size
	 * @param hotel
	 * @return
	 */
	@RequestMapping(value = "/lisPaget", method = RequestMethod.POST)
	public PageResult<GroupHotel> list(@RequestParam("index")int index, @RequestParam("size")int size, @RequestBody GroupHotel hotel) throws AppException;
	
	/**
	 * 获取所有有效酒店
	 */
	@RequestMapping(value = "/getAllHotel", method = RequestMethod.POST)
	public List<String> getAllHotel();
}
