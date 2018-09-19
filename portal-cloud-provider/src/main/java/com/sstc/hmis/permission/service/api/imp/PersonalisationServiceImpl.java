package com.sstc.hmis.permission.service.api.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.permission.data.PersonalisationEntity;
import com.sstc.hmis.permission.dbaccess.dao.PermsTPersonalisationMapper;
import com.sstc.hmis.permission.dbaccess.data.PermsTPersonalisation;
import com.sstc.hmis.permission.service.PersonalisationService;
import com.sstc.hmis.util.HashUtils;
import com.sstc.hmis.util.bean.utils.BeanUtils;

/**
 * 个性化设置Service实现类
 * @author Qxiaoxiang
 *
 */
@RestController
public class PersonalisationServiceImpl implements PersonalisationService {
	
	@Autowired
	private PermsTPersonalisationMapper permsTPersonalisationMapper;
	

	@Override
	public List<PersonalisationEntity> getPersonalisationList(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		List<PermsTPersonalisation> permsTPersonalisationList = permsTPersonalisationMapper.getPersonalisationListByUserId(paramMap);
		if (permsTPersonalisationList != null && permsTPersonalisationList.size() > 0) {
			return BeanUtils.copyDbBeanList2ServiceBeanList(permsTPersonalisationList, PersonalisationEntity.class);
		}
		return new ArrayList<PersonalisationEntity>();
	}


	@Override
	public void newPersonalisation(String key, String value, String userId, String grpId, String hotelId) {
		// 编辑删除都调用，先删除再插入
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("key", key);
		paramMap.put("userId", userId);
		List<PermsTPersonalisation> permsTPersonalisationList = permsTPersonalisationMapper.getPersonalisationListByUserId(paramMap);
		if (permsTPersonalisationList != null && permsTPersonalisationList.size() > 0) {
			permsTPersonalisationMapper.deleteByPrimaryKey(permsTPersonalisationList.get(0).getClId());
		}
		PermsTPersonalisation permsTPersonalisation = new PermsTPersonalisation();
		permsTPersonalisation.setClId(HashUtils.uuidGenerator());
		permsTPersonalisation.setClKey(key);
		permsTPersonalisation.setClValue(value);
		permsTPersonalisation.setClUserId(userId);
		permsTPersonalisation.setClGrpId(grpId);
		permsTPersonalisation.setClHotelId(hotelId);
		permsTPersonalisation.setClCreateUser(userId);
		permsTPersonalisation.setClCreateTime(new Date());
		permsTPersonalisation.setClUpdateUser(userId);
		permsTPersonalisation.setClUpdateTime(new Date());
		
		permsTPersonalisationMapper.insertSelective(permsTPersonalisation);
	}

}
