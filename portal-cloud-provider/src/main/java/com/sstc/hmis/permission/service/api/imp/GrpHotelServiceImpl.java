package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.ContactsInfo;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.GroupInfo;
import com.sstc.hmis.permission.data.LinkHotelInfo;
import com.sstc.hmis.permission.data.LinkmanDeptInfo;
import com.sstc.hmis.permission.data.LinkmanInfo;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.data.WorkGroupInfo;
import com.sstc.hmis.permission.dbaccess.dao.PermsTDepartmentMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTGroupHotelMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPostResponsibilityMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTDepartment;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTGroupHotelExample.Criteria;
import com.sstc.hmis.permission.dbaccess.data.PermsTPostResponsibility;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.domain.Dept;
import com.sstc.hmis.permission.dbaccess.data.domain.Hotel;
import com.sstc.hmis.permission.dbaccess.data.domain.Post;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
 * 
 * 酒店管理Service
 * <p/>
 * 
 * @author <a href="mailto:cuihu@sstcsoft.com">cuihu</a>
 * @version Date: 2017年4月12日 下午2:29:52
 * @serial 1.0
 * @since 2017年4月12日 下午2:29:52
 */
@RestController
public class GrpHotelServiceImpl extends BaseServiceImpl<GroupHotel> implements GrpHotelService {

	@Autowired
	private PermsTGroupHotelMapper permsTGroupHotelMapper;
	@Autowired
	PermsTDepartmentMapper permsTDepartmentMapper;
	@Autowired
	PermsTPostResponsibilityMapper permsTPostResponsibilityMapper;

	@Override
	public List<GroupHotel> list(@RequestBody GroupHotel hotel) {
		PermsTGroupHotelExample example = new PermsTGroupHotelExample();
		Criteria createCriteria = example.createCriteria();
		if (StringUtils.isNotBlank(hotel.getId())) {
			createCriteria.andClIdEqualTo(hotel.getId());
		}
		Staff staff = LoginInfoHolder.getLoginInfo();
		if(staff != null && StringUtils.isNoneBlank(staff.getHotelId()) 
				&& !staff.getHotelId().equals(staff.getGrpId())){
			createCriteria.andClIdEqualTo(staff.getHotelId());
		}
		createCriteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
		List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.selectByExample(example);
		List<GroupHotel> list = new ArrayList<GroupHotel>();
		for (PermsTGroupHotel permsTGroupHotel : permsTGroupHotelList) {
			GroupHotel groupHotel = convertPermsTGroupHotel2GroupHotel(permsTGroupHotel);
			list.add(groupHotel);
		}
		return list;
	}

