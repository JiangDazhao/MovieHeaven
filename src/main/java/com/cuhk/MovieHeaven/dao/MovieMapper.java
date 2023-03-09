package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {
    List<Movie> selectAllMovies(int offset, int limit);

    int selectMovieRows();

    Movie selectMovieByMovieId(int movieId);

    int insertMovie(Movie movie);

    int updateReviewCount(int movieId, int reviewCount);

    int updateRating(int movieId,int rating);
}
