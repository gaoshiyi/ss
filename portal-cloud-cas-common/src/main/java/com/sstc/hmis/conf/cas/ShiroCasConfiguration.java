/**
 * 
 */
package com.sstc.hmis.conf.cas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Cluster;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.sstc.hmis.conf.cache.RedisProperties;
import com.sstc.hmis.conf.cas.constants.ShiroCasProperties;
import com.sstc.hmis.conf.cas.session.CasLogoutFilter;
import com.sstc.hmis.conf.cas.session.CasSessionListener;
import com.sstc.hmis.filter.GlobalRedirectFilter;
import com.sstc.hmis.filter.NightAuditFilter;
import com.sstc.hmis.filter.SstcFormAuthenticationFilter;
import com.sstc.hmis.permission.service.MsgQueueService;
import com.sstc.hmis.shiro.RedisCacheManager;
import com.sstc.hmis.shiro.RedisManager;
import com.sstc.hmis.shiro.RedisSessionDao;
import com.sstc.hmis.statusSync.filter.DataSyncFilter;

import redis.clients.jedis.JedisPoolConfig;



/**
 * <p> Title: ShiroCasConfiguration </p>
 * <p> Description: 单点登录、shiro权限管理Bean实例化注入到容器 </p>
 * <p> Company: SSTC </p>
 * @author Qxiaoxiang
 * @date 2017年6月23日 上午11:36:05
 */
@SuppressWarnings("deprecation")
@Configuration
@ConditionalOnProperty(name = "feign.service.type", havingValue = "consumer")
@EnableConfigurationProperties(value = {ShiroCasProperties.class})
public class ShiroCasConfiguration {
	
//	private static final long GLOBEL_SESSION_TIMEOUT = 60000;//7200000;	
	
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}
	
	@Bean
	public MyCasRealm myShiroCasRealm(ShiroCasProperties shiroCasProperties,RedisCacheManager<?, ?> redisCacheManager)  {
		MyCasRealm realm = new MyCasRealm();
		realm.setCachingEnabled(true);
		realm.setAuthenticationCachingEnabled(true);
		realm.setAuthenticationCacheName("authenticationCache");
		realm.setAuthorizationCachingEnabled(true);
		realm.setAuthorizationCacheName("authorizationCache");
		realm.setCasServerUrlPrefix(shiroCasProperties.getSsoServerUrlPrefix());
		realm.setCasService(shiroCasProperties.getCasService());
		realm.setCacheManager(redisCacheManager);
		return realm;
	}
	
	@Bean
	public SessionIdGenerator sessionIdGenerator(){
		JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
		return sessionIdGenerator;
	}
	
	@Bean
	public Cookie sessionIdCookie(){
		SimpleCookie cookie = new SimpleCookie("sid");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		return cookie;
	}
	@Bean
	public Cookie rememberMeCookie(){
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(2592000);//30天
		return cookie;
	}
	
//	@Bean
//	public RememberMeManager rememberMeManager(){
//		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
//		rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
//		rememberMeManager.setCookie(rememberMeCookie());
//		return rememberMeManager;
//	}
	
	@Bean
	public SessionDAO sessionDao(RedisManager redisManager,RedisTemplate<String, String> redisTemplate){
		RedisSessionDao sessionDAO = new RedisSessionDao();
		sessionDAO.setRedisManager(redisManager);
		sessionDAO.setRedisTemplate(redisTemplate);
		sessionDAO.setSessionIdGenerator(sessionIdGenerator());
		return sessionDAO;
	}
	