	@Override
	public PageResult<GroupHotel> list(int index, int size, @RequestBody GroupHotel groupHotel) throws AppException {
		PageResult<GroupHotel> pageResult = new PageResult<>();
		PageHelper.startPage(index + 1, size);
		PermsTGroupHotel param = convertGroupHotel2PermsTGroupHotel(groupHotel);
		Page<PermsTGroupHotel> page = (Page<PermsTGroupHotel>) permsTGroupHotelMapper.selectBySelective(param);
		long total = page.getTotal();
		List<PermsTGroupHotel> tGroupHotelList = page.getResult();
		List<GroupHotel> groupHotellist = new ArrayList<>();
		for (PermsTGroupHotel permsTGroupHotel : tGroupHotelList) {
			groupHotel = convertPermsTGroupHotel2GroupHotel(permsTGroupHotel);
			groupHotellist.add(groupHotel);
		}
		pageResult.setResult(true);
		pageResult.setResults(total);
		pageResult.setRows(groupHotellist);
		return pageResult;
	}
	
	
	/* (non-Javadoc)
	 * @see com.sstc.hmis.permission.service.GrpHotelService#list(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public List<TreeNode> list(Short orgLevel, String hotelId, Integer disableLevel) {
		PermsTGroupHotel hotelQuery = new PermsTGroupHotel();
		Staff staff = LoginInfoHolder.getLoginInfo();
		if(StringUtils.isBlank(hotelId)){
			hotelId = staff.getHotelId();
		}
		hotelQuery.setClId(hotelId);
		//hotelQuery.setClStatus(Constants.STATUS_NORMAL);
		List<Hotel> hotelList = permsTGroupHotelMapper.selectAll(hotelQuery);
		List<TreeNode> list = new ArrayList<>();
		if (null == hotelList || hotelList.size() == 0) {
			return list;
		}
		List<TreeNode> children = new ArrayList<>();
		for (Hotel hotel : hotelList) {
			TreeNode root = new TreeNode(hotel.getName(), hotel.getId(), hotel.getPid());
			if(hotelId.equals(staff.getGrpId())){
				root.setNodeType(Constants.NODE_TYPE_ORG);
			}else{
				root.setNodeType(Constants.NODE_TYPE_HOTEL);
			}
			
			if(disableLevel != null && disableLevel == Constants.NODE_TYPE_HOTEL){
				root.setDisabled(true);
			}
			
			root.setCls(Constants.NODE_ICON_ORG);
			root.setExpanded(true);
			List<TreeNode> departmentNodeList = doLoopDepartment(hotel.getDepartmentList(),orgLevel);

			if(orgLevel != null && orgLevel == Constants.ORG_PATH_DPT){
				return departmentNodeList;
			}
			children.addAll(departmentNodeList);
			if(orgLevel != null && orgLevel != Constants.ORG_PATH_HTL_DPT){
				List<TreeNode> hotelNodeList = doLoopHotel(hotel.getHotelList(),orgLevel);
				children.addAll(hotelNodeList);
			}

			root.setChildren(children);
			list.add(root);
		}
		return list;
	}

	@Override
	public List<TreeNode> list(Short orgLevel, String hotelId) {
		return list(orgLevel, hotelId, null);
	}
	
	@Override
	public List<TreeNode> list2(Short orgLevel, String hotelId) {
		Staff staff = LoginInfoHolder.getLoginInfo();
		List<TreeNode> list = null;
		if(StringUtils.equals(staff.getHotelId(), staff.getGrpId())){
			list = new ArrayList<TreeNode>();
			list.add(new TreeNode("请选择集团或酒店","-1"));
			List<TreeNode> list2 = list(orgLevel, hotelId, null);
			list.addAll(list2);
			return list;
		} else {
			return list(orgLevel, hotelId, null);
		} 
	}
	
	/**
	 * 查询酒店部门
	 * @param hotelId
	 */
	@Override
	public List<TreeNode> list(String hotelId) {
		return list(Constants.ORG_PATH_DPT, hotelId, null);
	}
	
	@Override
	public List<TreeNode> list(Short orgLevel) {
		if(orgLevel == null){
			orgLevel = 0;
		}
		return list(orgLevel, null);
	}

