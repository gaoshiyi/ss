/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.permission.data.GrpMember;
import com.sstc.hmis.permission.data.WorkTeam;

/**
 * <p>
 * Title: WorkTeamService
 * </p>
 * <p>
 * Description: 工作圈
 * </p>
 * <p>
 * Company: SSTC
 * </p>
 * 
 * @author Qxiaoxiang
 * @date 2017年3月31日 上午11:50:01
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/workTeamService")
public interface WorkTeamService {
	/**
	 * 已选择员工Grid数据
	 */
	@RequestMapping(value = "/selGrpMember", method = RequestMethod.POST)
	PageResult<GrpMember> selGrpMember(@RequestParam("teamId") String teamId, @RequestParam("roleId") String roleId)
			throws AppException;

	/**
	 * 根据Id查询圈成员信息；(右边)
	 */
	@RequestMapping(value = "/selMemberInfos", method = RequestMethod.POST)
	PageResult<GrpMember> selMemberInfos(@RequestBody List<String> idList) throws AppException;

	/**
	 * 获取未加入工作圈中员工的信息；
	 */
	@RequestMapping(value = "/selGrpMemberAll", method = RequestMethod.POST)
	List<GrpMember> selGrpMemberAll(@RequestParam("teamId") String teamId, @RequestParam("roleId") String roleId,
			@RequestParam("orgId") String orgId, @RequestParam("nodeType") Integer nodeType,
			@RequestParam("empName") String empName, @RequestParam("selStaffIdArr") String[] selStaffIdArr)
			throws AppException;

	/**
	 * 查询未被选择的员工信息；（左边）
	 */
	@RequestMapping(value = "/selMemberLeftInfos", method = RequestMethod.POST)
	PageResult<GrpMember> selMemberLeftInfos(@RequestParam("idList") List<String> idList,
			@RequestParam("roleId") String roleId, @RequestParam("orgId") String orgId,
			@RequestParam("nodeType") Integer nodeType, @RequestParam("empName") String empName,
			@RequestParam("selStaffIdArr") String[] selStaffIdArr) throws AppException;

	/**
	 * 获取编辑工作圈信息；
	 */
	@RequestMapping(value = "/getWorkTeamEditInfo", method = RequestMethod.POST)
	WorkTeam getWorkTeamEditInfo(@RequestParam("id") String id);

	/**
	 * 保存工作圈添加员工信息到工作圈关联信息表；
	 */
	@RequestMapping(value = "/saveGrpMembers", method = RequestMethod.POST)
	int saveGrpMembers(@RequestBody List<GrpMember> grpMemberList);

	/**
	 * 查询所有工作圈信息
	 * 
	 * @author CKang
	 * @date 2017年4月17日 上午10:46:39
	 * @return
	 */
	@RequestMapping(value = "/findAllWorkTeam", method = RequestMethod.POST)
	List<WorkTeam> findAllWorkTeam();

	/**
	 * 锁定数据；
	 */
	@RequestMapping(value = "/lockGrid", method = RequestMethod.POST)
	String lockGrid(@RequestParam("idvalue") String idvalue);

	/**
	 * 根据角色获取角色所属酒店信息；
	 */
	@RequestMapping(value = "/getHotelInfoByRoleId", method = RequestMethod.POST)
	Map<String, String> getHotelInfoByRoleId(@RequestParam("roleId") String roleId);

	/**
	 * 根据工作圈Id查询所有成员信息；
	 * 
	 * @param workTeamId(工作圈Id)
	 * @return
	 */
	@RequestMapping(value = "/getWortemMemebers", method = RequestMethod.POST)
	List<String> getWortemMemebers(@RequestParam("workTeamId") String workTeamId);

	/**
	 * @param index 分页起始
	 * @param size 分页数量
	 * @param workTeam 属性
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	PageResult<WorkTeam> list(@RequestParam("index") int index, @RequestParam("size") int size,
			@RequestBody WorkTeam t);

	/**
	 * 查询所有符合条件的工作圈
	 * @param workTeam
	 * @return
	 * @throws AppException
	 */
	@RequestMapping(value = "/list2", method = RequestMethod.POST)
	List<WorkTeam> list(@RequestBody WorkTeam t) throws AppException;

	/**
	 * @param t
	 * @return
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	int insert(@RequestBody WorkTeam t);

	/**
	 * @param workTeam
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	WorkTeam find(@RequestBody WorkTeam workTeam);

	/**
	 * @param workTeam
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	int update(@RequestBody WorkTeam workTeam);

	/**
	 * 模糊查询工作圈根据name
	 * @author yuankairui
	 * @since 2018/5/8
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/queryTeamList", method = RequestMethod.POST)
	PageResult<WorkTeam> queryTeamList(@RequestParam("index") int index, @RequestParam("size") int size,@RequestParam("name")String name,@RequestParam("hotelId")String hotelId);
}
