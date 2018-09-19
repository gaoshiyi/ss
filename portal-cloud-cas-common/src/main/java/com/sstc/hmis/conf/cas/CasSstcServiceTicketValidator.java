/**
 * 
 */
package com.sstc.hmis.conf.cas;

import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.AbstractCasProtocolUrlBasedTicketValidator;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;

import com.sstc.hmis.permission.data.cas.PrincipalVo;

/**
  * <p> Title: CasSstcServiceTicketValidator </p>
  * <p> Description:  ST校验 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年9月8日 上午9:51:42
   */
public class CasSstcServiceTicketValidator extends AbstractCasProtocolUrlBasedTicketValidator{

    public CasSstcServiceTicketValidator(final String casServerUrlPrefix) {
        super(casServerUrlPrefix);
    }

    protected String getUrlSuffix() {
        return "serviceValidate";
    }

    @Deprecated
    protected final Assertion parseResponseFromServer(final String response) throws TicketValidationException {
        final String error = XmlUtils.getTextForElement(response,"authenticationFailure");

        if (CommonUtils.isNotBlank(error)) {
            throw new TicketValidationException(error);
        }
        final String account = XmlUtils.getTextForElement(response, "user");
        final String hotelId = XmlUtils.getTextForElement(response, "hotelId");
        final String locale = XmlUtils.getTextForElement(response, "locale");
        final String shiftId = XmlUtils.getTextForElement(response, "shiftId");
        final String shiftCode = XmlUtils.getTextForElement(response, "shiftCode");

        PrincipalVo principal = new PrincipalVo(account, hotelId, locale, shiftCode, shiftId);
        return new AssertionImpl(principal);
        
    }
	

}
