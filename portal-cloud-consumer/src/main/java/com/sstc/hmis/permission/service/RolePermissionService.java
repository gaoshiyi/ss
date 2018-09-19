/**
 * 
 */
package com.sstc.hmis.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
  * <p> Title: RoleService </p>
  * <p> Description:  角色权限关联信息 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年3月31日 上午11:48:21
   */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/rolePermService")
public interface RolePermissionService {

}
