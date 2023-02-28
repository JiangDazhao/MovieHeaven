package com.cuhk.MovieHeaven.dao;

import com.cuhk.MovieHeaven.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {
    List<Review> selectAllReview(int offset,int limit);
    Review selectReviewByReviewId(int reviewId);
    Review selectReviewByMovieId(int movieId);
    int insertReview(Review review);

    int selectReviewRows();
    int updateStars(int reviewId,int stars);
}
