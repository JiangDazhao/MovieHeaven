package com.cuhk.MovieHeaven.util;

public class RedisKeyUtil {
    private static final String PREFIX_KAPTCHA="kaptcha";
    private static final String SPLIT=":";

    public static String getKaptchaKey(String key){
        return PREFIX_KAPTCHA+SPLIT+key;
    }

}
