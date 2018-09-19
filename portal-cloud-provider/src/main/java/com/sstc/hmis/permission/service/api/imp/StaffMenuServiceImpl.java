/**
 * 
 */
package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.mdata.service.api.ParamService;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.MenuTreeNode;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.permission.data.SystemMenu;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPermissionMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffMenuMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermission;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermissionExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffMenu;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffMenuExample;
import com.sstc.hmis.permission.dbaccess.data.domain.PermParent;
import com.sstc.hmis.permission.service.GrpHotelService;
import com.sstc.hmis.permission.service.StaffMenuService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.DateUtils;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
  * <p> Title: StaffMenuServiceImp </p>
  * <p> Description:  员工菜单管理实现逻辑 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月23日 下午1:57:12
   */
@RestController
public class StaffMenuServiceImpl implements StaffMenuService{

	@Autowired
	private PermsTStaffMenuMapper permsTStaffMenuMapper;
	@Autowired
	private PermsTPermissionMapper permsTPermissionMapper;
	@Autowired
	private GrpHotelService grpHotelService;
	@Autowired
	private ParamService paramService;
	@Autowired  
    private Environment env; 
	
	@Override
	public Result addMenu(String menuId, Short type,Short collected) {
		Result result = Result.ERROR_PARAMS;
		if(StringUtils.isBlank(menuId)){
			return result;
		}
		try {
			Map<String,Object> data = new HashMap<>(4);
			data.put("collected", collected);
			data.put("menuId", menuId);
			data.put("type", type);
			String queryId = menuId;
			if(type == PortalConstants.MENU_TYPE_HOME){
				//查询是否设置过我的主页
				queryId = null;
			}
			PermsTStaffMenuExample example = getQueryExample(queryId,type);
			int count = permsTStaffMenuMapper.countByExample(example);
			String successMsg = "操作成功";
			if(count > 0){
				switch (collected) {
				case PortalConstants.MENU_COLLECT:
					//如果已经设置过首页则更新数据
					if(type == PortalConstants.MENU_TYPE_HOME){
						PermsTStaffMenu record = new PermsTStaffMenu();
						record.setClMenuId(menuId);
						record.setClUpdateTime(new Date());
						record.setClStatus(Constants.STATUS_NORMAL);
						PermsTStaffMenuExample updateExample = new PermsTStaffMenuExample();
						PermsTStaffMenuExample.Criteria criteria = updateExample.createCriteria();
						criteria.andClTypeEqualTo(PortalConstants.MENU_TYPE_HOME);
						criteria.andClStaffIdEqualTo(LoginInfoHolder.getLoginInfo().getId());
						criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
						permsTStaffMenuMapper.updateByExampleSelective(record, updateExample);
					}
					break;
				case PortalConstants.MENU_DISCOLLECT:
					PermsTStaffMenu record = new PermsTStaffMenu();
					record.setClMenuId(menuId);
					PermsTStaffMenuExample delExample = new PermsTStaffMenuExample();
					PermsTStaffMenuExample.Criteria criteria = delExample.createCriteria();
					criteria.andClMenuIdEqualTo(menuId);
					criteria.andClStaffIdEqualTo(LoginInfoHolder.getLoginInfo().getId());
					criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
					permsTStaffMenuMapper.deleteByExample(example);
					break;
				}
				result = Result.successResult(successMsg);
			}else{
				String staffId = LoginInfoHolder.getLoginInfo().getId();
				String hotelId = LoginInfoHolder.getLoginHotelId();
				String grpId = LoginInfoHolder.getLoginGrpId();
				String id = HashUtils.uuidGenerator();
				PermsTStaffMenu menu = new PermsTStaffMenu(id, staffId, menuId, 
						Constants.STATUS_NORMAL, type, grpId, hotelId, new Date(), null);
				count = permsTStaffMenuMapper.insert(menu);
				if(count > 0){
					result = Result.successResult(successMsg);
					PermsTPermission permission = permsTPermissionMapper.selectByPrimaryKey(menuId);
					List<PermsTPermission> permList = Arrays.asList(permission);
					dealPermUrl(permList);
					data.put("perm", BeanUtils.copyDbBean2ServiceBean(permList.get(0), Permission.class));
				}
			}
			result.setResult(data);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.errorResult("操作失败");
		}
		return result;
	}

