package com.cuhk.MovieHeaven.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

public class MovieUtil {
    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 根据key生成md5码
    public static String  md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJSONString(int code, String msg, Map<String,Object>map){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if(map!=null){
            for(String key:map.keySet()){
                map.put(key,map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    // 方法重载
    public static String getJSONString(int code, String msg){
            return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }
}
