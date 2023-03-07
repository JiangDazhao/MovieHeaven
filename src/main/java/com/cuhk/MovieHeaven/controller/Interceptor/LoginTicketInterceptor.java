package com.cuhk.MovieHeaven.controller.Interceptor;

import com.cuhk.MovieHeaven.entity.LoginTicket;
import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.service.UserService;
import com.cuhk.MovieHeaven.util.CookieUtil;
import com.cuhk.MovieHeaven.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String cookieValue= CookieUtil.getValue(request,"ticket");
        if(cookieValue!=null){
            LoginTicket loginTicket = userService.findLoginTicket(cookieValue);
            if(loginTicket!=null&&loginTicket.getStatus()!=0&&loginTicket.getExpired().after(new Date())){
                // store the login user, which can be used in many situations
                User user = userService.findUserById(loginTicket.getUserId());
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // get the user from this  request thread
        User user=hostHolder.getUser();
        if(user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // clear the user in ThreadLocal
        hostHolder.clear();

    }
}