	@Override
	public Result delMenu(String menuId) {
		Result result = Result.ERROR_PARAMS;
		if(StringUtils.isNotBlank(menuId)){
			try {
				PermsTStaffMenuExample example = getQueryExample(menuId,PortalConstants.MENU_TYPE_MENU);
				permsTStaffMenuMapper.deleteByExample(example);
				result = Result.successResult("删除成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private PermsTStaffMenuExample getQueryExample(String menuId,Short type){
		String staffId = LoginInfoHolder.getLoginInfo().getId();
		String hotelId = LoginInfoHolder.getLoginHotelId();
		String grpId = LoginInfoHolder.getLoginGrpId();
		PermsTStaffMenuExample example = new PermsTStaffMenuExample();
		PermsTStaffMenuExample.Criteria criteria = example.createCriteria();
		criteria.andClStaffIdEqualTo(staffId);
		criteria.andClHotelIdEqualTo(hotelId);
		criteria.andClGrpIdEqualTo(grpId);
		if(StringUtils.isNoneBlank(menuId)){
			criteria.andClMenuIdEqualTo(menuId);
		}
		if(type != null){
			criteria.andClTypeEqualTo(type);
		}
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		return example;
	}

	@Override
	public Permission homePage() {
		try {
			PermsTStaffMenuExample example = getQueryExample(null,PortalConstants.MENU_TYPE_HOME);
			PermsTStaffMenu permsTStaffMenu = permsTStaffMenuMapper.selectOneByExample(example);
			if(permsTStaffMenu != null){
				String menuId = permsTStaffMenu.getClMenuId();
				PermsTPermission permsTPermission = permsTPermissionMapper.selectByPrimaryKey(menuId);
				
				if(permsTPermission != null){
					List<PermsTPermission> permList = Arrays.asList(permsTPermission);
					dealPermUrl(permList);
					return BeanUtils.copyDbBean2ServiceBean(permList.get(0), Permission.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 统一处理权限的url，数据库中是相对路径，需要拼上绝对路径
	 * @param permList 权限列表
	 */
	public void dealPermUrl(List<PermsTPermission> permList) {
		GroupHotel grpHotel = null;
		List<String> idList = new ArrayList<>(permList.size());
		if(permList != null && permList.size() > 0){
			
			for (PermsTPermission permission : permList) {
				idList.add(permission.getClId());
			}
			//获取权限对象的系统ID
			List<PermParent> parentList = permsTPermissionMapper.selectPermSystemId(idList);
			Map<String,String> permPidMap = new HashMap<>(parentList.size());
			
			for (PermParent permParent : parentList) {
				permPidMap.put(permParent.getPermId(), permParent.getSystemId());
			}
			
			for (PermsTPermission permsTPermission : permList) {
				String path = permsTPermission.getClUrl();
				if(StringUtils.isNoneBlank(path)){
					String clId = permsTPermission.getClId();
					String uri = env.getProperty("hostname."+permPidMap.get(clId));
					if(env.getProperty("sys.pms","a3e37c9a6a8c48a99b32c590e68a084a").equals(permPidMap.get(clId))){
						if(grpHotel == null){
							grpHotel = grpHotelService.findGroupHotelById(LoginInfoHolder.getLoginHotelId());
						}
						try {
							//拼接区域code，达到不同酒店不同的访问子域名
							String region = grpHotel.getRegion();
							if(StringUtils.isNotBlank(region)){
//								uri = uri.replace("http://", "http://" + region + ".");
								uri = uri + "-" + region;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String url = uri + path;
					permsTPermission.setClUrl(url);
				}
			}
		}
	}

	@Override
	public Result myMenus() {
		PermsTStaffMenuExample example = getQueryExample(null,PortalConstants.MENU_TYPE_MENU);
		List<PermsTStaffMenu> dbStaffMenus = permsTStaffMenuMapper.selectByExample(example);
		List<String> idList = new ArrayList<>();
		for (PermsTStaffMenu permsTStaffMenu : dbStaffMenus) {
			idList.add(permsTStaffMenu.getClMenuId());
		}
		List<Permission> menuList = new ArrayList<>();
		List<Permission> reportList = new ArrayList<>();
		if(idList.size() > 0){
			PermsTPermissionExample permissionExample = new PermsTPermissionExample();
			PermsTPermissionExample.Criteria criteria = permissionExample.createCriteria();
			criteria.andClIdIn(idList);
			criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
			List<PermsTPermission> list = permsTPermissionMapper.selectByExample(permissionExample);
			
			dealPermUrl(list);
			
			for (PermsTPermission permsTPermission : list) {
				Short isReport = permsTPermission.getClIsReport();
				Permission permission = BeanUtils.copyDbBean2ServiceBean(permsTPermission, Permission.class);
				if (isReport == PortalConstants.MENU_TYPE_REPORT) {
					reportList.add(permission);
				}else{
					menuList.add(permission);
				}
			}
		}
		Map<String,List<Permission>> data = new HashMap<>(2);
		data.put("menu", menuList);
		data.put("report", reportList);
		Result result = Result.SUCCESS;
		result.setResult(data);
		return result;
	}
	
	@Override
	public Result listUserMenu(){
		Result result = Result.SUCCESS;
		Staff staff = LoginInfoHolder.getLoginInfo();
		Map<String,Object> condition = new HashMap<>(2);
		Map<String,Object> data = new HashMap<>(2);
		condition.put("staffId", staff.getId());
		condition.put("hotelId", LoginInfoHolder.getLoginHotelId());
		List<PermsTPermission> list = permsTPermissionMapper.listUserMenu(condition);
		
		if(CollectionUtils.isNotEmpty(list)){
			dealPermUrl(list);
			List<SystemMenu> menu = exchange2Menu(list);
			data.put("menu", menu);
		}
		
		Permission homePermission = this.homePage();
		data.put("homePage", homePermission);
		Date bizDate = new Date();
		try {
			bizDate = paramService.getBusinessDay(LoginInfoHolder.getLoginHotelId());
			data.put("bizDate", DateUtils.format(bizDate, DateUtils.DATE_FMT_DATE_1));
		} catch (Exception e) {
			e.printStackTrace();
			data.put("bizDate", "");
		}
		result.setResult(data);
		return result;
	}

	/**
	 * 权限转菜单树
	 * @param list 权限列表
	 * @return
	 */
	private List<SystemMenu> exchange2Menu(List<PermsTPermission> list) {
		List<MenuTreeNode> menuAll = new ArrayList<>(list.size());
		Set<String> sysMenu = new HashSet<String>();
		for (PermsTPermission perm : list) {
			MenuTreeNode node = new MenuTreeNode(perm.getClId(), 
					perm.getClIconClass(), perm.getClName(), perm.getClUrl(), perm.getClPid(),perm.getClHeight(),perm.getClWidth(),perm.getClOpenType());
			menuAll.add(node);
			if(perm.getClType() == Constants.PERMS_SYSTEM){
				sysMenu.add(perm.getClId());
			}
		}
		
		List<MenuTreeNode> menu = new ArrayList<>(10);
		for (MenuTreeNode node1 : menuAll) {
			String pid1 = node1.getPid();
			String id = node1.getId();
			if(sysMenu.contains(id)){
				menu.add(node1);
				continue;
			}
			for (MenuTreeNode node2 : menuAll) {
				String id2 = node2.getId();
				if(StringUtils.isNotBlank(pid1) && StringUtils.equals(pid1, id2) ){
					if(node2.getItems() == null){
						node2.setItems(new ArrayList<>());
						node2.getItems().add(node1);
					}else{
						node2.getItems().add(node1);
					}
				}
			}
		}
		
		
		
//		给请求链接添加uri路径，因为所有请求都是在portal请求，
//		浏览器根路径不变，如果要访问其他系统则需要其他系统的全路径访问
		List<SystemMenu> sysList = new ArrayList<>(menu.size());
		for (MenuTreeNode root : menu) {
//			坑爹的插件，一个json得用两个对象，系统是SystemMenu对象，菜单是MenuTreeNode对象
			SystemMenu sys = new SystemMenu(root.getId(), root.getElCls(), root.getText(),
					root.getHref(), root.getPid(), root.getItems());
			sysList.add(sys);
		}
		return sysList;
	}

	
}
