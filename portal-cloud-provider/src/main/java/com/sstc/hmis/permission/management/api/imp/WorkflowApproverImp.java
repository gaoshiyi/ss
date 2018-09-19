package com.sstc.hmis.permission.management.api.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.permission.management.api.WorkflowApprover;
import com.sstc.hmis.permission.management.data.WorkflowData;
import com.sstc.hmis.wf.service.workflow.api.WfCommonService;

@RestController
public class WorkflowApproverImp implements WorkflowApprover{

	@Autowired
	private WfCommonService wfCommonService;
	
	@Override
	public List<WorkflowData> getWfPrcfIdList() {
		List<WorkflowData> ret = new ArrayList<WorkflowData>();
		List<Map<String, Object>> mapList = wfCommonService.getWfPrcfIdList();
		WorkflowData tmp = null;
		for(Map<String, Object> map : mapList) {
			tmp = new WorkflowData();
			tmp.setKey(map.get("key")==null?null:map.get("key").toString());
			tmp.setName(map.get("name")==null?null:map.get("name").toString());
			ret.add(tmp);
		}
		return ret;
	}

	@Override
	public List<WorkflowData> getTaskIdByPrcfId(String wfPrcfId) {
		Assert.hasText(wfPrcfId, "parameter:\"wfPrcfId\" is not allowed empty!");
		List<WorkflowData> ret = new ArrayList<WorkflowData>();
		List<Map<String, Object>> mapList = wfCommonService.getTaskIdByPrcfId(wfPrcfId);
		WorkflowData tmp = null;
		for(Map<String, Object> map : mapList) {
			tmp = new WorkflowData();
			tmp.setKey(map.get("key")==null?null:map.get("key").toString());
			tmp.setName(map.get("name")==null?null:map.get("name").toString());
			ret.add(tmp);
		}
		return ret;
	}

	@Override
	public List<WorkflowData> getAuditType() {
		List<WorkflowData> ret = new ArrayList<WorkflowData>();
		List<Map<String, Object>> mapList = wfCommonService.getAuditType();
		WorkflowData tmp = null;
		for(Map<String, Object> map : mapList) {
			tmp = new WorkflowData();
			tmp.setKey(map.get("key")==null?null:map.get("key").toString());
			tmp.setName(map.get("name")==null?null:map.get("name").toString());
			ret.add(tmp);
		}
		return ret;
	}

	@Override
	public void saveWfAuditDetail(String wfPrcfId, String wfTaskKey, String type, @RequestBody List<String> auditId, String grpId,
			String hotelId, String userId) {
		Assert.hasText(wfPrcfId, "parameter:\"wfPrcfId\" is not allowed empty!");
		Assert.hasText(wfTaskKey, "parameter:\"wfTaskKey\" is not allowed empty!");
		Assert.notEmpty(auditId, "parameter:\"auditId\" is not allowed empty!");
		Assert.hasText(grpId, "parameter:\"grpId\" is not allowed empty!");
		Assert.hasText(hotelId, "parameter:\"hotelId\" is not allowed empty!");
		Assert.hasText(userId, "parameter:\"userId\" is not allowed empty!");
		
		wfCommonService.saveWfAuditDetial(wfPrcfId, wfTaskKey, type, auditId, grpId, hotelId, userId);
	}

	@Override
	public int getCount(String wfPrcfId, @RequestBody List<String> auditId, String type, String grpId, String hotelId) {
		
		return wfCommonService.getCount(wfPrcfId, auditId, type, grpId, hotelId);
	}

}
