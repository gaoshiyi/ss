package com.sstc.hmis.permission.management.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.permission.management.data.WorkflowData;

@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/workflowApprover")
public interface WorkflowApprover {

	/**
	 * 获取所有工作流模板
	 * @return
	 */
	@RequestMapping(value = "/getWfPrcfIdList", method = RequestMethod.POST)
	public List<WorkflowData> getWfPrcfIdList();

	/**
	 * 获取某个工作流模板的流程节点配置
	 * @param wfPrcfId 流程模板Id
	 * @return
	 */
	@RequestMapping(value = "/getTaskIdByPrcfId", method = RequestMethod.POST)
	public List<WorkflowData> getTaskIdByPrcfId(@RequestParam("wfPrcfId") String wfPrcfId);

	/**
	 * 审批策略
	 * @return
	 */
	@RequestMapping(value = "/getAuditType", method = RequestMethod.POST)
	public List<WorkflowData> getAuditType();

	/**
	 * 保存配置
	 * @param wfPrcfId 工作流Id
	 * @param wfTaskKey 任务Id
	 * @param type 类型
	 * @param auditId 职务或人员ID
	 * @param grpId 集团
	 * @param hotelId 酒店
	 * @param userId 当前用户
	 */
	@RequestMapping(value = "/saveWfAuditDetail", method = RequestMethod.POST)
	public void saveWfAuditDetail(@RequestParam("wfPrcfId") String wfPrcfId,
			@RequestParam("wfTaskKey") String wfTaskKey, @RequestParam("type") String type,
			@RequestBody List<String> auditId, @RequestParam("grpId") String grpId,
			@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId);
	
	/**
	 * 查询是否已添加过
	 * @param wfPrcfId 工作流Id
	 * @param auditId 职务或人员ID
	 * @param hotelId 酒店ID
	 * @param grpId 集团Id
	 */
	@RequestMapping(value = "/getCount", method = RequestMethod.POST)
	public int getCount(@RequestParam("wfPrcfId") String wfPrcfId,
			@RequestBody List<String> auditId, @RequestParam("type") String type,
			@RequestParam("grpId") String grpId, @RequestParam("hotelId") String hotelId);

}
