package com.sstc.hmis.permission.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sstc.hmis.permission.data.StaffHotel;

/**
 * Title: 员工酒店
 * Description: 
 * @Company: SSTC
 * @author CKang
 * @date 2017年5月9日 下午1:47:31
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/staffHotelService")
public interface StaffHotelService {

	/**
	 * 通过用户id查询员工酒店信息
	 * @author CKang
	 * @date 2017年5月9日 下午1:48:34
	 * @param staffHotel
	 * @return
	 */
	@RequestMapping(value="/findStaffHotelByStaffId",method = RequestMethod.POST)
	public List<StaffHotel> findStaffHotelByStaffId(@RequestBody StaffHotel staffHotel);
}
