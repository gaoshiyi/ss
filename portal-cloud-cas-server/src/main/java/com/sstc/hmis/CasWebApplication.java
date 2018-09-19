package com.sstc.hmis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.TicketRegistry;
import org.apereo.cas.util.CasBanner;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sstc.hmis.portal.cloud.cas.action.AuthenticationViaCaptchaFormAction;
import com.sstc.hmis.portal.cloud.cas.authentication.pricipal.PrincipalFactory;
import com.sstc.hmis.portal.cloud.cas.captcha.KaptchaServlet;
import com.sstc.hmis.portal.cloud.cas.handler.BizAuthenticationHandler;
import com.sstc.hmis.portal.cloud.cas.ticket.RedisTicketRegistry;

/**
 * This is {@link CasWebApplication}.
 *
 * @author Misagh Moayyed
 * @since 5.0.0
 */
@SuppressWarnings("deprecation")
@SpringBootApplication(
        exclude = {HibernateJpaAutoConfiguration.class,
                JerseyAutoConfiguration.class,
                GroovyTemplateAutoConfiguration.class,
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                MetricsDropwizardAutoConfiguration.class,
                VelocityAutoConfiguration.class})
@ComponentScan(basePackages = {"org.apereo.cas", "org.pac4j.springframework"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "org\\.pac4j\\.springframework\\.web\\.ApplicationLogoutController")})
@EnableAsync
@EnableConfigurationProperties(CasConfigurationProperties.class)
@EnableTransactionManagement
@EnableFeignClients
@EnableDiscoveryClient
public class CasWebApplication {
	
	@Resource
	private RedisTemplate<String, Ticket> redisTemplate;
	@Resource
	private StringRedisTemplate stringRedisTemplate;
    
	protected CasWebApplication() {
    }

    public static void main(final String[] args) {
    	
        new SpringApplicationBuilder(CasWebApplication.class)
                .banner(new CasBanner())
                .run(args);
    }
    
    @Bean
    BizAuthenticationHandler bizAuthenticationHandler(){
    	BizAuthenticationHandler authenticationHandler = new BizAuthenticationHandler();
    	authenticationHandler.setPrincipalFactory(principalFactory());
    	return authenticationHandler;
    }
    
    @Bean
    public Map<AuthenticationHandler,PrincipalResolver> authenticationHandlersResolvers() {
        final Map<AuthenticationHandler,PrincipalResolver> map = new HashMap<>();
        map.put(bizAuthenticationHandler(), null);
        return map;
    }
    
    @Bean(name = {"defaultPrincipalFactory", "principalFactory"})
    public PrincipalFactory principalFactory() {
        return new PrincipalFactory();
    }
    
    
    @Bean  
    public ServletRegistrationBean kaptchaServletRegistration() {  
    	Properties properties=new Properties();
        properties.setProperty("kaptcha.noise.color", "white");
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.size", "50");
        properties.setProperty("kaptcha.textproducer.char.string", "23456789abctmnwxvABCDEGHJKMNRST");
        KaptchaServlet kaptchaServlet = new KaptchaServlet();
        kaptchaServlet.setProps(properties);
        ServletRegistrationBean registration = new ServletRegistrationBean(kaptchaServlet);  
        registration.addUrlMappings("/captcha.jpg"); 
        return registration;   
    }
    
    @Bean
    public AuthenticationViaCaptchaFormAction authenticationViaCaptchaFormAction(){
    	return new AuthenticationViaCaptchaFormAction();
    }
    
    @Bean(name = {"redisTicketRegistry","ticketRegistry"})
    public TicketRegistry redisTicketRegistry(){
    	RedisTicketRegistry ticketRegistry = new RedisTicketRegistry();
    	ticketRegistry.setRedisTemplate(redisTemplate);
    	ticketRegistry.setStringRedisTemplate(stringRedisTemplate);
    	return ticketRegistry;
    }
}
