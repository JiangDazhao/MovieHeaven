package com.cuhk.MovieHeaven.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class CookieUtil {
    public static String getValue(HttpServletRequest request, String name){
        if(request==null||name==null){
            throw new IllegalArgumentException("Empty argument");
        }

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(Objects.equals(cookie.getName(),name)){
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
