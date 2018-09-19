package com.sstc.hmis.portal.common.base;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName PropertyUtil
 * @Description property文件工具类 
 * @author yaodm
 * @date 2017年3月7日 下午6:53:44
 */
@Component
public class PropertyUtil  implements InitializingBean{
		
	@Value("${hostname.resources}")
    public String common_resources_url;
	@Value("${resource.version}")
	public String common_resources_ver;
	@Value("${sys.profile:test}")
	public String profileActive;
	
	public static String COMMON_RESOURCES_URL;
	
	public static String COMMON_RESOURCES_VER;
	
	public static String PROFILE_ACTIVE;
	

	@Override
	public void afterPropertiesSet() throws Exception {
		COMMON_RESOURCES_URL = common_resources_url;
		COMMON_RESOURCES_VER = common_resources_ver;
		PROFILE_ACTIVE = profileActive;
	}
       
}
