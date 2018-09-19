///**
// * 
// */
//package com.sstc.hmis.conf.web;
//
//import org.springframework.context.annotation.Configuration;
//
//import com.netflix.hystrix.exception.HystrixBadRequestException;
//
//import feign.FeignException;
//import feign.Response;
//import feign.codec.ErrorDecoder;
//
///**
//  * <p> Title: BizExceptionFeignErrorDecoder </p>
//  * <p> Description:  全局Feign的rest请求异常捕获 </p>
//  * <p> Company: SSTC </p> 
//  * @author  Qxiaoxiang
//  * @date  2017年6月30日 下午5:14:21
//   */
//@Configuration
//public class BizExceptionFeignErrorDecoder implements ErrorDecoder{
//
//	@Override
//	public Exception decode(String methodKey, Response response) {
//		int status = response.status();
//		if(status >= 400 && status <= 499){
//			return new HystrixBadRequestException("");
//		}
//		return FeignException.errorStatus(methodKey, response);
//	}
//
//}
