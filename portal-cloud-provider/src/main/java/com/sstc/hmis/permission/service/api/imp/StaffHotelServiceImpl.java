package com.sstc.hmis.permission.service.api.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.model.constants.Constants;
import com.sstc.hmis.permission.data.StaffHotel;
import com.sstc.hmis.permission.dbaccess.dao.PermsTStaffHotelMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotel;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample;
import com.sstc.hmis.permission.dbaccess.data.PermsTStaffHotelExample.Criteria;
import com.sstc.hmis.permission.service.StaffHotelService;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
 * Title: 员工酒店Service
 * Description: 
 * @Company: SSTC
 * @author CKang
 * @date 2017年5月9日 下午1:50:25
 */
@RestController
public class StaffHotelServiceImpl extends BaseServiceImpl<StaffHotel> implements StaffHotelService {
	
	@Autowired
	private PermsTStaffHotelMapper permsTStaffHotelMapper;

	@Override
	public List<StaffHotel> findStaffHotelByStaffId(@RequestBody StaffHotel staffHotel) {
		PermsTStaffHotelExample example = new PermsTStaffHotelExample();
		Criteria criteria = example.createCriteria();
		criteria.andClStaffIdEqualTo(staffHotel.getStaffId());
		criteria.andClStatusEqualTo(Constants.STATUS_NORMAL);
		List<PermsTStaffHotel> permsTStaffHotelList = permsTStaffHotelMapper.selectByExample(example);
		return BeanUtils.copyDbBeanList2ServiceBeanList(permsTStaffHotelList, StaffHotel.class);
	}


}
