package com.hjc.hjjcclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hjc.hjjcclientsdk.util.SignUtils;

import com.hjc.hjjcclientsdk.model.User;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * File Description: houApiClient
 * Author: hou-jch
 * Date: 2024/9/3
 */

public class HouApiClient {

    private String gatewayHost;
    private String secretKey;
    private String accessKey;
    private Map<String,String> getHeaderMap(String body){
        Map<String,String> paramMap = new HashMap<>();
//        paramMap.put("secretKey",secretKey);
        paramMap.put("accessKey",accessKey);
        paramMap.put("body",body);
        paramMap.put("nonce", RandomUtil.randomNumbers(4));
        paramMap.put("timeStamp",String.valueOf(System.currentTimeMillis()/1000));
        paramMap.put("sign", SignUtils.getSion(body,secretKey));
        return paramMap;
    }
//    public HouApiClient(String secretKey, String accessKey) {
//        this.secretKey = secretKey;
//        this.accessKey = accessKey;
//    }
    public HouApiClient(String secretKey, String accessKey,String gatewayHost) {
        this.secretKey = secretKey;
        this.accessKey = accessKey;
        this.gatewayHost = gatewayHost;
    }


    public void setAkSkApiClient(String secretKey, String accessKey){
        this.secretKey = secretKey;
        this.accessKey = accessKey;
    }
    public String getNameByGet(String name) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.get(gatewayHost +"/api/name/", paramMap);
        System.out.println(result);
        return result;
    }


    public String getNameByPost(String name) {
        String jsonStr = JSONUtil.toJsonStr(name);
        HttpResponse httpResponse = HttpRequest.post(gatewayHost +"/api/name/names").addHeaders(getHeaderMap(jsonStr)).body(jsonStr).execute();
        System.out.println(httpResponse.body());
        return httpResponse.body();
    }


    public String getUserNameByPost( User user) {
        String jsonStr = JSONUtil.toJsonStr(user);
      HttpResponse httpResponse = HttpRequest.post(gatewayHost +"/api/name/user").addHeaders(getHeaderMap(jsonStr)).body(jsonStr).execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
