/**
 * 
 */
package com.sstc.hmis.permission.web;

import org.apache.commons.lang3.StringUtils;

import com.sstc.hmis.permission.data.GroupHotel;
import com.sstc.hmis.permission.data.Staff;
import com.sstc.hmis.util.fileupload.cos.UserInfoHolder;

/**
  * <p> Title: LoginInfoHolder </p>
  * <p> Description:  登录信息 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年3月8日 下午5:16:27
   */
public class LoginInfoHolder {
	
	private static final ThreadLocal<Staff> loginInfoHolder = new ThreadLocal<Staff>();
	
	private static final ThreadLocal<GroupHotel> hotelHolder = new ThreadLocal<>();
	
	private static final ThreadLocal<String> sessionHolder = new ThreadLocal<>();
	
	public static void setSessionId(final String sessionId){
		sessionHolder.set(sessionId);
	}
	
	public static String getSessionId(){
		return sessionHolder.get();
	}
	
	public static void setHotel(final GroupHotel hotel){
		UserInfoHolder.setHotelCode(hotel.getCode());
		hotelHolder.set(hotel);
	}
	
	public static void setLoginInfo(final Staff loginInfo) {
		loginInfoHolder.set(loginInfo);
	}
	/**
	 * 获取登录的帐号信息
	 * @return
	 */
	public static Staff getLoginInfo() {
		return loginInfoHolder.get();
	}
	
	public static GroupHotel getHotel(){
		return hotelHolder.get();
	}
	
	public static void clearUser() {
		loginInfoHolder.remove();
	}
	
	public static void clearHotel(){
		hotelHolder.remove();
	}
	
	/**
	 * 获取酒店编码
	 * @return
	 */
	public static String getHotelCode(){
		GroupHotel hotel = hotelHolder.get();
		if(hotel != null){
			return hotel.getCode();
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 获取登录帐号
	 * @return
	 */
	public static String getLoginAccount(){
		Staff staff = loginInfoHolder.get();
		if(staff != null){
			return staff.getAccount();
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 获取登录者姓名
	 * @return
	 */
	public static String getName(){
		Staff staff = loginInfoHolder.get();
		if(staff != null){
			String fn = staff.getFamilyName();
			String name = staff.getName();
			return (StringUtils.isBlank(fn)? "":fn) + (StringUtils.isBlank(name)? "":name);
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 获取登录酒店ID
	 * @return
	 */
	public static String getLoginHotelId(){
		Staff staff = loginInfoHolder.get();
		if(staff != null){
			return staff.getHotelId();
		}else{
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 获取登录集团ID
	 * @return
	 */
	public static String getLoginGrpId(){
		Staff staff = loginInfoHolder.get();
		if(staff != null){
			return staff.getGrpId();
		}else{
			return StringUtils.EMPTY;
		}
	}

}
