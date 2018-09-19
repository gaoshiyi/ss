package com.sstc.hmis.notification.service.api.imp;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sstc.hmis.mdata.service.api.GenCodeService;
import com.sstc.hmis.model.constants.GenCodeConstants;
import com.sstc.hmis.model.data.MdataBeanModel;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.notification.data.Notice;
import com.sstc.hmis.notification.data.NoticeAccount;
import com.sstc.hmis.notification.data.NoticeBean;
import com.sstc.hmis.notification.data.NoticeReceipt;
import com.sstc.hmis.notification.dbaccess.dao.PermsTNoticeAccountMapper;
import com.sstc.hmis.notification.dbaccess.dao.PermsTNoticeMapper;
import com.sstc.hmis.notification.dbaccess.dao.PermsTNoticeReceiptMapper;
import com.sstc.hmis.notification.dbaccess.data.PermsTNotice;
import com.sstc.hmis.notification.dbaccess.data.PermsTNoticeAccount;
import com.sstc.hmis.notification.dbaccess.data.PermsTNoticeAccountExample;
import com.sstc.hmis.notification.dbaccess.data.PermsTNoticeExample;
import com.sstc.hmis.notification.dbaccess.data.PermsTNoticeReceipt;
import com.sstc.hmis.notification.dbaccess.data.PermsTNoticeReceiptExample;
import com.sstc.hmis.notification.service.SystemMsgNotifyService;
import com.sstc.hmis.permission.web.LoginInfoHolder;
import com.sstc.hmis.util.DateUtils;
import com.sstc.hmis.util.HashUtils;

@RestController
public class SystemMsgNotifyServiceImp implements SystemMsgNotifyService {
	
	private final Log log = LogFactory.getLog(SystemMsgNotifyServiceImp.class);
	@Autowired
	private PermsTNoticeMapper permsTNoticeMapper ;
	@Autowired
	private PermsTNoticeReceiptMapper permsTNoticeReceiptMapper;
	@Autowired
	private PermsTNoticeAccountMapper permsTNoticeAccountMapper;
	@Autowired
    private GenCodeService genCodeService;

	private String getTypeVal(String code)
	{
		List<MdataBeanModel> msgNoticeLst = this.getParaMap(GenCodeConstants.PORTAL, GenCodeConstants.MSGNOTICE); // 消息通知
		if (CollectionUtils.isNotEmpty(msgNoticeLst))
		{
			for(MdataBeanModel mdataBeanModel : msgNoticeLst)
			{
				String mdataCode = mdataBeanModel.getCode();
				if (StringUtils.equals(mdataCode, code))
				{
					return mdataBeanModel.getName();
				}
			}
		}
		return "";
	}
	
