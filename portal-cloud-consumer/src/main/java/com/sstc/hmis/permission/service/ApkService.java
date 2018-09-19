package com.sstc.hmis.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.permission.data.ApkDetail;

import java.util.List;

/**
 * 小秘版本Service接口
 * @author Lrongrong
 *
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/apkService")
public interface ApkService {

	/**
	 * 获取最新的apk信息
	 * @return 最新的apk文件地址
	 */
	@RequestMapping(value = "/getNewApkDetail" ,method = RequestMethod.POST)
	List<ApkDetail> getNewApkDetail(@RequestParam("grpId") String grpId);
}
