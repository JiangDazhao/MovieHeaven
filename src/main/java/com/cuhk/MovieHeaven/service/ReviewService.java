package com.cuhk.MovieHeaven.service;

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
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewService {
    private static final Logger logger= LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    ReviewMapper reviewMapper;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    // Caffeine核心接口：LoadingCache：同步缓存，AsyncLoadingCache：异步缓存
    // 想缓存频繁访问的数据，但是又不是更新的那么快的数据

    //review列表缓存
    private LoadingCache<String, List<Review>> reviewListCache;

    //review总数缓存
    private LoadingCache<Object,Integer> reviewRowsCache;

    @PostConstruct
    public void init(){
        // review列表缓存
        reviewListCache= Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<Review>>() {
                    @Override
                    public @Nullable List<Review> load(@NonNull String s) throws Exception {
                        if(s==null||s.length()==0){
                            throw new IllegalArgumentException("参数错误");
                        }
                        String[] params = s.split(":");
                        if(params==null||params.length!=2){
                            throw new IllegalArgumentException("参数错误");
                        }

                        int offset= Integer.valueOf(params[0]);
                        int limit=Integer.valueOf(params[1]);


                        logger.debug("load post list from DB.");
                        return reviewMapper.selectAllReview(offset,limit);
                    }
                });

        //review总数缓存
        reviewRowsCache=Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds,TimeUnit.SECONDS)
                .build(new CacheLoader<Object, Integer>() {
                    @Override
                    public @Nullable Integer load(@NonNull Object o) throws Exception {
                        return reviewMapper.selectReviewRows();
                    }
                });
    }

    // 查出所有review
    public List<Review> findAllReview(int offset,int limit){
        return reviewListCache.get(offset+":"+limit);
    }

    // 查出所有review 行数,与输入无关
    public int findReviewRows(){
        return reviewRowsCache.get(null);
    }

    public Review findReviewByReviewId(int id){
        return reviewMapper.selectReviewByReviewId(id);
    }

    public Review findReviewByMovieId(int id){
        return reviewMapper.selectReviewByMovieId(id);
    }

    public int addReview(Review review){
        if (review == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        review.setReviewContent(HtmlUtils.htmlEscape(review.getReviewContent()));
        return reviewMapper.insertReview(review);
    }

    public int updateStars(int reviewId,int stars){
        return reviewMapper.updateStars(reviewId,stars);
    }
}
