/**
  * 文件名：CommonServiceImp.java
  * 日期：2017年3月21日
  * Copyright sstc Corporation 2017 
  * 版权所有
  */
package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.mdata.service.api.GenCodeService;
import com.sstc.hmis.permission.data.Area;
import com.sstc.hmis.permission.service.PortalCommonService;

/**
 * @ClassName CommonServiceImp
 * @Description 公共service 实现 
 * @author yaodm
 * @date 2017年3月21日 上午9:58:40 
*/
@RestController
public class PortalCommonServiceImpl implements PortalCommonService {
	
	//获取国家
	private static String COUNTRY="COUNTRY";
	//获取省、洲
	private static String PROVINCE="PROVINCE";
	//获取城市
	private static String CITY="CITY";
	
	private static String KEYSYSET="GCOM";
	
	@Resource
	private GenCodeService genCodeService; 

	@Override
	public List<Area> getAreaSettingData(int level, String areaId, String parentId,String grpId,String hotelId) {
		areaId = getCode(areaId);
		parentId = getCode(parentId);
		//都使用集团的配置
		return getAreaData(level,areaId,parentId,grpId,grpId);
	}

	/**
	 * @param level
	 * @param areaId
	 * @param parentId
	 * @param grpId
	 * @param hotelId
	 * @return
	 */
	private List<Area> getAreaData(int level, String areaId, String parentId, String grpId, String hotelId) {
		Map<String, String> map = new HashMap<>();
		switch (level) {
		case 0:
			map = genCodeService.getSelectEnum(KEYSYSET, COUNTRY,grpId,hotelId);
			break;
		case 1:
			map = genCodeService.getCascadaVal(KEYSYSET, PROVINCE, areaId,grpId,hotelId);
			break;
		default:
			map = genCodeService.getCascadaValThree(KEYSYSET, CITY, parentId,areaId,grpId,hotelId);
			break;
		}
		return transfer(map);
	}

	/**
	 * 方法描述: 获取编码
	 * @author yaodm
	 * @date 2017年3月21日 下午1:22:44
	 * @param areaId void  
	 */
	private String getCode(String idAndCode) {
		if (null != idAndCode && idAndCode.length() > 0) {
			String[] str = idAndCode.split("\\+");
			if (str.length > 1) {
				return idAndCode.split("\\+")[1];
			}
		}
		return idAndCode;
	}

	/**
	 * 方法描述: 将map转换为area对象
	 * @author yaodm
	 * @date 2017年3月21日 上午11:28:35
	 * @param map
	 * @param areas void  
	 */
	private List<Area> transfer(Map<String, String> map) {
		List<Area> areas = new ArrayList<>(map.size());
		if (!map.isEmpty()) {
			for(Map.Entry<String, String> entry : map.entrySet()) {
				Area area = new Area();
				area.setId(entry.getKey());
				//area.setName(entry.getValue());
				area.setName(entry.getKey().split("\\+")[1] + "-" + entry.getValue());
				areas.add(area);
			}
		}
		return areas;
	}


	@Override
	public Map<String, String> getSelectEnumByHotelId(String keysyset, String keyvalue, String grpId, String hotelId) {
		return genCodeService.getSelectEnum(keysyset, keyvalue,grpId,hotelId);
	}

}