//	@Bean
//	public SessionValidationScheduler sessionValidationScheduler(){
//		QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
//		sessionValidationScheduler.setSessionManager(sessionManager());
//		sessionValidationScheduler.setSessionValidationInterval(sessionValidationInterval);
//		return sessionValidationScheduler;
//	}
	
	@Bean
	public DefaultWebSessionManager sessionManager(SessionDAO sessionDAO,@Value("${server.session.timeout:7200}") long session_timeout) {
		
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(session_timeout*1000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
//		sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		sessionManager.getSessionListeners().add(new CasSessionListener());
		return sessionManager;
	}

	@Bean
	public SubjectFactory subjectFactory(){
		return new CasSubjectFactory();
	}
	
	
	@Bean
	public SecurityManager securityManager(MyCasRealm myShiroCasRealm,RedisCacheManager<?, ?> cacheManager,DefaultWebSessionManager sessionManager) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(myShiroCasRealm);
		dwsm.setSessionManager(sessionManager);
		// <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
		dwsm.setCacheManager(cacheManager);
//		dwsm.setRememberMeManager(rememberMeManager());
		// 指定 SubjectFactory
		dwsm.setSubjectFactory(subjectFactory());
		return dwsm;
	}

	@Bean
	public FactoryBean<Object> methodInvokingFactoryBean(SecurityManager securityManager) {
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		factoryBean.setArguments(new Object[]{securityManager});
		return factoryBean;
	}
	
	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	public CasFilter casFilter(ShiroCasProperties shiroCasProperties) {
		CasFilter casFilter = new CasFilter(){
			@Override
			protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
				HttpServletRequest httpReq = (HttpServletRequest)request;
				HttpSession session = httpReq.getSession();
				Object url = session.getAttribute("OLD_URL");
				String oldUrl = this.getSuccessUrl();
				if(url != null && StringUtils.isNotBlank(oldUrl.toString())){
					oldUrl = url.toString();
				}
				WebUtils.redirectToSavedRequest(request, response, oldUrl);
			}
		};
//		casFilter.setName("casFilter");
//		casFilter.setEnabled(true);
		// 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo
		// 方法向CasServer验证tiket
		casFilter.setFailureUrl(shiroCasProperties.getLoginUrl());// 我们选择认证失败后再打开登录页面
		return casFilter;
	}
	
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter(ShiroCasProperties shiroCasProperties,
			org.springframework.boot.autoconfigure.data.redis.RedisProperties properties,
			ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider,
			@Value("${spring.redis.pmsdb:-1}")int pmsdb) {
		SstcFormAuthenticationFilter ret = new SstcFormAuthenticationFilter();
		ret.setRedisTemplate(pmsRedisTemplate(properties,clusterConfigurationProvider,pmsdb));
		ret.setLoginUrl(shiroCasProperties.getLoginUrl());
		return ret;
	}
	
	@Bean
	public DataSyncFilter dataSyncFilter(RedisManager redisManager, DefaultWebSessionManager sessionManager,StringRedisTemplate stringRedisTemplate){
		DataSyncFilter syncFilter = new DataSyncFilter();
//		syncFilter.setSessionManager(sessionManager);
		syncFilter.setStringRedisTemplate(stringRedisTemplate);
		return syncFilter;
	}
	
	@Bean
	public NightAuditFilter nightAuditFilter(RedisTemplate<String, String> redisTemplate){
		NightAuditFilter nightAuditFilter = new NightAuditFilter();
		nightAuditFilter.setRedisTemplate(redisTemplate);
		return nightAuditFilter;
	}
	
	@Bean
	public GlobalRedirectFilter globalRedirectFilter(){
		return new GlobalRedirectFilter();
	}
	
	
	@Bean
	@ConditionalOnProperty(name = "spring.shiro.cas.logoutUrl")
	public LogoutFilter logoutFilter(ShiroCasProperties shiroCasProperties,MsgQueueService msgQueueService,
			DefaultWebSessionManager sessionManager,StringRedisTemplate stringRedisTemplate){
		CasLogoutFilter logoutFilter = new CasLogoutFilter();
		logoutFilter.setRedisTemplate(stringRedisTemplate);
		logoutFilter.setSessionManager(sessionManager);
		logoutFilter.setRedirectUrl(shiroCasProperties.getLogoutUrl());
		return logoutFilter;
	}
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroCasProperties shiroCasProperties,MsgQueueService msgQueueService,
			SecurityManager securityManager,DefaultWebSessionManager sessionManager,RedisManager redisManager,StringRedisTemplate stringRedisTemplate,
			org.springframework.boot.autoconfigure.data.redis.RedisProperties properties,
			ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider,
			@Value("${spring.redis.pmsdb:-1}") int pmsdb) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new MyShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl(shiroCasProperties.getLoginUrl());
		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl(shiroCasProperties.getAuthSuccessUrl());
		shiroFilterFactoryBean.setUnauthorizedUrl(shiroCasProperties.getUnauthorizedUrl());
		// 添加casFilter到shiroFilter中
		Map<String, Filter> filters = new HashMap<>();
		filters.put("sync", dataSyncFilter(redisManager,sessionManager,stringRedisTemplate));
		filters.put("redirect", globalRedirectFilter());
		filters.put("na", nightAuditFilter(stringRedisTemplate));
		filters.put("cas", casFilter(shiroCasProperties));
