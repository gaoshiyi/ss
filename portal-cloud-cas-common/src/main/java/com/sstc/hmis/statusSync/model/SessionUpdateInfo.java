/**
 * 
 */
package com.sstc.hmis.statusSync.model;

import java.io.Serializable;

/**
  * <p> Title: SessionUpdateInfo </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午4:59:28
   */
public class SessionUpdateInfo implements Serializable{

	private static final long serialVersionUID = 5999083800609340605L;
	
	private String account;

	private String locale;
	
	private String hotelId;
	
	private String shiftCode;
	
	private String shiftId;
	
	private SessionUpdateInfo(Builder b){
		this.account = b.account;
		this.locale = b.locale;
		this.hotelId = b.hotelId;
		this.shiftCode = b.shiftCode;
		this.shiftId = b.shiftId;
	}
	
	public SessionUpdateInfo(){}
	
	public SessionUpdateInfo(String account, String locale, String hotelId, String shiftCode, String shiftId) {
		this.account = account;
		this.locale = locale;
		this.hotelId = hotelId;
		this.shiftCode = shiftCode;
		this.shiftId = shiftId;
	}


	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return the shiftCode
	 */
	public String getShiftCode() {
		return shiftCode;
	}

	/**
	 * @param shiftCode the shiftCode to set
	 */
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	/**
	 * @return the shiftId
	 */
	public String getShiftId() {
		return shiftId;
	}

	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	
	public static class Builder{
		
		private String account;

		private String locale;
		
		private String hotelId;
		
		private String shiftCode;
		
		private String shiftId;
		
		public Builder(String account){
			this.account = account;
		}
		
		public Builder account(String account){
			this.account = account;
			return this;
		}
		public Builder locale(String locale){
			this.locale = locale;
			return this;
		}
		public Builder hotelId(String hotelId){
			this.hotelId = hotelId;
			return this;
		}
		public Builder shiftCode(String shiftCode){
			this.shiftCode = shiftCode;
			return this;
		}
		public Builder shiftId(String shiftId){
			this.shiftId = shiftId;
			return this;
		}
		
		public SessionUpdateInfo build(){
			return new SessionUpdateInfo(this);
		}
		
	}
	
}
