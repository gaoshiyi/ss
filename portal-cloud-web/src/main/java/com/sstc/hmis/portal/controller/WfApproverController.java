package com.sstc.hmis.portal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.management.api.WorkflowApprover;
import com.sstc.hmis.permission.management.data.WorkflowData;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

@RequestMapping("/wfapprover")
@Controller
public class WfApproverController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(WfApproverController.class);
	
	private static final String WORKTEAM_TYPE = "04";
	private static final String CUSTOMER_TYPE = "01";
	
	@Autowired
	private WorkflowApprover wfApprover;
	
	@Autowired
	private WorkTeamService workTeamService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("/mainPage")
	public String mainPage(Model model) {
		List<WorkflowData> wfdataList =  wfApprover.getWfPrcfIdList();
		model.addAttribute("wfdataList", wfdataList);
		List<WorkflowData>  auditTypeList = wfApprover.getAuditType();
		model.addAttribute("auditTypeList", auditTypeList);
		return "manage/workflow/approver";
	}
	
	@RequestMapping("/getwfprocfids")
	public @ResponseBody Result getWfPrcfIdList() {
		try {
			return new Result(wfApprover.getWfPrcfIdList());
		} catch (Throwable t) {
			logger.error("query all workflow template happening error!", t);
			return Result.errorResult(t.getMessage());
		}
	}
	
	@RequestMapping("/getTaskidsByprocfid")
	public @ResponseBody Result getTaskIdByPrcfId(String wfPrcfId) {
		try {
			return new Result(wfApprover.getTaskIdByPrcfId(wfPrcfId));
		} catch (Throwable t) {
			logger.error("query all task'ids of the workflow template happening error!", t);
			return Result.errorResult(t.getMessage());
		}
	}
	
	@RequestMapping("/getAuditType")
	public @ResponseBody Result getAuditType() {
		try {
			return new Result(wfApprover.getAuditType());
		} catch (Throwable t) {
			logger.error("query all auditTypes happening error!", t);
			return Result.errorResult(t.getMessage());
		}
	}
	
	@RequestMapping("/saveWfAuditDetail")
	public @ResponseBody Result saveWfAuditDetail(String wfPrcfId, String wfTaskKey, String type,
			String auditId) {
		 Assert.hasText(wfPrcfId);
		 Assert.hasText(wfTaskKey);
		 Assert.hasText(type);
		 Assert.hasText(auditId);
         String userId = LoginInfoHolder.getLoginInfo()==null?null:LoginInfoHolder.getLoginInfo().getId();
         Assert.hasText(userId);
         String grpId = LoginInfoHolder.getLoginGrpId();
         Assert.hasText(grpId);        
         String hotelId = LoginInfoHolder.getLoginHotelId();
         Assert.hasText(hotelId);
         
         String[] auditIdArray = auditId.split(",");
         try {
        	 // 校验审批人是否已添加过
        	 int count = wfApprover.getCount(wfPrcfId, Arrays.asList(auditIdArray), type, grpId, hotelId);
        	 if (count > 0) {
        		 Result result = Result.ERROR_SYS;
        		 return result;
        	 }
        	 wfApprover.saveWfAuditDetail(wfPrcfId, wfTaskKey, type, Arrays.asList(auditIdArray), grpId, hotelId, userId);
        	 return new Result("saving workflow approver settings successfull!");
         }catch(Throwable t) {
        	 logger.error("save workflow approver settings happing error!", t);
 			 return Result.errorResult(t.getMessage());
         }
	}
	
	@RequestMapping("/queryWorkTeamOrEmployee")
	public @ResponseBody PageResult<GridItemData> queryWorkTeamOrEmployee(@RequestParam(required=false) String name, String type, int pageIndex,
			int limit, int start) {		
		name = StringUtils.hasText(name)?name:null;		
		String orgId = LoginInfoHolder.getLoginHotelId();		
		Assert.hasText(orgId,"not get loginInfo data!");
		PageResult<GridItemData> ret = new PageResult<GridItemData>(limit, pageIndex);
		if (CUSTOMER_TYPE.equals(type)) {
			try {
				PageResult<Staff>  PageStaff= employeeService.pageList(pageIndex, limit, Constants.NODE_TYPE_HOTEL, orgId, null, name, null, null);
				ret.setIndex(PageStaff.getIndex());
				ret.setResult(PageStaff.isResult());
				ret.setResults(PageStaff.getResults());
				ret.setSize(PageStaff.getSize());
				List<GridItemData> gtdList = new ArrayList<GridItemData>();
				int seq = 1;
				GridItemData temp;
				for(Staff staff : PageStaff.getRows()) {
					temp = new GridItemData();
					temp.setSeq(seq++);
					temp.setId(staff.getId());
					temp.setName(staff.getName());
					temp.setOrgPath(staff.getOrgInfo());
					gtdList.add(temp);
				}
				ret.setRows(gtdList);
			}catch(Throwable t) {
				ret.setResult(false);
				logger.error("查询人员出错！", t);
			}
		}
		else if(WORKTEAM_TYPE.equals(type)){
			WorkTeam wt = new WorkTeam();
			wt.setName(name);
			wt.setHotelId(orgId);
			PageResult<WorkTeam> wtPageResult = workTeamService.list(pageIndex, limit, wt);
			List<GridItemData> gtdList = new ArrayList<GridItemData>();			
			ret.setIndex(wtPageResult.getIndex());
			ret.setResult(wtPageResult.isResult());
			ret.setResults(wtPageResult.getResults());
			ret.setSize(wtPageResult.getSize());
			int seq = 1;
			GridItemData temp;
			for(WorkTeam wt1 : wtPageResult.getRows()) {
				temp = new GridItemData();
				temp.setSeq(seq++);
				temp.setId(wt1.getId());
				temp.setName(wt1.getName());
				temp.setOrgPath("");
				gtdList.add(temp);
			}
			ret.setRows(gtdList);
		}
		return ret;
	}
	
	public static class GridItemData{
		
		private int seq;
		
		private String id;
		
		private String name;
		
		private String orgPath;

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOrgPath() {
			return orgPath;
		}

		public void setOrgPath(String orgPath) {
			this.orgPath = orgPath;
		}
				
	}	 
}
