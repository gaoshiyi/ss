package com.sstc.hmis.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.ApproveDetail;
import com.sstc.hmis.permission.data.ApproveDetailEntity;
import com.sstc.hmis.permission.data.ApproveEntity;

/**
 * 流程审批管理Serivce
 * @author Qxiaoxiang
 *
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/workflowApproverService")
public interface WorkflowApproverService {

	/**
	 * 依据审核人ID获取对应明细信息
	 */
	@RequestMapping(value = "/getDetailByWfPrfAndType", method = RequestMethod.POST)
	List<ApproveDetail> getDetailByWfPrfAndType(@RequestParam("wfPrfId") String wfPrfId, @RequestParam("type") String type,@RequestParam("hotelId") String hotelId);

	/**
	 * 删除
	 * @param wfPrfId
	 * @param type
	 */
	@RequestMapping(value = "/deleteApprove", method = RequestMethod.POST)
	void deleteApprove(@RequestParam("wfPrfId") String wfPrfId, @RequestParam("type") String type);

	/**
	 * 保存审批人管理
	 * @param wfPrcfId
	 * @param wfTaskKey
	 * @param type
	 * @param auditIdList
	 * @param grpId
	 * @param hotelId
	 * @param userId
	 * @param dptId
	 * @param documentId
	 */
	@RequestMapping(value = "/saveWfAuditDetailNew", method = RequestMethod.POST)
	void saveWfAuditDetailNew(@RequestParam("wfPrcfId") String wfPrcfId, @RequestParam("wfTaskKey") String wfTaskKey,
			@RequestParam("type") String type, @RequestBody List<String> auditIdList, @RequestParam("grpId") String grpId,
			@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId, @RequestParam("dptId") String dptId,
			@RequestParam("documentId") String documentId);

	/**
	 * 查询明细
	 * @param pageIndex
	 * @param pageSize
	 * @param wfPrfId
	 * @param type
	 * @param hotelId
	 * @return
	 */
	@RequestMapping(value = "/getDetailByWfPrfAndTypePage", method = RequestMethod.POST)
	PageResult<ApproveDetailEntity> getDetailByWfPrfAndTypePage(@RequestParam("pageIndex")int pageIndex,@RequestParam("pageSize") int pageSize, @RequestParam("wfPrfId")String wfPrfId,
			@RequestParam("type")String type,@RequestParam("hotelId")String hotelId,@RequestParam("documentNo")String documentNo);

	/**
	 * 查询审批人
	 * @param pageIndex
	 * @param pageSize
	 * @param hotelId
	 * @return
	 */
	@RequestMapping(value = "/getApproveListPage", method = RequestMethod.POST)
	PageResult<ApproveEntity> getApproveListPage(@RequestParam("pageIndex")int pageIndex,@RequestParam("pageSize") int pageSize,@RequestParam("hotelId")String hotelId);

}
