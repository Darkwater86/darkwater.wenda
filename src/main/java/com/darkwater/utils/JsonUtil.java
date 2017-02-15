package com.darkwater.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lenovo1 on 2017/2/4.
 */
public class JsonUtil {
    private static final Logger logger= LoggerFactory.getLogger(JsonUtil.class);

    public static String getJsonString(String code,String msg){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        return json.toJSONString();
    }
    public static String getJsonString(String code){
        JSONObject json = new JSONObject();
        json.put("code",code);
        return json.toJSONString();
    }
}
