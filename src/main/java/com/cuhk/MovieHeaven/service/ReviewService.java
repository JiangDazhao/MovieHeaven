package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.dao.MovieMapper;
import com.cuhk.MovieHeaven.dao.ReviewMapper;
import com.cuhk.MovieHeaven.entity.Review;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewService {
    private static final Logger logger= LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    MovieMapper movieMapper;

    public List<Review> findReviewsByMovieId(int movieId,int offset,int limit){
        return reviewMapper.selectReviewsByMovieId(movieId,offset,limit);
    }

    public int findReviewCount(int movieId){
        return reviewMapper.selectCountByMovieId(movieId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int addReview(Review review){
        if (review == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 添加review
        review.setReviewContent(HtmlUtils.htmlEscape(review.getReviewContent()));
        int rows= reviewMapper.insertReview(review);

        // 同时更新movie reviewCount
        int count = reviewMapper.selectCountByMovieId(review.getMovieId());
        movieMapper.updateReviewCount(review.getMovieId(),count);

        return rows;
    }

    public Review findReviewByReviewId(int id){
        return reviewMapper.selectReviewByReviewId(id);
    }

}
