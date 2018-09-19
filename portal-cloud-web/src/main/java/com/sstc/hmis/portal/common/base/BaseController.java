/**
  * 文件名：BaseController.java
  * 日期：2017年4月11日
  * Copyright sstc Corporation 2017 
  * 版权所有
  */
package com.sstc.hmis.portal.common.base;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.AjaxResult;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.ExcelUtils;

/**
 * @ClassName BaseController
 * @Description 基础spring mvc控制器 
 * @author yaodm
 * @date 2017年4月11日 下午1:06:56 
*/
public class BaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	protected int size;

	protected int index;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected HttpSession session;
	
	protected PageResult<Object> pageInfo;
	
	protected AjaxResult ajaxResult;
	
	protected Result result;
	
	@ModelAttribute
	protected void setRequestAndResponse(HttpServletRequest request,
			HttpServletResponse response) {
		this.ajaxResult = new AjaxResult(com.sstc.hmis.model.constants.Constants.RES_CODE_SUCCESS, null);
		this.result = Result.SUCCESS;
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.size = this.getParameterInteger("limit",10);
		this.index = this.getParameterInteger("pageIndex", 0);
		pageInfo = new PageResult<>(size, index);
		LoginInfoHolder.setLoginInfo(getLoginStaff());
		try {
			LoginInfoHolder.setSessionId(SecurityUtils.getSubject().getSession().getId().toString());
		} catch (Exception e) {
			LOG.error("获取session信息错误:{}", e);
		}
	}
	public Staff getLoginStaff(){
		Session session = SecurityUtils.getSubject().getSession();
		return (Staff) session.getAttribute(PortalConstants.SESSION_USER_KEY);
	}
	/**
	 * 数据Excel导出
	 * @param columns 导出字段
	 * @param headers excel字段头
	 * @param list 数据列表
	 * @param fileName 导出文件名
	 * @return
	 */
	public boolean exportExcel(String[] columns, String[] headers, 
			List<? extends Object> list,String fileName) {
		try {
			OutputStream out= new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/msexcel;charset=ISO8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( fileName + ".xlsx", "utf-8"));
			ExcelUtils.exportExcel(out, fileName, LoginInfoHolder.getLoginAccount(), columns, headers, list);
			out.flush();
			out.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		request.getAttribute("size", WebRequest.SCOPE_REQUEST);
//		model.addAttribute("commonResources", PropertyUtil.COMMON_RESOURCES_URL);
	}
	
	public Integer getParameterInteger(String name,int defaultVal){
		String str = request.getParameter(name);
		if(StringUtils.isBlank(str)){
			return defaultVal;
		} 
		try{
			return Integer.valueOf(str);
		}catch(Exception e){
			LOG.error("获取请求参数转Integer类型错误:{}",e);
			return defaultVal;
		}
	}
	
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	
	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }  
}
