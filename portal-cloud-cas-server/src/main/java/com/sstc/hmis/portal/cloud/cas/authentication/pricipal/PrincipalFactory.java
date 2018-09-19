/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.authentication.pricipal;

import java.util.Map;

import com.stormpath.sdk.lang.Assert;

/**
  * <p> Title: PrincipalFactory </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月6日 下午7:54:14
   */
public class PrincipalFactory implements org.apereo.cas.authentication.principal.PrincipalFactory{


	private static final long serialVersionUID = -8128190994749560887L;

	
	@Override
	public SstcPrincipal createPrincipal(String id) {
		return new SstcPrincipal(id);
	}

	@Override
	public SstcPrincipal createPrincipal(String id, Map<String, Object> attr) {
		Assert.notNull(id, "id不能为空");
		Assert.notNull(attr,"属性不能为空");
		return new SstcPrincipal(id, (String)attr.get("hotelId"), 
				(String)attr.get("locale"), (String)attr.get("shiftCode"), (String)attr.get("shiftId"));
	}

}
