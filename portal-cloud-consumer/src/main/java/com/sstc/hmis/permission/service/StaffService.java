/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.LinkmanInfo;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.data.StaffRole;
import com.sstc.hmis.permission.data.StaffTeam;
import com.sstc.hmis.permission.data.cas.PrincipalVo;
import com.sstc.hmis.permission.data.cas.SimpleVo;

/**
 * <p> Title: StaffService</p>
 * <p> Description: 酒店员工信息 </p>
 * <p> Company: SSTC </p>
 * 
 * @author Qxiaoxiang
 * @date 2017年3月30日 上午9:29:25
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/staffService")
public interface StaffService{
    
    /**
     * 通过帐号信息查询用户ID
     * 
     * @param account 员工帐号
     * @return
     */
    @RequestMapping(value = "/queryByUserId", method = RequestMethod.POST)
    Staff queryByUserId(@RequestParam("account") String account);
    
    /**
     * 查找员工的工作酒店列表
     * 
     * @param staffId 员工ID
     * @return
     */
    @RequestMapping(value = "/findUserStaffHotel", method = RequestMethod.POST)
    List<SimpleVo> findUserStaffHotel(@RequestParam("staffId") String staffId);
    
    /**
     * 根据输入条件查询员工信息
     * 
     * @param staff
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    Staff login(@RequestBody Staff staff)
        throws AppException;
    
    /**
     * 用户注册
     * 
     * @param staff
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    int regist(@RequestBody Staff staff)
        throws AppException;
    
    /**
     * 根据酒店信息查询酒店下的工作员工
     * 
     * @param staffHotel
     * @return
     */
    @RequestMapping(value = "/listByStaffHotel", method = RequestMethod.POST)
    List<Staff> listByStaffHotel(@RequestBody StaffHotel staffHotel)
        throws AppException;
    
    /**
     * 根据属性查询对象，需要根据需求自行扩展
     * 
     * @param t
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    Staff find(@RequestBody Staff t);
    
    /**
     * 获取员工工作圈信息；
     * 
     * @param staffInfo
     * @return
     */
    @RequestMapping(value = "/getWorkTeamInfos", method = RequestMethod.POST)
    List<StaffTeam> getWorkTeamInfos(@RequestBody Staff staffInfo);
    
    /**
     * 获取员工角色信息；
     * 
     * @param staffInfo
     * @return
     */
    @RequestMapping(value = "/getStaffRoleInfos", method = RequestMethod.POST)
    List<StaffRole> getStaffRoleInfos(@RequestBody Staff staffInfo);
    
    /**
     * 依据用户ID，酒店ID获取当前角色ID
     */
    @RequestMapping(value = "/getStaffRoleId", method = RequestMethod.POST)
    List<String> getStaffRoleId(@RequestParam("staffId") String staffId, @RequestParam("hotelId") String hotelId);
    
    /**
     * 依据角色ID，酒店ID获取权限列表
     */
    @RequestMapping(value = "/getRolePermissionList", method = RequestMethod.POST)
    List<String> getRolePermissionList(@RequestBody List<String> roleIdList, @RequestParam("hotelId") String hotelId);
    
    /**
     * 完善资料
     * 
     * @author CKang
     * @date 2017年5月12日 下午3:21:32
     * @param staffId
     * @return
     */
    @RequestMapping(value = "/updateStaffByStaffId", method = RequestMethod.POST)
    public int updateStaffByStaffId(@RequestBody Staff staffInfo);
    
    /**
     * 通过id查询员工信息
     * 
     * @author CKang
     * @date 2017年5月12日 下午3:37:24
     * @param staffId
     * @return
     */
    @RequestMapping(value = "/findStaffByStaffId", method = RequestMethod.POST)
    public Staff findStaffByStaffId(@RequestParam("staffId") String staffId);
    
    /**
     * @param staffInfo
     * @return
     */
    @RequestMapping(value = "/getUserLoginSuccessTimes", method = RequestMethod.POST)
    int getUserLoginSuccessTimes(@RequestBody Staff staffInfo);
    
    /**
     * @param staffInfo
     */
    @RequestMapping(value = "/insertLoginInfo", method = RequestMethod.POST)
    int insertLoginInfo(@RequestBody Staff staffInfo);
    
    /**
     * @author CKang
     * @date 2017年5月25日 上午10:50:48
     * @param staffId 员工id
     * @param deptId 部门id
     * @param hotelId 酒店id
     * @param postId 职位id
     * @param type 类型
     * @param expireTime 过期时间
     * @param modifyBy 修改人
     */
    @RequestMapping(value = "/auditStaff", method = RequestMethod.POST)
    public void auditStaff(@RequestParam("staffId") String staffId, @RequestParam("deptId") String deptId, @RequestParam("hotelId") String hotelId,
        @RequestParam("postId") String postId, @RequestParam("type") Short type, @RequestParam("expireTime") Date expireTime,
        @RequestParam("modifyBy") String modifyBy);
    
    /**
     * 获取当前用户信息
     * 
     * @param userIdList 用户ID列表
     * @return 用户信息
     */
    @RequestMapping(value = "/getStaffInfo", method = RequestMethod.POST)
    List<Staff> getStaffInfo(@RequestBody List<String> userIdList);
    
    @RequestMapping(value = "/getStaffIdListByJobId", method = RequestMethod.POST)
    List<String> getStaffIdListByJobId(String jobId);
    
    @RequestMapping(value = "/getStaffById", method = RequestMethod.POST)
    LinkmanInfo getStaffById(@RequestBody Map<String, Object> paramMap)
        throws Exception;
    
    /**
     * 审核不通过 更新Staff表数据状态
     * 
     * @param paramMap 参数Map
     */
    @RequestMapping(value = "/updateStaffStatus", method = RequestMethod.POST)
    void updateStaffStatus(@RequestBody Map<String, Object> paramMap);
    
    /**
     * 审核通过更新密码
     * 
     * @param paramMap 参数Map
     */
    @RequestMapping(value = "/updateStaffPsd", method = RequestMethod.POST)
    void updateStaffPsd(@RequestBody Map<String, Object> paramMap);
    
    @RequestMapping(value = "/findStaffByPhoneAccount", method = RequestMethod.POST)
    List<Staff> findStaffByPhoneAccount(@RequestBody Map<String, Object> paramMap);
    
    @RequestMapping(value = "/insertSelective", method = RequestMethod.POST)
    int insertSelective(@RequestBody Staff staff)
        throws Exception;
    
    @RequestMapping(value = "/updateByPrimaryKeySelective", method = RequestMethod.POST)
    int updateByPrimaryKeySelective(@RequestBody Staff staff)
        throws Exception;

	/**
	 * 认证中心登录
	 * @param userName 帐号
	 * @param password 密码
	 * @param deviceType 设备类型 @com.sstc.hmis.model.constants.Constants
	 * @return
	 */
    @RequestMapping( value = "/casSsoLogin", method = RequestMethod.POST)
    Integer casSsoLogin(@RequestParam("userName")String userName, 
			@RequestParam("password")String password,@RequestParam("deviceType")Integer deviceType);

	/**
	 * @return
	 */
    @RequestMapping( value = "/shift" , method = RequestMethod.POST)
	Result shift(@RequestBody PrincipalVo info);

    /**
     * 方法描述: 根据账号获取对应的名称
     * @author yaodm
     * @date 2017年11月28日 下午7:36:53
     * @param accnos
     * @param hotelId
     * @return List<Staff> 注意：对象中的name字段值为：familyName+name
     */
    @RequestMapping(value = "/getStaffInfoByAccnos", method = RequestMethod.POST)
    List<Staff> getStaffInfoByAccnos(@RequestBody List<String> accnos, @RequestParam("hotelId") String hotelId);
    
    /**
     * 依据部门ID获取员工信息
     */
    @RequestMapping(value = "/getStaffInfoByDeptId", method = RequestMethod.POST)
    List<Staff> getStaffInfoByDeptId(@RequestParam("deptId") String deptId, @RequestParam("hotelId") String hotelId);
    
    // add by liurongrong for 既存用户注册环信 begin
    @RequestMapping(value = "/registerHuanxin", method = RequestMethod.POST)
    void registerHuanxin();
    
    @RequestMapping(value = "/registerHuanxinByHotelId", method = RequestMethod.POST)
    void registerHuanxinByHotelId(@RequestParam("hotelId") String hotelId);
    // add by liurongrong for 既存用户注册环信 end
    
    @RequestMapping(value = "/findByIdOrAccount", method = RequestMethod.POST)
    Staff findByIdOrAccount(@RequestBody Staff staff);
}
