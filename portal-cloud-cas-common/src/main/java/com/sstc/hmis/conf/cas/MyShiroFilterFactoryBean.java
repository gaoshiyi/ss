/**
 * 
 */
package com.sstc.hmis.conf.cas;

import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

/**
  * <p> Title: MyShiroCasConfig </p>
  * <p> Description:  加载全局url拦截 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年7月4日 下午3:11:06
   */
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean{

	
	private FilterChainManager filterChainManager;
	
	/* (non-Javadoc)
	 * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean#createFilterChainManager()
	 */
	@Override
	protected FilterChainManager createFilterChainManager() {
		filterChainManager = super.createFilterChainManager();
		return filterChainManager;
	}
	
	
	public void createChain(Map<String,String> chains){
		if (!CollectionUtils.isEmpty(chains)) {
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue();
                filterChainManager.createChain(url, chainDefinition);
            }
        }
	}
	
}
