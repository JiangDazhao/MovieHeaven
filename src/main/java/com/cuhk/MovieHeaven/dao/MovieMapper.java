package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {
    List<Movie> selectAllMovies(int offset, int limit);

    Movie selectMovieByMovieId(int movieId);

    Movie selectMovieByTitle(String title);

    int insertMovie(Movie movie);

    int selectMovieRows();

    int updateRating(int movieId,int rating);
}
