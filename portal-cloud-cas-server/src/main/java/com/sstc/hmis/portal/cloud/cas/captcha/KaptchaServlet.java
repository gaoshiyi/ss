/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

/**
 * <p> Title: KaptchaServlet </p>
 * <p> Description: 验证码生成器 </p>
 * <p> Company: SSTC </p>
 * 
 * @author Qxiaoxiang
 * @date 2017年10月17日 下午4:42:59
 */
public class KaptchaServlet extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = -5232248248144356113L;
	
	private static final Logger log = LoggerFactory.getLogger(KaptchaServlet.class);

	private Properties props = new Properties();
	
	public void setProps(Properties props) {
		this.props = props;
	}

	private Producer kaptchaProducer = null;

	private String sessionKeyValue = null;

	private String sessionKeyDateValue = null;

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);

		// Switch off disk based caching.
//		ImageIO.setUseCache(false);

		Enumeration<?> initParams = conf.getInitParameterNames();
		while (initParams.hasMoreElements()) {
			String key = (String) initParams.nextElement();
			String value = conf.getInitParameter(key);
			this.props.put(key, value);
		}

		Config config = new Config(this.props);
		this.kaptchaProducer = config.getProducerImpl();
		this.sessionKeyValue = config.getSessionKey();
		this.sessionKeyDateValue = config.getSessionDate();
	}

	/** */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set standard HTTP/1.1 no-cache headers.
		resp.setHeader("Cache-Control", "no-store, no-cache");

		// return a jpeg
		resp.setContentType("image/jpeg");

		// create the text for the image
		String capText = this.kaptchaProducer.createText();
		log.debug("请求生成的验证码是{}",capText);

		// create the image with the text
		BufferedImage bi = this.kaptchaProducer.createImage(capText);

		// fixes issue #69: set the attributes after we write the image in case
		// the image writing fails.
		
		// add by ydm at 2018-04-09 记录上一次的验证码信息 start
		Object lastCaptchaObj = req.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String lastCaptcha = lastCaptchaObj == null ? "" : lastCaptchaObj.toString();
		req.getSession().setAttribute("lastCaptcha", lastCaptcha);
		// add by ydm at 2018-04-09 记录上一次的验证码信息 end
		
		// store the text in the session
		req.getSession().setAttribute(this.sessionKeyValue, capText);
		
		// store the date in the session so that it can be compared
		// against to make sure someone hasn't taken too long to enter
		// their kaptcha
		req.getSession().setAttribute(this.sessionKeyDateValue, new Date());
		
		ServletOutputStream out = resp.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);

	}

}
