package com.sstc.hmis.portal.cloud.cas.authentication.pricipal;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
  * <p> Title: Principal </p>
  * <p> Description:  状态同步对象 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午7:55:48
   */
public class SstcPrincipal implements org.apereo.cas.authentication.principal.Principal{

	private static final long serialVersionUID = -2835697540928214802L;

	private String id;
	
	private String hotelId;
	
	private String locale;
	
	private String shiftCode;
	
	private String shiftId;
	
	private String shift;
	
	

	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	public SstcPrincipal(){}
	
	public SstcPrincipal(String id){
		this.id = id;
	}
	
	public SstcPrincipal(String id, String hotelId, String locale, String shiftCode, String shiftId) {
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
	public String toString() {
		return this.id;
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
		SstcPrincipal principal = (SstcPrincipal) obj;
		return StringUtils.equalsIgnoreCase(id, principal.getId())&&
				StringUtils.equalsIgnoreCase(hotelId, principal.getHotelId());
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public Map<String, Object> getAttributes() {
		final Map<String, Object> attrs = Maps.newTreeMap(String.CASE_INSENSITIVE_ORDER);
        attrs.put("hotelId", this.hotelId);
        attrs.put("locale", this.locale);
        attrs.put("shiftCode", this.shiftCode);
        attrs.put("shiftId", this.shiftId);
		return attrs;
	}


}
