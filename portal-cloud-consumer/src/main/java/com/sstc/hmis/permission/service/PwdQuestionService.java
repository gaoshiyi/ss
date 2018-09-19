/**
 * 
 */
package com.sstc.hmis.permission.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
  * <p> Title: PwdQuestionService </p>
  * <p> Description:  密保问题 </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年3月31日 上午11:45:10
   */
@FeignClient(name = "${feign.provider.portal}")
@RequestMapping("/pwdQuesService")
public interface PwdQuestionService {

}