//		filters.put("authc", formAuthenticationFilter(shiroCasProperties,properties,clusterConfigurationProvider,pmsdb));
		filters.put("logout", logoutFilter(shiroCasProperties,msgQueueService,sessionManager,stringRedisTemplate));
		shiroFilterFactoryBean.setFilters(filters);
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(new ChainDefinitionSectionMetaSource().getObject());
		return shiroFilterFactoryBean;
	}
	

	@Bean
	public RedisManager redisManager(RedisConnectionFactory redisConnectionFactory, @Value("${server.session.timeout:7200}") int sessionTimeout) {
		RedisManager redisManager = new RedisManager(redisConnectionFactory,sessionTimeout);
		return redisManager;
	}
	
	@Bean
	public RedisCacheManager<?, ?> cacheManager(RedisManager redisManager){
		@SuppressWarnings("rawtypes")
		RedisCacheManager<?, ?> redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager);
		return redisCacheManager;
	}
	

	/**
	 * 注册DelegatingFilterProxy（Shiro）替代web.xml中的注册
	 * @param dispatcherServlet
	 * @return
	 */
	@Bean
	@Order(Integer.MIN_VALUE)
	public FilterRegistrationBean shiroFilterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.setAsyncSupported(true);
		filterRegistration.addUrlPatterns("/*");
		
		return filterRegistration;
	}
	
	@Bean
	@Order(Integer.MIN_VALUE + 1)
	public FilterRegistrationBean signOutFilterRegistrationBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new SingleSignOutFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	@Bean
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListenerRegistrationBean() {
		return new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>(
				new SingleSignOutHttpSessionListener());
	}


	@Bean(name="pmsRedisTemplate")
	public RedisTemplate<Object, Object> pmsRedisTemplate(org.springframework.boot.autoconfigure.data.redis.RedisProperties properties,
				ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider,
				@Value("${spring.redis.pmsdb:-1}") int pmsdb){
		JedisConnectionFactory factory = createJedisConnectionFactory(properties,clusterConfigurationProvider.getIfAvailable());
		factory = applyProperties(factory,properties);
		if(pmsdb<0) {
			factory.setDatabase(properties.getDatabase());
		}
		else {
			factory.setDatabase(pmsdb);
		}
		factory.afterPropertiesSet();
		RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
		template.setConnectionFactory(factory);
		return template;
	}
	
	private final JedisConnectionFactory applyProperties(
			JedisConnectionFactory factory,org.springframework.boot.autoconfigure.data.redis.RedisProperties properties) {
		factory.setHostName(properties.getHost());
		factory.setPort(properties.getPort());
		if (properties.getPassword() != null) {
			factory.setPassword(properties.getPassword());
		}
		factory.setDatabase(properties.getDatabase());
		if (properties.getTimeout() > 0) {
			factory.setTimeout(properties.getTimeout());
		}
		return factory;
	}
	
	private JedisConnectionFactory createJedisConnectionFactory(org.springframework.boot.autoconfigure.data.redis.RedisProperties properties,RedisClusterConfiguration clusterConfiguration) {
		JedisPoolConfig poolConfig = properties.getPool() != null
				? jedisPoolConfig(properties) : new JedisPoolConfig();

		if (getSentinelConfig(properties) != null) {
			return new JedisConnectionFactory(getSentinelConfig(properties), poolConfig);
		}
		if (getClusterConfiguration(properties,clusterConfiguration) != null) {
			return new JedisConnectionFactory(getClusterConfiguration(properties,clusterConfiguration), poolConfig);
		}
		return new JedisConnectionFactory(poolConfig);
	}

	private JedisPoolConfig jedisPoolConfig(org.springframework.boot.autoconfigure.data.redis.RedisProperties properties) {
		JedisPoolConfig config = new JedisPoolConfig();
		org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool props = properties.getPool();
		config.setMaxTotal(props.getMaxActive());
		config.setMaxIdle(props.getMaxIdle());
		config.setMinIdle(props.getMinIdle());
		config.setMaxWaitMillis(props.getMaxWait());
		return config;
	}
	
	
	private final RedisSentinelConfiguration getSentinelConfig(org.springframework.boot.autoconfigure.data.redis.RedisProperties properties) {
		
		Sentinel sentinelProperties = properties.getSentinel();
		if (sentinelProperties != null) {
			RedisSentinelConfiguration config = new RedisSentinelConfiguration();
			config.master(sentinelProperties.getMaster());
			config.setSentinels(createSentinels(sentinelProperties));
			return config;
		}
		return null;
	}

	/**
	 * Create a {@link RedisClusterConfiguration} if necessary.
	 * @return {@literal null} if no cluster settings are set.
	 */
	private final RedisClusterConfiguration getClusterConfiguration(org.springframework.boot.autoconfigure.data.redis.RedisProperties properties,
			RedisClusterConfiguration clusterConfiguration) {
		if (clusterConfiguration != null) {
			return clusterConfiguration;
		}
		if (properties.getCluster() == null) {
			return null;
		}
		Cluster clusterProperties = properties.getCluster();
		RedisClusterConfiguration config = new RedisClusterConfiguration(
				clusterProperties.getNodes());

		if (clusterProperties.getMaxRedirects() != null) {
			config.setMaxRedirects(clusterProperties.getMaxRedirects());
		}
		return config;
	}

	
	private List<RedisNode> createSentinels(Sentinel sentinel) {
		List<RedisNode> nodes = new ArrayList<RedisNode>();
		for (String node : org.springframework.util.StringUtils
				.commaDelimitedListToStringArray(sentinel.getNodes())) {
			try {
				String[] parts = StringUtils.split(node, ":");
				Assert.state(parts.length == 2, "Must be defined as 'host:port'");
				nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
			}
			catch (RuntimeException ex) {
				throw new IllegalStateException(
						"Invalid redis sentinel " + "property '" + node + "'", ex);
			}
		}
		return nodes;
	}

	/**
	 * springboot-admin使用到的endpoint需要在Filter里排除掉
	 * @return
	 */
	public static final String[] EXCULDE_URL = new String[]{"/autoconfig","/refresh","/heapdump","/logfile",
			"/configprops","/beans","/dump","/env", "/env/**","/health","/jolokia/**",
			"/info","/mappings","/metrics","/metrics/**","/trace","/api/applications/**"};
	
	
	public static boolean isExculdeUrl(final String reqUri){
		
		if(StringUtils.isNotBlank(reqUri)){
			for (String uri : EXCULDE_URL) {
				if(uri.contains("**")){
					uri = uri.replace("**", "");
					if(reqUri.startsWith(uri)){
						return true;
					}
				}else if(StringUtils.equalsIgnoreCase(reqUri, uri)){
					return true;
				}
			}
		}
		return false;
	}
	
}
