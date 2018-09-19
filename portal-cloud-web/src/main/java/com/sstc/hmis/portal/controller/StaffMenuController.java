/**
 * 
 */
package com.sstc.hmis.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.permission.service.StaffMenuService;
import com.sstc.hmis.portal.common.base.BaseController;

/**
  * <p> Title: StaffMenuController </p>
  * <p> Description:  员工菜单管理 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月23日 下午2:00:23
   */
@Controller
@RequestMapping("/staffMenu")
public class StaffMenuController extends BaseController{

	
	@Autowired
	private StaffMenuService staffMenuService;
	
	/**
	 * 加入我的菜单
	 * @param menuId 菜单ID
	 * @param type 类型：0-普通菜单，1-首页
	 * @param collected 0-添加 1-删除
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Result addMenu(@RequestParam("id")String menuId,Short type,Short collected){
		return staffMenuService.addMenu(menuId,type,collected);
	}
	/**
	 * 删除我的菜单
	 * @param menuId 菜单ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Result delMenu(String menuId){
		return staffMenuService.delMenu(menuId);
	}
	
	/**
	 * 我的菜单和报表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/my")
	public Result myMenus(){
		return staffMenuService.myMenus();
	}
}
