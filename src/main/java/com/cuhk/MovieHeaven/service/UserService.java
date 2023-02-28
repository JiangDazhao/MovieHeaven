package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.util.CommunityUtil;
import com.cuhk.MovieHeaven.dao.UserMapper;
import com.cuhk.MovieHeaven.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Map<Object,String> registerValidate(User user){
        Map<Object,String> map=new HashMap<>();

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
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        userMapper.insertUser(user);

        return map;
    }

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

}
