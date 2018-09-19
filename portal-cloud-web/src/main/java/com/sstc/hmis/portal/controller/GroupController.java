/**
 * Copyright 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sstc.hmis.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.portal.common.base.BaseController;

/**
 * 酒店管理Controller
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version Date: 2017年4月7日 上午10:43:11
 * @serial 1.0
 * @since 2017年4月7日 上午10:43:11
 */

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

	@Autowired
	private GrpHotelService grpHotelService;

	/**
	 * 进入首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "manage/group/org";
	}
	
	/**
	 * 进入集团信息页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/group")
	public String group(Model model,String orgId,String name,String nodeType) {
		model.addAttribute("name", name);
		model.addAttribute("orgId", orgId);
		model.addAttribute("nodeType", nodeType);
		GroupHotel hotel = new GroupHotel();
		hotel.setId(orgId);
		try {
			hotel = grpHotelService.find(hotel);
		} catch (AppException e) {
			e.printStackTrace();
		}
		model.addAttribute("hotel", hotel);
		return "manage/group/group";
	}
	
	/**
	 * 进入酒店列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/hotel")
	public String hotel(Model model,String parentId) {
		model.addAttribute("parentId", parentId);
		return "manage/group/hotel";
	}
	
	
	/**
	 * 查询集团组织结构
	 * 
	 * @return
	 */
	@RequestMapping("/findGroupList")
	@ResponseBody
	public List<TreeNode> findGroupList(@RequestParam(value="orgLevel",required=false)Short orgLevel) {
		List<TreeNode> list = grpHotelService.list(orgLevel);
		return list;
	}

	/**
	 * 查询集团酒店列表
	 * 
	 * @return
	 */
	@RequestMapping("/findHotelList")
	@ResponseBody
	public PageResult<GroupHotel> findHotelList(String groupId,GroupHotel hotel) {
		
		hotel.setPid(LoginInfoHolder.getLoginInfo().getGrpId());
		PageResult<GroupHotel> page = new PageResult<>();
		try {
			page = grpHotelService.list(index, size, hotel);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 批量更新酒店状态
	 * 
	 * @return
	 */
	@RequestMapping("/batchUpdateStatus")
	@ResponseBody
	public int batchUpdateStatus(String ids,int status) {
		List<String> list=new ArrayList<>();
		String[] arr=StringUtils.split(ids, ",");
		for(String id:arr){
			list.add(id);
		}
		
		int count = 0;
		try {
			count = grpHotelService.updateStatusByPrimaryKeys(list, status);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return count;
	}
}