	@Override
	public GroupHotel find(@RequestBody GroupHotel hotel) throws AppException {
		String hotelId = hotel.getId();
		PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelId);
		hotel = convertPermsTGroupHotel2GroupHotel(permsTGroupHotel);
		return hotel;
	}

	@Override
	public int updateStatusByPrimaryKeys(@RequestBody List<String> ids, int status) throws AppException {
		short blockup = Constants.BLOCKUP_NO;
		if(Constants.STATUS_LOCK == status){
			blockup = Constants.BLOCKUP_YES;
		}
		int count = permsTGroupHotelMapper.updateStatusByPrimaryKeys(ids, status,blockup);
		return count;
	}

	private List<TreeNode> doLoopHotel(List<Hotel> hoteNodelList, Short orgLevel) {
		List<TreeNode> list = new ArrayList<>();
		if (null == hoteNodelList || hoteNodelList.size() == 0) {
			return list;
		}
		for (Hotel hotel : hoteNodelList) {
			TreeNode hotelNode = new TreeNode(hotel.getName(), hotel.getId(), hotel.getPid());
			List<TreeNode> children = new ArrayList<>();
			hotelNode.setNodeType(Constants.NODE_TYPE_HOTEL);
			hotelNode.setCls(Constants.NODE_ICON_HOTEL);
			List<TreeNode> departmentNodeList = doLoopDepartment(hotel.getDepartmentList(),orgLevel);
			children.addAll(departmentNodeList);

			List<TreeNode> hotelNodeList = doLoopHotel(hotel.getHotelList(),orgLevel);
			children.addAll(hotelNodeList);

			hotelNode.setChildren(children);
			list.add(hotelNode);
		}
		return list;
	}

	private List<TreeNode> doLoopDepartment(List<Dept> departmentNodeList, Short orgLevel) {
		List<TreeNode> list = new ArrayList<>();
		if (null == departmentNodeList || departmentNodeList.size() == 0) {
			return list;
		}
		for (Dept department : departmentNodeList) {
			TreeNode dptNode = new TreeNode(department.getName(), department.getId(), department.getPid());
			List<TreeNode> children = new ArrayList<>();
			dptNode.setNodeType(Constants.NODE_TYPE_DPT);
			dptNode.setCls(Constants.NODE_ICON_DPT);

				List<TreeNode> departmentNodes = doLoopDepartment(department.getDepartmentList(),orgLevel);
				children.addAll(departmentNodes);
			if(orgLevel != null && orgLevel == Constants.ORG_PATH_ALL){
				List<TreeNode> posts = doLoopPost(department);
				children.addAll(posts);
			}
			dptNode.setChildren(children);
			list.add(dptNode);
		}
		return list;
	}

	private List<TreeNode> doLoopPost(Dept department) {
		List<TreeNode> list = new ArrayList<>();
		List<Post> postList = department.getPostList();
		if (null == postList || postList.size() == 0) {
			return list;
		}
		for (Post permsTDptPost : postList) {
			TreeNode postNode = new TreeNode(permsTDptPost.getName(), permsTDptPost.getId(), permsTDptPost.getPid());
			postNode.setChildren(new ArrayList<TreeNode>());
			postNode.setNodeType(Constants.NODE_TYPE_POST);
			list.add(postNode);
		}
		return list;
	}

	public GroupHotel convertPermsTGroupHotel2GroupHotel(PermsTGroupHotel permsTGroupHotel) {
		GroupHotel groupHotel = new GroupHotel();
		try {
			BeanUtils.copyDbBean2ServiceBean(permsTGroupHotel, groupHotel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return groupHotel;
	}

	public PermsTGroupHotel convertGroupHotel2PermsTGroupHotel(GroupHotel groupHotel) {
		PermsTGroupHotel permsTGroupHotel = new PermsTGroupHotel();
		try {
			BeanUtils.copyServiceBean2DbBean(groupHotel, permsTGroupHotel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return permsTGroupHotel;
	}

	/**
	 * 查询除当前登录酒店以外的酒店信息
	 * @author CKang
	 * @date 2017年4月21日 上午9:59:22
	 * @return
	 */
	@Override
	public List<GroupHotel> findGrpHotelNotByHotelId() {
		PermsTGroupHotelExample example = new PermsTGroupHotelExample();
		Criteria criteria = example.createCriteria();
		criteria.andClIdNotEqualTo(LoginInfoHolder.getLoginInfo().getHotelId());
		List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.selectByExample(example);
		List<GroupHotel> groupHotelList = new ArrayList<GroupHotel>();
		for(PermsTGroupHotel permsTGroupHotel : permsTGroupHotelList){
			groupHotelList.add(convertPermsTGroupHotel2GroupHotel(permsTGroupHotel));
		}
		return groupHotelList;
	}


	@Override
	public GroupHotel findGroupHotelById(String id) {
		PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(id);
		return this.convertPermsTGroupHotel2GroupHotel(permsTGroupHotel);
	}

	@Override
	public List<GroupHotel> findStaffHotelByStaffId(@RequestBody StaffHotel staffHotel) {
		List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.findStaffHotelByStaffId(convertstaffHotel2PermsTStaffHotel(staffHotel));
		List<GroupHotel> list = new ArrayList<GroupHotel>();
		for(PermsTGroupHotel permsTGroupHotel : permsTGroupHotelList){
			list.add(convertPermsTGroupHotel2GroupHotel(permsTGroupHotel));
		}
		return list;
	}
	/*
	 * StaffHotel转换PermsTStaffHotel实体类
	 */
	private PermsTStaffHotel convertstaffHotel2PermsTStaffHotel(StaffHotel staffHotel){
		try {
			return BeanUtils.copyServiceBean2DbBean(staffHotel, new PermsTStaffHotel());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<GroupHotel> findhotelList(String grpId) {
		PermsTGroupHotelExample example = new PermsTGroupHotelExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(grpId)){
			criteria.andClPidEqualTo(grpId);
		}
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTGroupHotel> permsTGroupHotelList = permsTGroupHotelMapper.selectByExample(example);
		List<GroupHotel> list = new ArrayList<GroupHotel>();
		for(PermsTGroupHotel permsTGroupHotel : permsTGroupHotelList){
			list.add(convertPermsTGroupHotel2GroupHotel(permsTGroupHotel));
		}
		return list;
	}
	
	@Override
	public GroupHotel findGrpHotelByOrgInfo(String orgId, Short orgType){
		GroupHotel grpHotel = null;
		if(orgType != null && StringUtils.isNotBlank(orgId)){
			String hotelId = "";
			switch (orgType) {
			case Constants.NODE_TYPE_DPT:
				PermsTDepartment dpt = permsTDepartmentMapper.selectByPrimaryKey(orgId);
				if(dpt != null && dpt.getClStatus() == Constants.STATUS_NORMAL){
					hotelId = dpt.getClHotelId();
				}
				break;
			case Constants.NODE_TYPE_POST:
				PermsTPostResponsibility post = permsTPostResponsibilityMapper.selectByPrimaryKey(orgId);
				if(post != null && post.getClStatus() == Constants.STATUS_NORMAL){
					hotelId = post.getClHotelId();
				}
				break;
			case Constants.NODE_TYPE_HOTEL:
			case Constants.NODE_TYPE_ORG:
				hotelId = orgId;
			default:
				break;
			}
			if(StringUtils.isNotBlank(hotelId)){
				PermsTGroupHotel permsTGroupHotel = permsTGroupHotelMapper.selectByPrimaryKey(hotelId);
				if(permsTGroupHotel != null && permsTGroupHotel.getClStatus() != Constants.STATUS_DEL){
					grpHotel = new GroupHotel();
					try {
						BeanUtils.copyDbBean2ServiceBean(permsTGroupHotel, grpHotel);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return grpHotel;
	}

	@Override
	public List<GroupInfo> getGroupInfoByHotelId(@RequestBody Map<String, Object> paramMap) {
		
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getGroupInfoByHotelId(paramMap), GroupInfo.class);
	}

	@Override
	public List<String> getUserIdsByDeptId(@RequestBody Map<String, Object> paramMap) {
		return permsTGroupHotelMapper.getUserIdsByDeptId(paramMap);
	}

	@Override
	public List<WorkGroupInfo> getWorkGroupList(@RequestBody Map<String, Object> paramMap) {
		
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getWorkGroupList(paramMap), WorkGroupInfo.class);
	}

	@Override
	public List<LinkmanInfo> getUserListByWorkGroupId(@RequestBody Map<String, Object> paramMap) {
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getUserListByWorkGroupId(paramMap), LinkmanInfo.class);
	}

	@Override
	public List<LinkmanInfo> getAllUser(@RequestBody Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getAllUser(paramMap), LinkmanInfo.class);
	}

	@Override
	public List<LinkHotelInfo> getLinkmanHotelInfoList(@RequestBody Map<String, Object> paramMap) {
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getLinkmanHotelInfoList(paramMap), LinkHotelInfo.class);
	}

	@Override
	public List<LinkmanDeptInfo> getLinkmanDeptInfoList(@RequestBody Map<String, Object> paramMap) {
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getLinkmanDeptInfoList(paramMap), LinkmanDeptInfo.class);
	}

	@Override
	public List<ContactsInfo> getContactsInfoList(@RequestBody Map<String, Object> paramMap) {
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTGroupHotelMapper.getContactsInfoList(paramMap), ContactsInfo.class);
	}
	
	@Override
	public List<String> getAllHotel() {
		
		return permsTGroupHotelMapper.getAllHotel();
	}

}