	@Override
	public List<Notice> selectNoticeList(@RequestBody NoticeBean noticeBean) {
		
		Notice tmpNotice = this.getterNotice(noticeBean);
		PermsTNotice record = new PermsTNotice();
		try {
			com.sstc.hmis.util.bean.utils.BeanUtils.copyServiceBean2DbBean(tmpNotice, record);
			if (StringUtils.isNoneBlank(record.getClSummary()))
			{
				record.setClSummary("%" + record.getClSummary() + "%");	
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		DateFormat df  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<Notice> noticeLst = new ArrayList<Notice>();
		List<PermsTNotice> lst = permsTNoticeMapper.selectNoticeList(record);
		if (CollectionUtils.isNotEmpty(lst))
		{
			Notice notice = null;
			for (PermsTNotice permsTNotice : lst)
			{
				notice = new Notice();
				try {
					com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
					if (null != notice.getPublishTime())
					{
						notice.setPublishTime(df.parse(df.format(notice.getPublishTime())));
					}
					notice.setType(this.getTypeVal(notice.getType())); // 类型
					notice.setValidateDate(df.format(notice.getValidStarttime()) + "~" + df.format(notice.getValidEndtime()));
				}  catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("method selectNoticeList copy dbBean permsTNotice to serviceBean notice error! ");
				}
				noticeLst.add(notice);
			}
		}
		return noticeLst;
	}

	@Override
	public List<NoticeReceipt> selectNoticeReceiptList(@RequestBody NoticeBean noticeBean)
	{
		DateFormat df  = new SimpleDateFormat("yyyy/MM/dd");
		List<NoticeReceipt> noticeReceiptLst = new ArrayList<NoticeReceipt>();
		Date startDate = null;
		Date endDate = null;
		try {
			if (StringUtils.isNoneBlank(noticeBean.getValidStarttime()))
			{
				startDate = df.parse(noticeBean.getValidStarttime());
			}
			if (StringUtils.isNoneBlank(noticeBean.getValidEndtime()))
			{
				endDate = df.parse(noticeBean.getValidEndtime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("string to date parse exception");
		}
		List<PermsTNoticeReceipt> lst = permsTNoticeReceiptMapper.selectNoticeReceiptList(startDate, 
				endDate, LoginInfoHolder.getLoginHotelId(), noticeBean.getDetail());
		if (CollectionUtils.isNotEmpty(lst))
		{
			NoticeReceipt noticeReceipt = null;
			for (PermsTNoticeReceipt permsTNoticeReceipt : lst)
			{
				noticeReceipt = new NoticeReceipt();
				try {
					com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNoticeReceipt, noticeReceipt);
				}  catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("method selectNoticeReceiptList copy dbBean permsTNoticeReceipt to serviceBean noticeReceipt error! ");
				}
				noticeReceiptLst.add(noticeReceipt);
			}
		}
		return noticeReceiptLst;
	}
	
	@Override
	public PageResult<Notice> selectPageNoticeList(@RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, @RequestBody NoticeBean noticeBean) {
        
		PageResult<Notice> pageResult = new PageResult<Notice>();
        PageHelper.startPage(pageIndex + 1, pageSize);
		// List<Notice> totalList =  this.selectNoticeList(noticeBean);
		Notice tmpNotice = this.getterNotice(noticeBean);
		PermsTNotice record = new PermsTNotice();
		try {
			com.sstc.hmis.util.bean.utils.BeanUtils.copyServiceBean2DbBean(tmpNotice, record);
			if (StringUtils.isNoneBlank(record.getClSummary()))
			{
				record.setClSummary("%" + record.getClSummary() + "%");	
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DateFormat df  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<Notice> noticeLst = new ArrayList<Notice>();
		List<PermsTNotice> lst = permsTNoticeMapper.selectNoticeList(record);
		Page<PermsTNotice> page = (Page<PermsTNotice>)lst;
		if (CollectionUtils.isNotEmpty(page))
		{
			Notice notice = null;
			for (PermsTNotice permsTNotice : page.getResult())
			{
				notice = new Notice();
				try {
					com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
					if (null != notice.getPublishTime())
					{
						notice.setPublishTime(df.parse(df.format(notice.getPublishTime())));
					}
					notice.setType(this.getTypeVal(notice.getType())); // 类型
					notice.setValidateDate(df.format(notice.getValidStarttime()) + "~" + df.format(notice.getValidEndtime()));
				}  catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("method selectNoticeList copy dbBean permsTNotice to serviceBean notice error! ");
				}
				noticeLst.add(notice);
			}
		}
        pageResult.setResult(true);
        pageResult.setResults(page.getTotal());
        pageResult.setRows(noticeLst);
		return pageResult;
	}

	// 获取登录人姓名
	private String getLogginName()
	{
		String name = "";
		if (StringUtils.isNoneBlank(LoginInfoHolder.getLoginInfo().getFamilyName()))
		{
			name = LoginInfoHolder.getLoginInfo().getFamilyName() + LoginInfoHolder.getLoginInfo().getName();
		} else {
			name = LoginInfoHolder.getLoginInfo().getName();
		}
		return name;
	}
	
	@Override
	@Transactional(transactionManager = "transactionManager")
	public Result saveNoticeInfo(@RequestBody NoticeBean noticeBean) {
		PermsTNotice permsTNotice = null;
		if (null != noticeBean)
		{
			Notice notice = getterNotice(noticeBean);
			permsTNotice = new PermsTNotice();
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyServiceBean2DbBean(notice, permsTNotice);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method saveNoticeInfo copy serviceBean notice to dbBean permsTNotice error! ");
			}
			String name = this.getLogginName();
			if (StringUtils.equals("0", noticeBean.getOperType()))
			{ // 新增
				permsTNotice.setClId(HashUtils.uuidGenerator());
				permsTNotice.setClGrpId(LoginInfoHolder.getLoginGrpId());
				permsTNotice.setClHotelId(LoginInfoHolder.getLoginHotelId());
				permsTNotice.setClCreateUserId(LoginInfoHolder.getLoginAccount());
				permsTNotice.setClCreateTime(new Date());
				if (permsTNotice.getClPublishStatus() == 1)
				{
					permsTNotice.setClPublisherId(LoginInfoHolder.getLoginAccount());
					
					permsTNotice.setClPublisher(name);
					permsTNotice.setClPublishTime(new Date());
				} else {
					permsTNotice.setClPublisherId("");
					permsTNotice.setClPublisher("");
					permsTNotice.setClPublishTime(null);
				}
				permsTNoticeMapper.insertSelective(permsTNotice);
			} else if (StringUtils.equals("1", noticeBean.getOperType()))
			{ // 编辑
				if (permsTNotice.getClPublishStatus() == 1)
				{
					permsTNotice.setClPublishTime(new Date());
					permsTNotice.setClPublisherId(LoginInfoHolder.getLoginAccount());
					permsTNotice.setClPublisher(name);
				} else {
					permsTNotice.setClPublishTime(null);
					permsTNotice.setClPublisherId("");
					permsTNotice.setClPublisher("");
				}
				permsTNotice.setClUpdateUserId(LoginInfoHolder.getLoginAccount());
				permsTNotice.setClUpdateTime(new Date());
				permsTNoticeMapper.updateByPrimaryKeySelective(permsTNotice);
			}
		}
		return Result.SUCCESS;
	}

	@Override
	public Notice selectNoticeByCurrTime(@RequestParam("businessDate") Date businessDate, @RequestParam("businessTime") Time businessTime) {
		
		PermsTNoticeExample example = new PermsTNoticeExample();
		PermsTNoticeExample.Criteria criteria = example.createCriteria();
		criteria.andClValidStarttimeLessThanOrEqualTo(DateUtils.concatDateAndTime(businessDate, businessTime));
		criteria.andClValidEndtimeGreaterThanOrEqualTo(DateUtils.concatDateAndTime(businessDate, businessTime));
		criteria.andClPublishStatusEqualTo((short) 0); // 未发布
		criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
		List<PermsTNotice> PermsTNoticeList = permsTNoticeMapper.selectByExample(example);
		Notice notice = null;
		if (CollectionUtils.isNotEmpty(PermsTNoticeList))
		{
			notice = new Notice();
			PermsTNotice permsTNotice = PermsTNoticeList.get(0);
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method selectNoticeByCurrTime copy dbBean permsTNotice to serviceBean notice error! ");
			}
		}
		return notice;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public Result saveReceipt(@RequestBody NoticeReceipt noticeReceipt) {
		PermsTNoticeReceipt permsTNoticeReceipt = null;
		if (null != noticeReceipt)
		{
			permsTNoticeReceipt = new PermsTNoticeReceipt();
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyServiceBean2DbBean(noticeReceipt, permsTNoticeReceipt);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method saveReceipt copy serviceBean noticeReceipt to dbBean permsTNoticeReceipt error! ");
			}
			if (StringUtils.equals("0", noticeReceipt.getOperType()))
			{ // 新增 插入PERMS_T_NOTICE_RECEIPT
				permsTNoticeReceipt.setClId(HashUtils.uuidGenerator());
				permsTNoticeReceipt.setClCreateTime(new Date());
				permsTNoticeReceipt.setClCreateUserId(LoginInfoHolder.getLoginAccount());
				permsTNoticeReceipt.setClGrpId(LoginInfoHolder.getLoginGrpId());
				permsTNoticeReceipt.setClHotelId(LoginInfoHolder.getLoginHotelId());
				permsTNoticeReceiptMapper.insertSelective(permsTNoticeReceipt);
				
				// 插入 PERMS_T_NOTICE_ACCOUNT
				PermsTNoticeAccount permsTNoticeAccount = new PermsTNoticeAccount();
				permsTNoticeAccount.setClId(HashUtils.uuidGenerator());
				permsTNoticeAccount.setClNoticeId(permsTNoticeReceipt.getClNoticeId());
				permsTNoticeAccount.setClAccountId(permsTNoticeReceipt.getClAccountId());
				permsTNoticeAccount.setClStatus((short)1); // 已确认回执
				permsTNoticeAccount.setClCreateTime(new Date());
				permsTNoticeAccount.setClCreateUserId(LoginInfoHolder.getLoginAccount());
				permsTNoticeAccount.setClGrpId(LoginInfoHolder.getLoginGrpId());
				permsTNoticeAccount.setClHotelId(LoginInfoHolder.getLoginHotelId());
				permsTNoticeAccountMapper.insertSelective(permsTNoticeAccount);
			} else if (StringUtils.equals("1", noticeReceipt.getOperType()))
			{ // 编辑
				permsTNoticeReceipt.setClUpdateUserId(LoginInfoHolder.getLoginAccount());
				permsTNoticeReceipt.setClUpdateTime(new Date());
				permsTNoticeReceiptMapper.updateByPrimaryKeySelective(permsTNoticeReceipt);
			}
		}
		return Result.SUCCESS;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public Result confirmReceipt(NoticeAccount noticeAccount) {
		PermsTNoticeAccount permsTNoticeAccount = null;
		if (null != noticeAccount)
		{
			permsTNoticeAccount = new PermsTNoticeAccount();
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyServiceBean2DbBean(noticeAccount, permsTNoticeAccount);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method confirmReceipt copy serviceBean noticeAccount to dbBean permsTNoticeAccount error! ");
			}
			if (StringUtils.equals("0", noticeAccount.getOperType()))
			{ // 新增
				permsTNoticeAccount.setClId(HashUtils.uuidGenerator());
				permsTNoticeAccount.setClCreateTime(new Date());
				permsTNoticeAccount.setClCreateUserId(LoginInfoHolder.getLoginAccount());
				permsTNoticeAccount.setClGrpId(LoginInfoHolder.getLoginGrpId());
				permsTNoticeAccount.setClHotelId(LoginInfoHolder.getLoginHotelId());
				permsTNoticeAccountMapper.insertSelective(permsTNoticeAccount);
			} else if (StringUtils.equals("1", noticeAccount.getOperType()))
			{ // 编辑
				permsTNoticeAccount.setClUpdateTime(new Date());
				permsTNoticeAccount.setClUpdateUserId(LoginInfoHolder.getLoginAccount());
				permsTNoticeAccountMapper.updateByPrimaryKeySelective(permsTNoticeAccount);
			}
		}
		return Result.SUCCESS;
	}
	
