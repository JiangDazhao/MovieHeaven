package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(int id);
    User selectByName(String name);
    int insertUser(User user);

}
