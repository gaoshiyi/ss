package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.model.constants.PortalConstants;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.model.exception.AppException;
import com.sstc.hmis.model.tree.TreeNode;
import com.sstc.hmis.permission.data.Permission;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPermissionMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTRolePermissionMapper;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffRoleMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermission;
import com.sstc.hmis.permission.dbaccess.data.PermsTPermissionExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTRolePermission;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRole;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffRoleExample;
import com.sstc.hmis.permission.dbaccess.data.domain.PermsTPermissionVo;
import com.sstc.hmis.permission.service.PermissionService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;
@RestController
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService{
	@Autowired
	private PermsTPermissionMapper permsTPermissionMapper;
	@Autowired
	private PermsTStaffRoleMapper permsTStaffRoleMapper;
	@Autowired
	private PermsTRolePermissionMapper permsTRolePermissionMapper;
	
	@Autowired  
    private Environment env; 
	
	
	@Override
	public Permission find(@RequestBody Permission query){
		Permission result = new Permission();
		try {
			PermsTPermissionExample example = new PermsTPermissionExample();
			PermsTPermissionExample.Criteria criteria = example.createCriteria();
			if(StringUtils.isNoneBlank(query.getId())){
				criteria.andClIdEqualTo(query.getId());
			}
			if(StringUtils.isNoneBlank(query.getName())){
				criteria.andClNameLike(query.getName());
			}
			if(query.getType() != null){
				criteria.andClTypeEqualTo(query.getType());
			}
			if(StringUtils.isNoneBlank(query.getPid())){
				criteria.andClPidEqualTo(query.getPid());
			}
			List<PermsTPermission> list = permsTPermissionMapper.selectByExample(example);
			if(list != null && list.size() > 0){
				BeanUtils.copyDbBean2ServiceBean(list.get(0), result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 加载用户权限列表
	 */
	@Override
	public List<TreeNode> loadPermissionTree(@RequestParam(name="roleId",required=false) String roleId) {
		
		//查询集团所有的权限
		List<PermsTPermission> permissionList = permsTPermissionMapper.findAllPerms();//permsTPermissionMapper.findPermissionByStaffId(permsTStaffRole);
		
		List<TreeNode> allNode = new ArrayList<>(permissionList.size());
		for (PermsTPermission permsTPermission : permissionList) {
			allNode.add(new TreeNode(permsTPermission.getClName(), permsTPermission.getClId(), permsTPermission.getClPid(),true));
		}
		
		if(StringUtils.isNotBlank(roleId)){
			List<PermsTRolePermission> rolePermList = permsTRolePermissionMapper.findPermissionIdByRoleId(roleId);
			//查询当前编辑角色的权限ID
			List<String> rolePermsIds = rolePermList.stream().map(perm -> perm.getClPermissionId()).collect(Collectors.toList());
			if(rolePermsIds.size() > 0){
				//将角色已有的权限设置checked=true
				allNode.stream().filter(node -> rolePermsIds.contains(node.getId())).forEach(node -> node.setChecked(true));
			}
		}
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for(TreeNode treeNode1 : allNode){
			String pid = treeNode1.getParentId();
			//如果是treeNode1是 子节点才去查找子节点的父节点
			if(StringUtils.isNoneBlank(pid)){
				for(TreeNode treeNode2 : allNode){
					//如果子节点的父节点ID 等于  当前节点的ID ，则将子节点 set 给父节点的 children 
					if(pid.equals(treeNode2.getId())){
						// 当找到子节点的父节点 时，判断父节点是否以存放了子节点list
						if(treeNode2.getChildren() == null){
							//如果没有就将子节点加入list,一遍下面直接get后add子节点
							treeNode2.setChildren(new ArrayList<TreeNode>());
						}
						
						if(!treeNode1.getChecked()){
							treeNode2.setChecked(Boolean.FALSE);
						}
						treeNode2.getChildren().add(treeNode1); 
					}
				}
			}else{
				//nodeList输出只存放根节点
				nodeList.add(treeNode1); 
			}
		}
		return nodeList;
	}
	
	/**
	 * cas认证查询用户角色权限相关信息
	 * @param roleIds 用户角色ID列表
	 * @return 用户角色所拥有的权限列表
	 */
	@Override
	public List<Permission> findUserPerms(@RequestBody Set<String> roleIds) {
		List<Permission> permsList = new ArrayList<>();
		if (roleIds != null && roleIds.size() > 0) {
			Map<String,Object> condition = new HashMap<>();
			condition.put("roleIds", roleIds);
			List<PermsTPermission> perms = permsTPermissionMapper.selectAllPermsByRoleIds(condition); 
			
			try {
				permsList = BeanUtils.copyDbBeanList2ServiceBeanList(perms, Permission.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return permsList;
	}

	/**
	 * 查询系统中所有的请求权限配置的url，将url加入到权限认证的Filter中
	 * 用户每次请求会通过比对url是否需要经过perm别名的Filter
	 * @return 系统所有请求url的集合
	 */
	@Override
	public Set<String> findAllFuncPerms() {
		PermsTPermissionExample example = new PermsTPermissionExample();
		PermsTPermissionExample.Criteria criteria = example.createCriteria();
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<Short> types = new ArrayList<>(2);
		types.add(Constants.PERMS_ACTION);
		types.add(Constants.PERMS_FUNCTION);
		types.add(Constants.PERMS_MODULE);
		criteria.andClTypeIn(types);
		List<PermsTPermission> list = permsTPermissionMapper.selectByExample(example);
		Set<String> urlList = new HashSet<>(list.size());
		for (PermsTPermission permsTPermission : list) {
			String url = permsTPermission.getClUrl();
			if(StringUtils.isNoneBlank(url)){
				urlList.add(url.trim());
			}
			String[] subUrls = permsTPermission.getClSubUrl();
			if(subUrls != null){
				for (String subUrl : subUrls) {
					if(StringUtils.isNoneBlank(subUrl)){
						urlList.add(subUrl);
					}
				}
			}
		}
		return urlList;
	}


	/*
	 * 加载已选中的权限信息
	 */
	@Override
	public List<Permission> loadcheckedPermissionList(String roleId) {
		List<PermsTPermission> permsTPermissionList = permsTPermissionMapper.loadcheckedPermissionList(roleId);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTPermissionList, Permission.class);
	}

	@Override
	public List<TreeNode> permTree() {
		List<TreeNode> list = new ArrayList<>();
		try {
			list = permsTPermissionMapper.selectPermTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Permission> list(@RequestBody Permission t) throws AppException {
		PermsTPermissionExample example = new PermsTPermissionExample();
		PermsTPermissionExample.Criteria criteria = example.createCriteria();
		if(t != null){
			if(StringUtils.isNoneBlank(t.getId())){
				criteria.andClIdEqualTo(t.getId());
			}
			if(StringUtils.isNotBlank(t.getName())){
				criteria.andClNameLike(t.getName());
			}
			if(t.getType() != null){
				criteria.andClTypeEqualTo(t.getType());
			}
			if(StringUtils.isNoneBlank(t.getPid())){
				criteria.andClPidEqualTo(t.getPid());
			}
			if(t.getStatus() != null){
				criteria.andClStatusEqualTo(t.getStatus());
			}else{
				criteria.andClStatusNotEqualTo(Constants.STATUS_DEL);
			}
		}
		List<PermsTPermission> list = permsTPermissionMapper.selectByExample(example);
		return BeanUtils.copyDbBeanList2ServiceBeanList(list, Permission.class);
		
	}

	@Override
	public Result resultList(@RequestBody Permission permission) {
		Result result = Result.SUCCESS;
		try {
			result.setResult(list(permission));
		} catch (AppException e) {
			e.printStackTrace();
			result = Result.ERROR_SYS;
		}
		return result;
	}

	@Override
	public PageResult<Permission> pageList(int index, int size, @RequestBody Permission permission) {
		
		PageResult<Permission> result = new PageResult<Permission>();
		PageHelper.startPage(index + 1, size);
		PermsTPermission permsTPermission = new PermsTPermission();
		try {
			BeanUtils.copyServiceBean2DbBean(permission, permsTPermission);
			Page<PermsTPermissionVo> page = permsTPermissionMapper.pageList(permsTPermission);
			if(page != null){
				long total = page.getTotal();
				if(total > 0){
					List<PermsTPermissionVo> voList = page.getResult();
					List<Permission> permList = BeanUtils.copyDbBeanList2ServiceBeanList(voList, Permission.class);
					result.setRows(permList);
				}
				result.setResults(total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Result saveOrUpdate(@RequestBody Permission permission) {
		if(permission != null){
			PermsTPermission permsTPermission = new PermsTPermission();
			try {
				String pid = permission.getPid();
				if(StringUtils.isBlank(pid)){
					permission.setPid(null);
				}
				BeanUtils.copyServiceBean2DbBean(permission, permsTPermission);
				if(StringUtils.isBlank(permsTPermission.getClName())){
					return Result.ERROR_PARAMS;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String id = permission.getId();
			int effectLine = 0;
			if(StringUtils.isNoneBlank(id)){
				effectLine = permsTPermissionMapper.updateByPrimaryKeySelective(permsTPermission);
			}else{
				permsTPermission.setClId(HashUtils.uuidGenerator());
				permsTPermission.setClCreateBy(LoginInfoHolder.getLoginAccount());
				permsTPermission.setClStatus(Constants.STATUS_NORMAL);
				effectLine = permsTPermissionMapper.insertSelective(permsTPermission);
			}
			if(effectLine > 0){
				//更新shiro的拦截链
//				Set<String> urlList = (Set<String>) this.findAllFuncPerms();
//				Map<String,String> section = new LinkedHashMap<String, String>();
//				for (String url : urlList) {
//					section.put(url, "perms["+url+"]");
//				}
//				section.put("/**", "perms,authc,user,url");
//				shiroFilterFactoryBean.setFilterChainDefinitionMap(section);
				
				return Result.SUCCESS;
			}
		}
		return Result.ERROR_SYS;
	}

	@Override
	public Permission findById(String id){
		Permission permission = new Permission();
		if(StringUtils.isNoneBlank(id)){
			PermsTPermission permsTPermission = permsTPermissionMapper.selectByPrimaryKey(id);
			try {
				BeanUtils.copyDbBean2ServiceBean(permsTPermission, permission);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return permission;
	}
	
	@Override
	public Result batchDelete(String[] ids){
		PermsTPermission record = new PermsTPermission();
		record.setClStatus(Constants.STATUS_DEL);
		int i = 0;
		for (String id : ids) {
			record.setClId(id);
			i = permsTPermissionMapper.updateByPrimaryKeySelective(record);
		}
		if(i == 0){
			return Result.ERROR_PARAMS;
		}
		return Result.SUCCESS;
	}

	@Override
	public List<Permission> listUserSysModule(@RequestBody Permission query) {
		
		String pid = query.getPid();
//		String cacheKey = MessageFormat.format(CacheConstants.SYS_MODULE_LIST, pid, LoginInfoHolder.getLoginInfo().getId());
//		List<CasPermission> result = redisCache.getList(cacheKey, CasPermission.class);
//		if(result != null && result.size() > 0){
//			return result;
//		}else{
		List<Permission>	result = new ArrayList<>();
//		}

		PermsTStaffRoleExample example = new PermsTStaffRoleExample();
		PermsTStaffRoleExample.Criteria criteria = example.createCriteria();
		criteria.andClStaffIdEqualTo(LoginInfoHolder.getLoginInfo().getId());
		criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTStaffRole> roleList = permsTStaffRoleMapper.selectByExample(example);
		Set<String> roleIdSet = new HashSet<>();
		for (PermsTStaffRole staffRole : roleList) {
			String id = staffRole.getClRoleId();
			roleIdSet.add(id);
		}
		List<Permission> list =  this.findUserPerms(roleIdSet);
		if (list != null && list.size() > 0) {
			String hostName = env.getProperty("hostname."+pid);
			for (Permission casPermission : list) {
				String modulePid = casPermission.getPid();
				Short type = casPermission.getType();
				if (StringUtils.equals(modulePid, pid) && type == Constants.PERMS_MODULE) {
					String url = casPermission.getUrl();
					if(StringUtils.isBlank(url)){
						String moduleId = casPermission.getId();
						for (Permission casPermission2 : list) {
							if (StringUtils.equals(moduleId, casPermission2.getPid())) {
								url = casPermission2.getUrl();
								casPermission.setUrl(hostName + url);
								break;
							}
						}
					}else{
						url = hostName + url;
						casPermission.setUrl(url);
					}
					result.add(casPermission);
				}
			}
		}
//		redisCache.setList(cacheKey, result);
		return result;
	}

	@Override
	public Result getPermissionUrlById(String permId) {
		Result result = Result.ERROR_PARAMS;
		if(StringUtils.isNotBlank(permId)){
			List<PermsTPermission> permList = permsTPermissionMapper.getSystemPermissionById(permId);
			if(CollectionUtils.isNotEmpty(permList)){
				String hostName = "";
				String uri = "";
				for (PermsTPermission permsession : permList) {
					String id = permsession.getClId();
					if(permsession.getClType() == PortalConstants.PERMS_SYSTEM){
						hostName = env.getProperty("hostname." + id);
					}else if(StringUtils.equalsIgnoreCase(id, permId)){
						uri = permsession.getClUrl();
					}
				}
				String url = hostName + uri;
				Map<String,Object> map = new HashMap<>(1);
				map.put("url", url);
				result = Result.SUCCESS;
				result.setResult(map);
			}
		}
		return result;
	}

	
	
}
