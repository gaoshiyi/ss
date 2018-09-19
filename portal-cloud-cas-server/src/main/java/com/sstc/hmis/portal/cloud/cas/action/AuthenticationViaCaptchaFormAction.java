package com.sstc.hmis.portal.cloud.cas.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.web.flow.AbstractAuthenticationAction;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.RequestContext;

import com.alibaba.fastjson.JSON;

/**
 * 表单验证Action，支持校验码
 * <a href=http://blog.csdn.net/zh350229319/article/details/50511284>参考网页</a>
 */
public class AuthenticationViaCaptchaFormAction extends AbstractAuthenticationAction{

    @Value("${cas.captcha.enable:true}")
    private boolean enableCaptcha;

	/** Authentication success result. */
    public static final String SUCCESS = "success";

    /** Authentication failure result. */
    public static final String AUTHENTICATION_FAILURE = "authenticationFailure";

    /** Error result. */
    public static final String ERROR = "error";

    /** Logger instance. **/
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public final String validateCaptcha(final RequestContext context,
            final Credential credential, final MessageContext messageContext)
            throws Exception {
        if(!enableCaptcha  ){
            // 禁用验证码，只在测试时禁用
            return SUCCESS;
        }

        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();
        String captcha = (String) session
                .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        // add by ydm at 2018-04-09 获取上一次的验证码信息 start
        Object lastCaptchaObj = session.getAttribute("lastCaptcha");
		String lastCaptcha = lastCaptchaObj == null ? "" : lastCaptchaObj.toString();
		// add by ydm at 2018-04-09 获取上一次的验证码信息 end
//        session.removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        logger.debug("Session中的验证码是:{}",captcha);
        if(StringUtils.isBlank(captcha)){
        	messageContext.addMessage(new MessageBuilder().error().code("captcha.timeout").build());
            return ERROR;
        }
        if(credential instanceof UsernamePasswordCaptchaCredential){
        	logger.debug("用户输入信息为:{}",JSON.toJSON(credential));
        }
        UsernamePasswordCaptchaCredential upc = (UsernamePasswordCaptchaCredential) credential;

        //检查用户名是否为空
        if (StringUtils.isBlank(upc.getUsername())) {
            messageContext.addMessage(new MessageBuilder().error().code("username.required").build());
            return ERROR;
        }
        //检查密码是否为空
        if (StringUtils.isBlank(upc.getPassword())) {
            messageContext.addMessage(new MessageBuilder().error().code("password.required").build());
            return ERROR;
        }

        String submitCaptcha = upc.getCaptcha();
        //检查验证码是否为空
        if (StringUtils.isBlank(submitCaptcha)) {
            messageContext.addMessage(new MessageBuilder().error().code("captcha.required").build());
            return ERROR;
        }
        // add by ydm at 2018-04-09 如果验证码是上一次的，这里也让他通过
        if(StringUtils.equalsIgnoreCase(submitCaptcha, captcha) || StringUtils.equalsIgnoreCase(submitCaptcha, lastCaptcha)){
            return SUCCESS;
        }
        messageContext.addMessage(new MessageBuilder().error().code("error.authentication.captcha.bad").build());
        return ERROR;
    }
    
    
    
}