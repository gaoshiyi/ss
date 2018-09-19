package com.sstc.hmis.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.permission.data.PersonalisationEntity;

/**
 * 个性化设置Service
 * @author Qxiaoxiang
 *
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/personalisationService")
public interface PersonalisationService {

	/**
	 * 查询个性化设置列表
	 */
	@RequestMapping(value = "/getPersonalisationList" , method = RequestMethod.POST)
	List<PersonalisationEntity> getPersonalisationList(@RequestParam("userId") String userId);
	
	/**
	 * 新建个性化设置
	 */
	@RequestMapping(value = "/newPersonalisation", method = RequestMethod.POST)
	void newPersonalisation(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("userId") String userId,
			@RequestParam("grpId") String grpId, @RequestParam("hotelId") String hotelId);
}
