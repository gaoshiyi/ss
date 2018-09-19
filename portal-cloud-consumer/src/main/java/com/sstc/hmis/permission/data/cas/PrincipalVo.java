/**
 * 
 */
package com.sstc.hmis.permission.data.cas;

import java.io.Serializable;
import java.security.Principal;

import org.apache.commons.lang3.StringUtils;

/**
  * <p> Title: Principal </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午7:55:48
   */
public class PrincipalVo implements Principal, Serializable{

	private static final long serialVersionUID = -6416053556379869636L;

	private String id;
	
	private String hotelId;
	
	private String locale;
	
	private String shiftCode;
	
	private String shiftId;
	

	public PrincipalVo(){}
	
	public PrincipalVo(String id){
		this.id = id;
	}
	
	public PrincipalVo(String id, String hotelId, String locale, String shiftCode, String shiftId) {
		this.id = id;
		this.hotelId = hotelId;
		this.locale = locale;
		this.shiftCode = shiftCode;
		this.shiftId = shiftId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
		PrincipalVo principal = (PrincipalVo) obj;
		return StringUtils.equalsIgnoreCase(id, principal.getId())&&
				StringUtils.equalsIgnoreCase(hotelId, principal.getHotelId());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return this.id;
	}



}
