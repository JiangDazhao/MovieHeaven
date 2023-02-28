package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.service.UserService;
import com.cuhk.MovieHeaven.util.CommunityUtil;
import com.cuhk.MovieHeaven.util.RedisKeyUtil;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path="/register",method = RequestMethod.GET)
    public String getRegisterPage(){
        return "/site/register";
    }

    @RequestMapping(path="/login",method = RequestMethod.GET)
    public String getLoginPage(){
        return"/site/login";
    }

    @RequestMapping(path="/register",method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<Object,String> map=userService.registerValidate(user);
        if(map==null||map.isEmpty()){
            model.addAttribute("msg","Successfully register!");
            model.addAttribute("target","/index");
            return "/site/operate-result";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path="/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response){
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);


        String kaptchaKeyStr= CommunityUtil.generateUUID();
        // 将kaptchaKeyStr存入session response作为凭证
        Cookie cookie = new Cookie("kaptcha", kaptchaKeyStr);
        cookie.setPath(CONTEXT_PATH);
        cookie.setMaxAge(60);
        response.addCookie(cookie);

        String redisKey= RedisKeyUtil.getKaptchaKey(kaptchaKeyStr);
        redisTemplate.opsForValue().set(redisKey,text,60); //60秒的过期时间

        response.setContentType("image/png");
        try {
            // response写图片
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }

}