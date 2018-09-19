/**
 * 
 */
package com.sstc.hmis.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.permission.data.Department;
import com.sstc.hmis.permission.data.PostResponsibility;

/**
 * Title: PostResponsibilityService
 * Description: 岗位职责
 * @Company: SSTC
 * @author CKang
 * @date 2017年4月11日 下午2:22:50
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/postService")
public interface PostResponsibilityService {

	/**
	 * 通过部门id查询所有的岗位
	 * @author CKang
	 * @date 2017年4月11日 下午2:23:09
	 * @param postResponsibility
	 * @return
	 */
	@RequestMapping(value = "/findPostResponsibilityByDeptId" , method = RequestMethod.POST)
	public PageResult<PostResponsibility> findPostResponsibilityByDeptId(@RequestBody PostResponsibility postResponsibility,@RequestParam("pageIndex")int pageIndex, @RequestParam("pageSize")int pageSize);

	/**
	 * 通过部门id查询所有的岗位(员工管理三级联动使用)
	 * @author CKang
	 * @date 2017年5月23日 上午11:26:48
	 * @param postResponsibility
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/findPostResponsibilityByDeptIdCopy" , method = RequestMethod.POST)
	public PageResult<PostResponsibility> findPostResponsibilityByDeptIdCopy(@RequestBody PostResponsibility postResponsibility,@RequestParam("pageIndex")int pageIndex, @RequestParam("pageSize")int pageSize);
	
	
	/**
	 * 添加职位信息
	 * @author CKang
	 * @date 2017年4月18日 下午6:49:51
	 * @param postResponsibility
	 * @param deptId
	 */
	@RequestMapping(value = "/savePostResponsibility" , method = RequestMethod.POST)
	public void savePostResponsibility(@RequestBody PostResponsibility postResponsibility,@RequestParam("deptId")String deptId);
	
	/**
	 * 通过职位id查询职位信息
	 * @author CKang
	 * @date 2017年4月19日 上午11:02:47
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/findPostResponsibilityById" , method = RequestMethod.POST)
	public PostResponsibility findPostResponsibilityById(@RequestParam("postId")String postId);
	
	
	/**
	 * 修改职位信息
	 * @author CKang
	 * @date 2017年4月19日 下午5:26:00
	 * @param postResponsibility
	 * @param deptId
	 */
	@RequestMapping(value = "/updatePostResponsibility" , method = RequestMethod.POST)
	public void updatePostResponsibility(@RequestBody PostResponsibility postResponsibility,@RequestParam("deptId")String deptId);
	
	/**
	 * 验证职位名称是否存在
	 * @author CKang
	 * @date 2017年4月19日 下午5:27:49
	 * @param postNameId
	 * @return
	 */
	@RequestMapping(value = "/validPostName" , method = RequestMethod.POST)
	public String validPostName(@RequestParam("postNameId")String postNameId,@RequestParam("hotelId")String hotelId,@RequestParam("deptId")String deptId);
	
	/**
	 * 验证职位名称是否存在,除去当前职位以外 
	 * @author CKang
	 * @date 2017年5月3日 上午9:47:29
	 * @param id
	 * @param postNameId
	 * @param hotelId
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/validPostNameNotId" , method = RequestMethod.POST)
	public String validPostNameNotId(@RequestParam("id")String id,@RequestParam("postNameId")String postNameId,@RequestParam("hotelId")String hotelId,@RequestParam("deptId")String deptId);
	
	/**
	 * 通过id删除职位信息
	 * @author CKang
	 * @date 2017年4月20日 上午10:21:08
	 * @param id
	 */
	@RequestMapping(value = "/deletePostById" , method = RequestMethod.POST)
	public void deletePostById(@RequestParam("id")String id);
	
	/**
	 * 批量删除职位信息
	 * @author CKang
	 * @date 2017年4月20日 上午10:42:03
	 * @param ids
	 */
	@RequestMapping(value = "/batchDeletePost" , method = RequestMethod.POST)
	public void batchDeletePost(@RequestParam("ids")String ids);
	
	/**
	 * 查询部门信息通过职位id
	 * @author CKang
	 * @date 2017年4月27日 下午1:57:39
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/findDeptByPostId" , method = RequestMethod.POST)
	public List<Department> findDeptByPostId(@RequestParam("postId")String postId);
	
	
	/**
	 * 通过角色id查询有效的员工的数量
	 * @author CKang
	 * @date 2017年4月26日 下午4:50:44
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findStaffCountByPostId" , method = RequestMethod.POST)
	public int findStaffCountByPostId(@RequestParam("postId")String postId);
	
	/**
	 * 通过角色id查询职位数量
	 * @author CKang
	 * @date 2017年5月9日 上午8:58:54
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/findPostCountByRoleId" , method = RequestMethod.POST)
	public int findPostCountByRoleId(@RequestBody PostResponsibility postResponsibility);
	
	/**
	 * 通过部门id查询职位信息
	 * @author CKang
	 * @date 2017年5月24日 下午2:38:48
	 * @param dept
	 * @return
	 */
	@RequestMapping(value = "/findPostByDeptId" , method = RequestMethod.POST)
	public List<PostResponsibility> findPostByDeptId(@RequestParam("deptId")String deptId);
	
	/**
	 * 通过用户id查询职位信息
	 * @author CKang
	 * @date 2017年5月26日 上午11:16:48
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/findPostByStaffId" , method = RequestMethod.POST)
	public List<PostResponsibility> findPostByStaffId(@RequestParam("staffId")String staffId);
	
	/**
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称
	 * @author denghaibo
	 * @date 2017年4月19日 下午5:27:49
	 * @param postNameId
	 * @return
	 */
	@RequestMapping(value = "/validDeptPostName" , method = RequestMethod.POST)
	public String validDeptPostName(@RequestParam("postNameId")String postNameId,@RequestParam("name")String name,@RequestParam("deptId")String deptId);
	
	/**
	 * 验证同一酒店下 同一个部门及同一个职级下，是否存在相同的名称  去除自己 修改使用 
	 * @author denghaibo
	 * @date 2017年5月3日 上午9:47:29
	 * @param id
	 * @param postNameId
	 * @param hotelId
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/validDeptPostNameNotId" , method = RequestMethod.POST)
	public String validDeptPostNameNotId(@RequestParam("id")String id,@RequestParam("postNameId")String postNameId,@RequestParam("name")String name,@RequestParam("deptId")String deptId);
	
	
}
