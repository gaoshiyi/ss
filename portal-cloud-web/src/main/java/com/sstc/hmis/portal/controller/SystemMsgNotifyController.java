package com.sstc.hmis.portal.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.MdataBeanModel;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.notification.data.Notice;
import com.sstc.hmis.notification.data.NoticeBean;
import com.sstc.hmis.notification.data.NoticeReceipt;
import com.sstc.hmis.notification.service.SystemMsgNotifyService;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.service.StaffService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;
@Controller
@RequestMapping("/sysMsgnotify")
public class SystemMsgNotifyController extends BaseController{
	
	@Autowired
	private SystemMsgNotifyService systemMsgNotifyService;
	@Autowired
	private StaffService staffService;
	
	@RequestMapping("/index")
	public String mainPage(Model model) { // 首页
		
		return "manage/notification/index";
	}
	
	@RequestMapping("/add")
	public String addPage(Model model, String id) { // 新建
		
		Notice notice = new Notice();
		notice.setPublishStatus((short)1);
		if (StringUtils.isNoneBlank(id))
		{
			notice = systemMsgNotifyService.initEditData(id);
			model.addAttribute("notice", notice);
		}
		List<MdataBeanModel> msgNoticeLst = systemMsgNotifyService.getParaMap(GenCodeConstants.PORTAL, GenCodeConstants.MSGNOTICE); // 消息通知
		model.addAttribute("msgNoticeLst", msgNoticeLst);
		return "manage/notification/add";
	}
	
	@RequestMapping("/isPrompt")
	@ResponseBody
	public Result isPrompt(Model model) { // 是否弹出提示框
		Notice notice = systemMsgNotifyService.selectNoticeByCurrTime(new Date());
		if (null != notice 
				&& StringUtils.isNotBlank(notice.getId()))
		{
			return new Result(notice);
		}
		return new Result(203, "no data");
	}
	
	@RequestMapping("/view")
	public String viewPage(Model model, String id) { // 详情
		
		Notice notice = systemMsgNotifyService.view(id);
		model.addAttribute("notice", notice);
		return "manage/notification/view";
	}

	@RequestMapping("/handleList")
	public String handleListPage(Model model, String id) { // 确认回执页面
		
		String hotelId = LoginInfoHolder.getLoginHotelId();
		model.addAttribute("hotelId", hotelId);
		return "manage/notification/handle-list";
	}
	
	@RequestMapping("/handle")
	public String handlePage(Model model, String noticeId) { // 确认回执页面
		
		String hotelId = LoginInfoHolder.getLoginHotelId();
		model.addAttribute("hotelId", hotelId);
		model.addAttribute("noticeId", noticeId);
		return "manage/notification/handle";
	}
	
	@RequestMapping("/pagefind")
    @ResponseBody
    public PageResult<Notice> pageFind(@RequestParam(name = "limit", defaultValue = "10") int pageSize,
        @RequestParam(name = "start", defaultValue = "0") int start,
        @RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex, NoticeBean noticeBean)
        throws ParseException
    {
        PageResult<Notice> pageResult = systemMsgNotifyService.selectPageNoticeList(pageSize, pageIndex, noticeBean);
        return pageResult;
    }
	
	@RequestMapping("/pageFindReceipt")
    @ResponseBody
	public PageResult<NoticeReceipt> pageFindReceipt(@RequestParam(name = "limit", defaultValue = "10") int pageSize,
	        @RequestParam(name = "start", defaultValue = "0") int start,
	        @RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex, NoticeBean noticeBean)
	 throws ParseException
	{
		PageResult<NoticeReceipt> pageResult = systemMsgNotifyService.selectPageNoticeReceiptList(pageSize, pageIndex, noticeBean);
		return pageResult;
	}
	@RequestMapping("/toConfirmReceipt")
	public String toConfirmReceipt()
	{ // 确认回执页面
		return "manage/notification/index";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(NoticeBean notice)
	{ // 保存
		Result result = systemMsgNotifyService.saveNoticeInfo(notice);
		return result;
	}
	
	@RequestMapping("/saveReceipt")
	@ResponseBody
	public Result saveReceipt(NoticeReceipt noticeReceipt)
	{ // 保存确认回执
		Result result = systemMsgNotifyService.saveReceipt(noticeReceipt);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String idStr)
	{
		int count = systemMsgNotifyService.delete(idStr);
		if (count > 0)
		{
			return Result.SUCCESS;
		}
		return Result.ERROR_PARAMS;
	}
	
	@RequestMapping("/initEditData")
	@ResponseBody
	public Result initEditData(String id)
	{
		Notice notice = systemMsgNotifyService.initEditData(id);
		return new Result(notice);
	}
	
	/**
     * 组织机构查询员工<一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/listStaff")
    @ResponseBody
    public Result listStaff(String deptId)
    {
    	try {
    		List<Staff> staffLst = staffService.getStaffInfoByDeptId(deptId, LoginInfoHolder.getLoginHotelId());
			return new Result(staffLst);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    /**部门下拉框-调用 EmployeeController.treeInfo 方法*/
}
