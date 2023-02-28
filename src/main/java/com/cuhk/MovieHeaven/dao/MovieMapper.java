package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
    Movie selectById(int movieId);

    Movie selectByTitle(String title);

    int insertMovie(Movie movie);
}
