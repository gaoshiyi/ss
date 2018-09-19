package com.sstc.hmis.notification.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sstc.hmis.model.data.MdataBeanModel;
import com.sstc.hmis.model.data.Result;
import com.sstc.hmis.model.data.page.PageResult;
import com.sstc.hmis.notification.data.Notice;
import com.sstc.hmis.notification.data.NoticeAccount;
import com.sstc.hmis.notification.data.NoticeBean;
import com.sstc.hmis.notification.data.NoticeReceipt;

/**
 * 消息通知
 * @author gkl
 *
 */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/systemMsgNotifyService")
public interface SystemMsgNotifyService {
	
	/**
     * 查询功能list
     * @param 消息通知列表
     * @return
     */
	@RequestMapping(value = "/selectNoticeList", method = RequestMethod.POST)
	List<Notice> selectNoticeList(@RequestBody NoticeBean noticeBean);
    
	/**
     * 查询功能list-分页
     * @param 消息通知列表
     * @return
     */
	@RequestMapping(value = "/selectPageNoticeList", method = RequestMethod.POST)
	public PageResult<Notice> selectPageNoticeList(@RequestParam("pageSize") int pageSize,
	        @RequestParam("pageIndex") int pageIndex, @RequestBody NoticeBean noticeBean);
	
	/**
	 * 查询功能list-分页 回执信息
	 * @param pageSize
	 * @param pageIndex
	 * @param noticeReceipt  回执信息
	 * @return 
	 */
	@RequestMapping(value = "/selectPageNoticeReceiptList", method = RequestMethod.POST)
	public PageResult<NoticeReceipt> selectPageNoticeReceiptList(@RequestParam("pageSize") int pageSize,
	        @RequestParam("pageIndex") int pageIndex, @RequestBody NoticeBean noticeBean);
	
	@RequestMapping(value = "/selectNoticeReceiptList", method = RequestMethod.POST)
	public List<NoticeReceipt> selectNoticeReceiptList(@RequestBody NoticeBean noticeBean);
	
	/**
	 * 编辑消息通知-保存
	 * @param noticeBean
	 * @return
	 */
	@RequestMapping(value = "/saveNoticeInfo", method = RequestMethod.POST)
	public Result saveNoticeInfo(@RequestBody NoticeBean notice);
	
	/**
	 * 查询当前时间需要通知的消息列表
	 * @param businessDate 营业日期
	 * @param businessTime 营业时间
	 * @return
	 */
	@RequestMapping(value = "/selectNoticeByBusinessTime", method = RequestMethod.POST)
	public Notice selectNoticeByCurrTime(@RequestParam("businessDate") Date businessDate, @RequestParam("businessTime")  Time businessTime);

	
	@RequestMapping(value = "/countNoticeByCurrTime", method = RequestMethod.POST)
	public int countNoticeByCurrTime(@RequestParam("nowDate") Date nowDate);
	
	@RequestMapping(value = "/selectNoticeByCurrTime", method = RequestMethod.POST)
	public Notice selectNoticeByCurrTime(@RequestParam("nowDate") Date nowDate);

	@RequestMapping(value = "/view", method = RequestMethod.POST)
	public Notice view(@RequestParam("id") String id);
	
	/**
	 * 保存确认接收信息
	 * @param noticeReceipt
	 * @return
	 */
	@RequestMapping(value = "/saveReceipt", method = RequestMethod.POST)
	public Result saveReceipt(@RequestBody NoticeReceipt noticeReceipt);
	
	/**
	 * 确认回执
	 * @param noticeAccount
	 * @return
	 */
	@RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
	public Result confirmReceipt(@RequestBody NoticeAccount noticeAccount);
	
	/**
     * 功能描述：获取页面下拉参数配置列表
     * 
     * @author Gkl
     */
	@RequestMapping(value = "/getParaMap", method = RequestMethod.POST)
    public List<MdataBeanModel> getParaMap(@RequestParam("keysyset") String keysyset, @RequestParam("keyvalue") String keyvalue);

	/**
     * 功能描述：删除消息通知
     * 
     * @author Gkl
     */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@RequestParam("idStr") String idStr);
	
	/**
     * 功能描述：初始化编辑数据
     * 
     * @author Gkl
     */
	@RequestMapping(value = "/initEditData", method = RequestMethod.POST)
	public Notice initEditData(@RequestParam("id") String id);
}
