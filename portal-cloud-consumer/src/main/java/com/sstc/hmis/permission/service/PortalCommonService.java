/**
  * 文件名：CommonService.java
  * 日期：2017年3月21日
  * Copyright sstc Corporation 2017 
  * 版权所有
  */
package com.sstc.hmis.permission.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.permission.data.Area;

/**
 * @ClassName CommonService
 * @Description 公共service
 * @author yaodm
 * @date 2017年3月21日 上午9:57:27 
*/
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/portalCommonService")
public interface PortalCommonService {
	
	/**
	 * 方法描述: 获取不同级别下的区域数据：<br/>
	 * level=0 国家<br/>
	 * level=1 省<br/>
	 * level=2 市<br/>
	 * @author yaodm
	 * @date 2017年3月21日 上午10:04:34
	 * @param level 级别
	 * @param areaId 当前所属ID
	 * @param parentId 父节点ID
	 * @return List<Area>
	 */
	@RequestMapping(value = "/getAreaSettingData" , method = RequestMethod.POST)
	public List<Area> getAreaSettingData(@RequestParam("level") int level,
			@RequestParam("areaId") String areaId,@RequestParam("parentId") String parentId,
			@RequestParam("grpId")String grpId,@RequestParam("hotelId")String hotelId);

	/**
	 * @param keysyset 系统键
	 * @param keyvalue 键值
	 * @param grpId 集团ID
	 * @param hotelId 酒店ID
	 * @return
	 */
	@RequestMapping(value = "/getSelectEnumByHotelId" , method = RequestMethod.POST)
	public Map<String, String> getSelectEnumByHotelId(@RequestParam("keysyset")String keysyset, 
			@RequestParam("keyvalue")String keyvalue,@RequestParam("grpId")String grpId,@RequestParam("hotelId")String hotelId);
}
