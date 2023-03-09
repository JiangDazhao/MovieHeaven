package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {
    List<Review> selectReviewsByMovieId(int movieId,int offset,int limit);
    List<Review> selectAllReviewsByMovieId(int movieId);
    int selectCountByMovieId(int movieId);
    int insertReview(Review review);
    Review selectReviewByReviewId(int reviewId);

}
