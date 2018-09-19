package com.sstc.hmis.portal.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.ApproveDetailEntity;
import com.sstc.hmis.permission.data.ApproveEntity;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.WorkTeam;
import com.sstc.hmis.permission.service.EmployeeService;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.PortalCommonService;
import com.sstc.hmis.permission.service.PostResponsibilityService;
import com.sstc.hmis.permission.service.WorkTeamService;
import com.sstc.hmis.permission.service.WorkflowApproverService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;
import com.sstc.hmis.util.CommonUtils;


@Controller
@RequestMapping("/workflowapprover")
public class WorkFlowApproverController extends BaseController{
	
	
    private static final Logger log = LoggerFactory.getLogger(WorkFlowApproverController.class);
	@Autowired
	private WorkflowApproverService workflowApproverService;
	
	@Autowired
	WorkTeamService workTeamService;
	
	@Autowired
	GrpHotelService grpHotelService;
	
	@Autowired
	private PortalCommonService commonService;
	
	@Resource(name = "redisTemplate")
    RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PostResponsibilityService postResponsibilityService;
	/**
	 * 主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String mainPage(Model model) {
		return "manage/approve/index";
	}
	
	/**
	 * 主页面获取数据
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult<ApproveEntity> list(@RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
	        @RequestParam(name = "limit", defaultValue = "10") int pageSize,
	        @RequestParam(name = "start", defaultValue = "0") int startRecord ){
		String hotelId = LoginInfoHolder.getLoginHotelId();
		PageResult<ApproveEntity> list = workflowApproverService.getApproveListPage(pageIndex,pageSize,hotelId);
		return list;
	}
	/**
	 * 查看界面
	 */
	@RequestMapping("/view")
	public String viewDialog(Model model,String wfPrfId,String type,String documentNo){
		try {
			// 获取分类的码表值；
			Map<String, String> typeSel = commonService
					.getSelectEnumByHotelId(GenCodeConstants.PORTAL, GenCodeConstants.WORK_TEAM_TYPE,
							LoginInfoHolder.getLoginGrpId(),LoginInfoHolder.getLoginHotelId());
			request.setAttribute("typeSel", typeSel);
			// 所属集团或者酒店
			List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
			request.setAttribute("groupHotelList", groupHotelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("wfPrfId", wfPrfId);
		model.addAttribute("type", type);
		model.addAttribute("documentNo", documentNo);
		return "manage/approve/view";
	}
	
	/**
	 * 查看详情页面获取数据
	 * @param wfPrfId
	 * @param type
	 * @return
	 */
    @RequestMapping("/detailList")
    public @ResponseBody PageResult<ApproveDetailEntity> detailList(
        @RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
        @RequestParam(name = "limit", defaultValue = "10") int pageSize,
        @RequestParam(name = "start", defaultValue = "0") int startRecord, String wfPrfId,String type,String documentNo)
    {
    	String hotelId = LoginInfoHolder.getLoginHotelId();
    	log.info("YYYYYYYYYYYYYYYYYYYYY-----------------------------DocumentNo为", documentNo);
        PageResult<ApproveDetailEntity> pageResult = workflowApproverService.getDetailByWfPrfAndTypePage(pageIndex, pageSize,wfPrfId, type,hotelId,documentNo);
        return pageResult;
        
    }
	/**
	 * 新增界面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String addDialog(Model model){
		
		return "manage/approve/add";
	}
	
	/**
	 * 打开选择组织界面
	 * @return
	 */
	@RequestMapping("/addOrg")
	public String addOrg(Model model){
		model.addAttribute("loginHotelVal", LoginInfoHolder.getLoginHotelId());
		return "manage/approve/add_org";
	}
	/**
	 * 打开选择人员界面
	 * @return
	 */
	@RequestMapping("/addPer")
	public String addPer(Model model){
		
		model.addAttribute("loginHotelVal", LoginInfoHolder.getLoginHotelId());
		return "manage/approve/add_per";
	}
	/**
	 * 新审批人管理添加员工页面查询
	 * @author yuankairui
	 * @date 2018/5/8
	 * @param orgId
	 * @param nodeType
	 * @param status
	 * @param info
	 * @param teamId
	 * @return
	 */
	@RequestMapping("/listforaddper")
	@ResponseBody
	public List<Staff> pageListForAddPer(String orgId, Short nodeType,  String staffName) {
		try {
			List<Staff> result = employeeService.pageListForAddPer( nodeType, orgId,staffName);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 打开工作圈界面
	 * @return
	 */
	@RequestMapping("/addTeam")
	public String addTeam(){
		try {
			// 获取分类的码表值；
			Map<String, String> typeSel = commonService
					.getSelectEnumByHotelId(GenCodeConstants.PORTAL, GenCodeConstants.WORK_TEAM_TYPE,
							LoginInfoHolder.getLoginGrpId(),LoginInfoHolder.getLoginHotelId());
			request.setAttribute("typeSel", typeSel);
			// 所属集团或者酒店
			List<GroupHotel> groupHotelList = grpHotelService.list(new GroupHotel());
			request.setAttribute("groupHotelList", groupHotelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manage/approve/add_team";
	}
	@RequestMapping("/teamList")
	@ResponseBody
	public PageResult<WorkTeam> queryTeamList(@RequestParam(defaultValue = "0", name = "pageIndex") int index,
			@RequestParam(defaultValue = "10", name = "limit") int size,String name) {
		return workTeamService.queryTeamList(index,size,name,LoginInfoHolder.getLoginHotelId());
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(String wfPrcfId,String wfTaskKey,String type,String auditIds,String dptId){
		String grpId = LoginInfoHolder.getLoginGrpId();
		String hotelId = LoginInfoHolder.getLoginHotelId();
		String userId = LoginInfoHolder.getLoginInfo().getId();
		List<String> auditIdList = Arrays.asList(auditIds.split(","));
		String documentId = getDocumentNo(wfPrcfId);
		workflowApproverService.saveWfAuditDetailNew(wfPrcfId, wfTaskKey, type, auditIdList, grpId, hotelId, userId, dptId,documentId );
		return new Result(200);
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String wfPrfId,String type){
		workflowApproverService.deleteApprove(wfPrfId, type);
		return new Result(200);
	}
	
	/**
	 * 获取类型编码
	 */
	private String getDocumentNo(String wfPrcfId) {
		String documentNo = "SP";
		if ("register".equals(wfPrcfId)) {
			documentNo += Constants.WF_DEF_TEMPLET_REGISTER;
		} else if ("wardProcess".equals(wfPrcfId)) {
			documentNo += Constants.WF_DEF_TEMPLET_WARD;
		}
		// 洗衣工程。。。。
		String nowNo = (String)redisTemplate.opsForValue().get(documentNo);
		String nowDocumentNo = CommonUtils.getDocumentNo(nowNo, 3);
		redisTemplate.opsForValue().set(documentNo, nowDocumentNo);
		
		return documentNo + nowDocumentNo;
	}
	
}
