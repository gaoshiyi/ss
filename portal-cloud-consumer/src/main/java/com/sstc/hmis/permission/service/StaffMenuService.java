/**
 * 
 */
package com.sstc.hmis.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.permission.data.Permission;

/**
  * <p> Title: StaffMenuService </p>
  * <p> Description:  员工订制菜单管理 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月23日 下午1:56:30
   */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/staffMenuService")
public interface StaffMenuService {
	
	/**
	 * 查询用户的菜单信息
	 * @return
	 */
	@RequestMapping(value = "/listUserMenu", method = RequestMethod.GET)
	public Result listUserMenu();

	/**
	 * 加入我的菜单
	 * @param menuId 菜单ID
	 * @param type 类型：0-普通菜单，1-首页
	 * @return
	 */
	@RequestMapping(value = "/addMenu" , method = RequestMethod.POST)
	Result addMenu(@RequestParam("menuId")String menuId, @RequestParam("type")Short type,@RequestParam("collected")Short collected);

	/**
	 * 删除我的菜单
	 * @param menuId 菜单ID
	 * @return
	 */
	@RequestMapping(value = "/delMenu" , method = RequestMethod.POST)
	Result delMenu(@RequestParam("menuId")String menuId);
	
	/**
	 * 员工设置的首页
	 * @return
	 */
	@RequestMapping(value = "/homePage" , method = RequestMethod.GET)
	Permission homePage();

	/**
	 * 我的菜单和报表
	 * @return
	 */
	@RequestMapping(value = "/myMenus" , method = RequestMethod.GET)
	Result myMenus();

}
