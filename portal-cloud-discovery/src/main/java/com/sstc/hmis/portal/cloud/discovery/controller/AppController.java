package com.sstc.hmis.portal.cloud.discovery.controller;

import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@RequestMapping("${eureka.dashboard.path:/}app/")
public class AppController {

    private static final OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * 执行重启
     * @param appUrl 例如： http://10.1.0.1:7896/info
     * @return 是否成功
     */
    @RequestMapping(value = "restart", method = RequestMethod.GET)
    @ResponseBody
    public ReturnMessage restart(String appUrl) {
        URL restartUrl = null;
        try {
            restartUrl = getRestartUrl(appUrl);
        } catch (Exception e) {
            return ReturnMessage.fail("无法生成"+appUrl+"对应的重启URL，"+e.getMessage());
        }

        // 向地址（如：http://10.1.0.1:7896/restart）发送Post请求执行restart命令
        Request request = new Request.Builder()
                .url(restartUrl).post(RequestBody.create(JSON,"")).build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200){
                return ReturnMessage.success();
            }else{
                return ReturnMessage.fail("调用"+restartUrl+"失败，HTTP响应："+response.code()+",响应信息："+response.body().string());
            }
        } catch (IOException e) {
            return ReturnMessage.fail("调用"+restartUrl+"失败，"+e.getMessage());
        }

    }

    /**
     * 获得重启URL
     *
     * @param appUrl 例如： http://10.1.0.1:7896/info
     * @return 重启URL
     */
    URL getRestartUrl(String appUrl) throws MalformedURLException {
        // 去除最后可能存在的/
        if (appUrl.endsWith("/")) {
            appUrl = appUrl.substring(0, appUrl.length() - 1);
        }

        //查找除http://之外的/的位置，并截取之前的字符串（例如http://10.1.0.1:7896）
        int index = appUrl.indexOf("/", 7);
        if (index >= 0) {
            appUrl = appUrl.substring(0, index);
        }
        appUrl += "/restart";
        return new URL(appUrl);
    }

    static class ReturnMessage{
        /**
         * 是否成功
         */
        private boolean success;

        /**
         * 失败信息
         */
        private String failMessage;

        /**
         * 构造函数
         * @param success 是否成功
         */
        ReturnMessage(boolean success){
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }


        public String getFailMessage() {
            return failMessage;
        }

        public void setFailMessage(String failMessage) {
            this.failMessage = failMessage;
        }

        /**
         * 生成成功消息
         * @return 成功消息
         */
        static ReturnMessage success(){
            return new ReturnMessage(true);
        }

        /**
         * 生成失败消息
         * @param failMessage 失败信息
         * @return 失败消息
         */
        static ReturnMessage fail(String failMessage){
            ReturnMessage returnMessage = new ReturnMessage(false);
            returnMessage.setFailMessage(failMessage);
            return returnMessage;
        }
    }
}