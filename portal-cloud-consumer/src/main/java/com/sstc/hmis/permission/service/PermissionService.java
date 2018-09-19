/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.Permission;
/**
 * Title: PermissionService
 * Description: 权限
 * @Company: SSTC
 * @author CKang
 * @date 2017年4月11日 下午2:21:50
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/permissionService")
public interface PermissionService  {

	/**
	 * 根据用户id加载权限列表
	 * @author CKang
	 * @date 2017年4月11日 下午2:21:36
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/loadPermissionTree" , method = RequestMethod.POST)
	public List<TreeNode> loadPermissionTree(@RequestParam(name = "roleId",required = false) String roleId);
	
	/**
	 * 加载已选中的权限信息
	 * @author CKang
	 * @date 2017年4月17日 上午11:23:02
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/loadcheckedPermissionList" , method = RequestMethod.POST)
	public List<Permission> loadcheckedPermissionList(@RequestParam("roleId")String roleId);

	/**
	 * @return
	 */
	@RequestMapping(value = "/permTree" , method = RequestMethod.POST)
	public List<TreeNode> permTree();

	/**
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/resultList" , method = RequestMethod.POST)
	public Result resultList(@RequestBody Permission permission);

	/**
	 * @param index
	 * @param size
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/pageList" , method = RequestMethod.POST)
	public PageResult<Permission> pageList(@RequestParam("index")int index, @RequestParam("size")int size, @RequestBody Permission permission);

	/**
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdate" , method = RequestMethod.POST)
	public Result saveOrUpdate(@RequestBody Permission permission);

	/**
	 * @return
	 */
	@RequestMapping(value = "/findById" , method = RequestMethod.POST)
	public Permission findById(@RequestParam("id")String id);

	/**
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/batchDelete" , method = RequestMethod.POST)
	public Result batchDelete(@RequestParam("ids")String[] ids);

	/**
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/listUserSysModule" , method = RequestMethod.POST)
	public List<Permission> listUserSysModule(@RequestBody Permission query);

	/**
	 * @param perm
	 * @return
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	public List<Permission> list(@RequestBody Permission perm) throws AppException;

	/**
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/find" , method = RequestMethod.POST)
	public Permission find(@RequestBody Permission query) throws AppException;
	
	@RequestMapping(value = "/findUserPerms" , method = RequestMethod.POST)
	public List<Permission> findUserPerms(@RequestBody Set<String> roleIdSet);
	
	@RequestMapping(value = "/findAllFuncPerms" , method = RequestMethod.GET)
	public Set<String> findAllFuncPerms();
	
	/**
	 * 根据菜单ID获取菜单的全路径访问地址
	 * @param permId 菜单ID，确保菜单配置了url
	 * @return
	 */
	@RequestMapping(value = "/getPermissionUrlById", method = RequestMethod.GET)
	public Result getPermissionUrlById(String permId);
	
 
}