	/**
     * 功能描述：获取页面下拉参数配置列表
     * 
     * @author Gkl
     * @return List<RsvRoomRatePayway>
     */
    @Override
    public List<MdataBeanModel> getParaMap(@RequestParam("keysyset") String keysyset, @RequestParam("keyvalue") String keyvalue)
    {

        Map<String, String> map = genCodeService.getSelectEnum(keysyset, keyvalue, LoginInfoHolder.getLoginGrpId(), LoginInfoHolder.getLoginHotelId());
        List<MdataBeanModel> list = new ArrayList<MdataBeanModel>();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            MdataBeanModel mdataBeanModel = new MdataBeanModel();
            String key = (String)iterator.next();
            mdataBeanModel.setId(key.split("[+]")[0]);
            mdataBeanModel.setCode(key.split("[+]")[1]);
            mdataBeanModel.setName(map.get(key));
            list.add(mdataBeanModel);
        }
        return list;
    }
    
    private NoticeReceipt gettNoticeReceipt(NoticeBean noticeBean)
    {
    	NoticeReceipt noticeReceipt = new NoticeReceipt();
    	if (StringUtils.isNoneBlank(noticeBean.getDetail()))
    	{
    		noticeReceipt.setPhone(noticeBean.getDetail());
    	}
    	DateFormat df  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	try {
			if (StringUtils.isNoneBlank(noticeBean.getValidStarttime()))
			{
				noticeReceipt.setCreateTime(df.parse(noticeBean.getValidStarttime()));
			}
			if (StringUtils.isNoneBlank(noticeBean.getValidEndtime()))
			{
				noticeReceipt.setCreateTime(df.parse(noticeBean.getValidEndtime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("string to date parse exception");
		}
    	return noticeReceipt;
    }
    
    private Notice getterNotice(NoticeBean noticeBean)
	{
		Notice notice = new Notice();
		notice.setId(noticeBean.getId());
		notice.setDetail(noticeBean.getDetail());
		notice.setOperType(noticeBean.getOperType());
		notice.setPublisher(noticeBean.getPublisher());
		notice.setPublisherId(noticeBean.getPublisherId());
		notice.setPublishStatus(noticeBean.getPublishStatus());
		notice.setSummary(noticeBean.getSummary());
		notice.setTitle(noticeBean.getTitle());
		notice.setType(noticeBean.getType());
		DateFormat df  = null;
		if (StringUtils.equals("0", noticeBean.getOperType())
				|| StringUtils.equals("1", noticeBean.getOperType()))
		{
			df = new SimpleDateFormat(DateUtils.DATE_FMT_DATE_3);
		} else {
			df = new SimpleDateFormat(DateUtils.DATE_FMT_DATE_2);
		}
		
		try {
			if (StringUtils.isNoneBlank(noticeBean.getValidStarttime()))
			{
				notice.setValidStarttime(df.parse(noticeBean.getValidStarttime()));
			}
			if (StringUtils.isNoneBlank(noticeBean.getValidEndtime()))
			{
				notice.setValidEndtime(df.parse(noticeBean.getValidEndtime()));
			}
			if (StringUtils.isNoneBlank(noticeBean.getPublishTime()))
			{
				notice.setPublishTime(df.parse(noticeBean.getPublishTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("string to date parse exception");
		}
		return notice;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public int delete(@RequestParam("idStr") String idStr) {
		int count = 0;
		if (StringUtils.isNoneBlank(idStr))
		{
			String[] idArray = null;
			idArray = idStr.split(",");
			// 删除PERMS.PERMS_T_NOTICE_ACCOUNT
			PermsTNoticeAccountExample accountExample = new PermsTNoticeAccountExample();
			PermsTNoticeAccountExample.Criteria accCriteria = accountExample.createCriteria();
			accCriteria.andClNoticeIdIn(Arrays.asList(idArray));
			accCriteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
			permsTNoticeAccountMapper.deleteByExample(accountExample);
			
			// 删除PERMS.PERMS_T_NOTICE_RECEIPT
			PermsTNoticeReceiptExample receiptExample = new PermsTNoticeReceiptExample();
			PermsTNoticeReceiptExample.Criteria receiptCriteria = receiptExample.createCriteria();
			receiptCriteria.andClNoticeIdIn(Arrays.asList(idArray));
			receiptCriteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
			permsTNoticeReceiptMapper.deleteByExample(receiptExample);
						
			// 删除PERMS.PERMS_T_NOTICE
			PermsTNoticeExample example = new PermsTNoticeExample();
			PermsTNoticeExample.Criteria criteria = example.createCriteria();
			criteria.andClIdIn(Arrays.asList(idArray));
			criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
			count = permsTNoticeMapper.deleteByExample(example);
		}
		return count;
	}
	
	@Override
	public Notice initEditData(@RequestParam("id") String id)
	{
		Notice notice = new Notice();
		PermsTNotice permsTNotice = permsTNoticeMapper.selectByPrimaryKey(id);
		try {
			com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("method initEditData copy dbBean permsTNotice to serviceBean notice error! ");
		}
		return notice;
	}

	@Override
	public int countNoticeByCurrTime(@RequestParam("nowDate") Date nowDate)
	{
		PermsTNoticeExample example = new PermsTNoticeExample();
		PermsTNoticeExample.Criteria criteria = example.createCriteria();
		criteria.andClValidStarttimeLessThanOrEqualTo(nowDate);
		criteria.andClValidEndtimeGreaterThanOrEqualTo(nowDate);
		criteria.andClPublishStatusEqualTo((short) 1); // 未发布
		criteria.andClHotelIdEqualTo(LoginInfoHolder.getLoginHotelId());
		int count = permsTNoticeMapper.countByExample(example);
		return count;
	}
	
	@Override
	public Notice selectNoticeByCurrTime(@RequestParam("nowDate") Date nowDate) {
		
		List<PermsTNotice> PermsTNoticeList = permsTNoticeMapper.selectNoticeByCurrTime(nowDate, (short) 1, LoginInfoHolder.getLoginHotelId());
		Notice notice = null;
		if (CollectionUtils.isNotEmpty(PermsTNoticeList))
		{
			notice = new Notice();
			PermsTNotice permsTNotice = PermsTNoticeList.get(0);
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
				notice.setType(this.getTypeVal(notice.getType())); // 类型
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method selectNoticeByCurrTime copy dbBean permsTNotice to serviceBean notice error! ");
			}
		}
		return notice;
	}

	@Override
	public PageResult<NoticeReceipt> selectPageNoticeReceiptList(int pageSize, int pageIndex, @RequestBody NoticeBean noticeBean) {
		PageResult<NoticeReceipt> pageResult = new PageResult<NoticeReceipt>();
        PageHelper.startPage(pageIndex + 1, pageSize);
        // List<NoticeReceipt> totalList =  this.selectNoticeReceiptList(noticeBean);
        DateFormat df  = new SimpleDateFormat("yyyy/MM/dd");
		List<NoticeReceipt> noticeReceiptLst = new ArrayList<NoticeReceipt>();
		Date startDate = null;
		Date endDate = null;
		try {
			if (StringUtils.isNoneBlank(noticeBean.getValidStarttime()))
			{
				startDate = df.parse(noticeBean.getValidStarttime());
			}
			if (StringUtils.isNoneBlank(noticeBean.getValidEndtime()))
			{
				endDate = df.parse(noticeBean.getValidEndtime());
			}
			if (StringUtils.isNoneBlank(noticeBean.getDetail()))
			{
				noticeBean.setDetail("%" + noticeBean.getDetail() + "%");	
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("string to date parse exception");
		}
		List<PermsTNoticeReceipt> lst = permsTNoticeReceiptMapper.selectNoticeReceiptList(startDate, 
				endDate, LoginInfoHolder.getLoginHotelId(), noticeBean.getDetail());
		Page<PermsTNoticeReceipt> page = (Page<PermsTNoticeReceipt>)lst;
		if (CollectionUtils.isNotEmpty(page))
		{
			NoticeReceipt noticeReceipt = null;
			for (PermsTNoticeReceipt permsTNoticeReceipt : page.getResult())
			{
				noticeReceipt = new NoticeReceipt();
				try {
					com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNoticeReceipt, noticeReceipt);
				}  catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("method selectNoticeReceiptList copy dbBean permsTNoticeReceipt to serviceBean noticeReceipt error! ");
				}
				noticeReceiptLst.add(noticeReceipt);
			}
		}
        
        
        pageResult.setResult(true);
        pageResult.setResults(page.getTotal());
        pageResult.setRows(noticeReceiptLst);
		return pageResult;
	}
	
	@Override
	public Notice view(@RequestParam("id") String id)
	{
		PermsTNotice permsTNotice = permsTNoticeMapper.selectByPrimaryKey(id);
		Notice notice = null;
		if (null != permsTNotice)
		{
			notice = new Notice();
			try {
				com.sstc.hmis.util.bean.utils.BeanUtils.copyDbBean2ServiceBean(permsTNotice, notice);
				notice.setType(this.getTypeVal(notice.getType())); // 类型
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("method view copy dbBean permsTNotice to serviceBean notice error! ");
			}
		}
		return notice;
	}
}
