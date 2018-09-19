/**
 * 
 */
package com.sstc.hmis.conf.cas;

import java.util.Date;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;

import com.sstc.hmis.permission.data.cas.PrincipalVo;

/**
  * <p> Title: AssertionImpl </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月8日 上午10:56:00
   */
public class AssertionImpl implements Assertion{

	
	private static final long serialVersionUID = -6242328182775006502L;

	
	private PrincipalVo principalVo;
	
	public void setPrincipalVo(PrincipalVo principalVo) {
		this.principalVo = principalVo;
	}
	
	public PrincipalVo getPrincipalVo() {
		return this.principalVo;
	}

	public AssertionImpl() {
	}
	
	public AssertionImpl(PrincipalVo principalVo) {
		this.principalVo = principalVo;
	}

	@Override
	public Date getValidFromDate() {
		return null;
	}

	@Override
	public Date getValidUntilDate() {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public AttributePrincipal getPrincipal() {
		return null;
	}

}
