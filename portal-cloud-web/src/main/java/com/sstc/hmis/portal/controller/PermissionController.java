/**
 * 
 */
package com.sstc.hmis.portal.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.service.PermissionService;
import com.sstc.hmis.portal.common.base.BaseController;

/**
  * <p> Title: StaffController </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年4月12日 上午10:36:29
   */
@Controller
@RequestMapping("/perm")
public class PermissionController extends BaseController{
	
	@Autowired
	private PermissionService permissionService;

	@RequestMapping("/page")
	public String page(){
		
		Permission perm = new Permission();
		perm.setType(Constants.PERMS_PRODUCTION);
		try {
			List<Permission> productList = permissionService.list(perm);
			request.setAttribute("productList", productList);
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		return "manage/permission/perm_list";
	}
	
	@RequestMapping("/edit")
	public String edit(String id){
		Permission queryPerm = new Permission();
		queryPerm.setType(Constants.PERMS_PRODUCTION);
		try {
			List<Permission> productList = permissionService.list(queryPerm);
			request.setAttribute("productList", productList);
			if(StringUtils.isNoneBlank(id)){
				Permission query = new Permission();
				query.setId(id);
				Permission permission = permissionService.find(query);
				String[] urls = permission.getSubUrl();
				int length = 0;
				String subUrlText = "";
				if(urls != null && urls.length > 0){
					length = urls.length;
					for (int i = 0; i < length; i++) {
						if(StringUtils.isNoneBlank(urls[i])){
							subUrlText += urls[i];
							if(i != length -1 ){
								subUrlText += ",";
							}
						}
					}
				}
				request.setAttribute("subUrlText", subUrlText);
				request.setAttribute("perm", permission);
				
				String pid = permission.getPid();
				if(StringUtils.isNoneBlank(pid)){
					Short type = permission.getType();
					if(type == Constants.PERMS_SYSTEM){
						request.setAttribute("productId", pid);
					} 
					if(type == Constants.PERMS_FUNCTION){
						request.setAttribute("moduleId", pid);
						query = new Permission();
						query.setId(pid);
						Permission parent = permissionService.findById(pid);
						query.setId(null);
						query.setPid(parent.getPid());
						List<Permission> moduleList = permissionService.list(query);
						if(moduleList.size() > 0 ){
							request.setAttribute("moduleList", moduleList);
							pid = moduleList.get(0).getPid();
						}
					}
					if(type >= Constants.PERMS_MODULE){
						request.setAttribute("systemId", pid);
						query = new Permission();
						query.setId(pid);
						Permission parent = permissionService.findById(pid);
						query.setId(null);
						query.setPid(parent.getPid());
						List<Permission> systemList = permissionService.list(query);
						if(systemList.size() > 0 ){
							request.setAttribute("systemList", systemList);
							request.setAttribute("productId", systemList.get(0).getPid());
						}
					}
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
		return "manage/permission/edit";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/tree")
	public List<TreeNode> tree(){
		return permissionService.permTree();
	}
	
	@ResponseBody
	@RequestMapping("/subPerms")
	public Result subPerms(Permission permission){
		return permissionService.resultList(permission);
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public PageResult<Permission> list(Permission permission){
		return permissionService.pageList(index,size,permission);
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public Result saveOrUpdate(Permission permission){
		Short isMenu = permission.getIsMenu();
		if(isMenu == null){
			permission.setIsMenu(Constants.IS_MENU_NO);
		}
		return permissionService.saveOrUpdate(permission);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		String[] ids = id.split(",");
		return permissionService.batchDelete(ids);
	}
	
	
	
}
