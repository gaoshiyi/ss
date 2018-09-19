/**
  * 文件名：CommonController.java
  * 日期：2017年3月8日
  * Copyright sstc Corporation 2017 
  * 版权所有
  */
package com.sstc.hmis.portal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.permission.service.PortalCommonService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;
import com.sstc.hmis.util.bean.UploadFileResp;
import com.sstc.hmis.util.fileupload.SaveUploadFileHelper;
import com.sstc.hmis.util.msg.MessageSourceHelper;

/**
 * @ClassName CommonController
 * @Description 公共spring mvc控制器 
 * @author yaodm
 * @date 2017年3月8日 下午7:35:53 
*/
@RequestMapping("/common")
@Controller
public class CommonController extends BaseController{
	
	@Resource
    private SaveUploadFileHelper saveUploadFileHelper;
	
	@Resource
	private PortalCommonService commonService;
	
	 @Resource
	    private MessageSourceHelper messageSourceHelper;
	/**
	 * 方法描述: 获取不同级别下的区域数据
	 * @author yaodm
	 * @date 2017年3月21日 上午10:18:19
	 * @param level
	 * @param areaId
	 * @return Result
	 */
	@RequestMapping(value = "/getAreaData", method = RequestMethod.GET)
	@ResponseBody
	public Result getAreaData(@RequestParam(defaultValue = "0")int type, String areaId, String parentId) {
		try {
			String grpId = LoginInfoHolder.getLoginGrpId();
			return new Result(commonService.getAreaSettingData(type, areaId, parentId,grpId,grpId));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.errorResult("获取地域数据失败!");
		}
	}
	
	@RequestMapping("/download")
	public String download() {
		
		return "common/download";
	}
	
	/**
	 * 功能：图片上传
	 * @param Filedata
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Result upload(MultipartFile Filedata, HttpServletRequest request) {
		try {
			UploadFileResp resp = saveUploadFileHelper.saveFile(Filedata, request);
			return new Result(resp);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.errorResult(messageSourceHelper.getMessage("error.uploadFile"));
		}
	}
    
}
