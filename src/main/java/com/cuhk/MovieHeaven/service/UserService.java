package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.entity.LoginTicket;
import com.cuhk.MovieHeaven.util.MovieUtil;
import com.cuhk.MovieHeaven.dao.UserMapper;
import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public Map<String,Object> registerValidate(User user){
        Map<String,Object> map=new HashMap<>();

        if(user==null){
            throw new IllegalArgumentException("参数不能为空！");
        }

        if(StringUtils.isBlank(user.getUserName())){
            map.put("usernameMsg","账号不能为空！");
            return map;
        }

        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空！");
            return map;
        }

        User u = userMapper.selectByName(user.getUserName());
        if(u!=null){
            map.put("usernameMsg","账号已经存在！");
            return map;
        }

        // 根据传入的user信息进行注册
        user.setSalt(MovieUtil.generateUUID().substring(0,5));
        user.setPassword(MovieUtil.md5(user.getPassword()+user.getSalt()));
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        userMapper.insertUser(user);

        return map;
    }

    public Map<String,Object>  login(String username, String password, int expiredSeconds){
        Map<String,Object> map=new HashMap<>();

        // 空值处理
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","Username is Empty!");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","Password is Empty!");
            return map;
        }

        //通过用户名从数据库拿到唯一的用户
        User user = userMapper.selectByName(username);
        if(user==null){
            map.put("usernameMsg","The account is Empty!");
            return map;
        }

        // 验证密码
        String s = MovieUtil.md5(password + user.getSalt());
        if(!user.getPassword().equals(s)){
            map.put("passwordMsg","Password is wrong");
            return map;
        }

        //前面验证成功，创建登录凭证
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(user.getUserId());
        loginTicket.setTicket(MovieUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+expiredSeconds*1000));

        //将loginTicket的信息存入redis，不过期
        String redisKey= RedisKeyUtil.getLoginTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket); // 自动将对象转换为JSON，整个存入redis

        map.put("ticket",loginTicket.getTicket()); // 只返回一个UUID
        return map;
    }


    public User findUserById(int id){
        return userMapper.selectById(id);
    }

}
