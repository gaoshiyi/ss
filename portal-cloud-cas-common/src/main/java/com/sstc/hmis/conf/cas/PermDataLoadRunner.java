/**
 * 
 */
package com.sstc.hmis.conf.cas;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.stereotype.Component;

import com.sstc.hmis.permission.service.PermissionService;

/**
  * <p> Title: PermDataLoadRunner </p>
  * <p> Description:  权限拦截器加载 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年6月30日 下午1:24:34
   */
@Component
@ConditionalOnBean(value = {ShiroCasConfiguration.class})
@AutoConfigureAfter({ShiroFilterFactoryBean.class,RedisAutoConfiguration.class})
public class PermDataLoadRunner implements CommandLineRunner{

	private static final String RESOURCE_CHAIN = "/resources/** = anon";
	
	@Value("${spring.shiro.cas.excludeUrl:}")
	private String[] excludeUrl;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired(required=false)
    private MyShiroFilterFactoryBean shiroFilterFactoryBean;
	
	@Override
	public void run(String... arg0) throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(RESOURCE_CHAIN);
		
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		section.put("/resources/**", "anon");
		//浏览器默认访问的favicon.ico不需要权限验证
		section.put("/favicon.ico", "anon");
		section.putAll(anonEndpoints());
		section.put("/ReportServer?op=fr_cluster**", "anon");
		// crm访问链接不走鉴权
		section.put("/outer/access/**", "anon");
		section.put("/sync", "sync");
		section.put("/cas", "cas");
		section.put("/logout", "logout");
		Set<String> urlList = permissionService.findAllFuncPerms();
		
		Assert.notNull(urlList, "权限控制url不能为空");
		
		
		if(urlList != null){
			if(excludeUrl != null && excludeUrl.length > 0){
				List<String> excludeList = Arrays.asList(excludeUrl);
				urlList.removeAll(excludeList);
				excludeList.stream().filter(u -> isInvalid(u)).forEach( u -> section.put(u, "anon"));
			}
			urlList.stream().filter(u -> isInvalid(u)).forEach( u -> section.put(u, "redirect["+ u +"]"));
		}
		
		section.put("/**", "na,perms,authc,user");
		shiroFilterFactoryBean.createChain(section);
	}
	
	
	private boolean isInvalid(String str){
		boolean invalid = true;
		if(StringUtils.isBlank(str) || str.contains("\"") || str.contains("\'")){
			invalid = false;
		}
		return invalid;
	}
	
	/**
	 * 固定监控url配置
	 * @return
	 */
	private Map<String,String> anonEndpoints(){
		Map<String,String> anonMap = new HashMap<String, String>();
		for (String key : ShiroCasConfiguration.EXCULDE_URL) {
			anonMap.put(key, "anon");
		}
		return anonMap;
	}

}
