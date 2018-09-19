/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.service;

import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.AbstractRegisteredService;

/**
  * <p> Title: CustomRegisteredService </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年8月4日 上午10:41:21
   */
public class CustomRegisteredService extends AbstractRegisteredService{

	
	private static final long serialVersionUID = 6121643846430444530L;
	
	private String serviceId;
	
	@Override
	public boolean matches(Service service) {
		boolean result = false;
		if(service != null && serviceId.equalsIgnoreCase(service.getId())){
			result = true; 
		}
		return result;
	}

	@Override
	public void setServiceId(String id) {
		this.serviceId = id;
	}

	@Override
	protected AbstractRegisteredService newInstance() {
		return new CustomRegisteredService();
	}

//	@Override
//	public boolean matches(String serviceId) {
//		if(StringUtils.isNotBlank(serviceId) 
//				&& this.serviceId.equalsIgnoreCase(serviceId)){
//			return true;
//		}
//		return false;
//	}

}
