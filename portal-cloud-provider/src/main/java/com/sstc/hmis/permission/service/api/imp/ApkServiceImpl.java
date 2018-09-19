package com.sstc.hmis.permission.service.api.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sstc.hmis.permission.data.ApkDetail;
import com.sstc.hmis.permission.dbaccess.dao.TApkVersionDetailMapper;
import com.sstc.hmis.permission.dbaccess.data.TApkVersionDetail;
import com.sstc.hmis.permission.service.ApkService;

/**
 * 小秘APKService实现类
 * @author Lrongrong
 *
 */
@RestController
public class ApkServiceImpl implements ApkService {
	
	@Autowired
	private TApkVersionDetailMapper tApkVersionDetailMapper;

	@Override
	public List<ApkDetail> getNewApkDetail(String grpId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("grpId", grpId);
		List<ApkDetail> apkDetailList = Lists.newArrayList();
		List<TApkVersionDetail> tApkVersionDetailList = tApkVersionDetailMapper.getNewApkDetail(paramMap);
//		if (tApkVersionDetail != null) {
//			apkDetail.setApkUrl(tApkVersionDetail.getClUrl());
//			apkDetail.setLog(tApkVersionDetail.getClLog());
//			apkDetail.setVersion(tApkVersionDetail.getClVersion());
//			apkDetail.setUpdateTime(tApkVersionDetail.getClCreateTime().getTime());
//			apkDetail.setUpdVersion(tApkVersionDetail.getClUpdVersion());
//		}
		if (tApkVersionDetailList != null && tApkVersionDetailList.size() > 0){
			for (TApkVersionDetail tApkVersionDetail : tApkVersionDetailList){
				ApkDetail apkDetail = new ApkDetail();
				apkDetail.setApkUrl(tApkVersionDetail.getClUrl());
				apkDetail.setLog(tApkVersionDetail.getClLog());
				apkDetail.setVersion(tApkVersionDetail.getClVersion());
				apkDetail.setUpdateTime(tApkVersionDetail.getClCreateTime().getTime());
				apkDetail.setUpdVersion(tApkVersionDetail.getClUpdVersion());
				apkDetailList.add(apkDetail);
			}
		}
		return apkDetailList;
	}
}
