package com.cuhk.MovieHeaven.util;

public class RedisKeyUtil {
    private static final String SPLIT=":";
    private static final String PREFIX_KAPTCHA="kaptcha";
    private static final String PREFIX_LOGINTICKET="ticket";

    public static String getKaptchaKey(String key){
        return PREFIX_KAPTCHA+SPLIT+key;
    }

    public static String getLoginTicketKey(String key){
        return PREFIX_LOGINTICKET+SPLIT+key;
    }
}
