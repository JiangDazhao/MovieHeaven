package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.client.MyWebSocketClient;
import com.cuhk.MovieHeaven.dao.MovieMapper;
import com.cuhk.MovieHeaven.dao.ReviewMapper;
import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.pojo.InsertResponse;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.java_websocket.enums.ReadyState;
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

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
    private static final String URL1 = "ws://127.0.0.1:8084/netty1";
    private static final String URL2 = "ws://127.0.0.1:8085/netty2";

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    MovieMapper movieMapper;

    public List<Review> findReviewsByMovieId(int movieId, int offset, int limit) {
        return reviewMapper.selectReviewsByMovieId(movieId, offset, limit);
    }

    public int findReviewCount(int movieId) {
        return reviewMapper.selectCountByMovieId(movieId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addReview(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 添加review
        review.setReviewContent(HtmlUtils.htmlEscape(review.getReviewContent()));
        int rowNum = 0;
        if (review.getReviewId() % 2 == 0) {
            try {
                MyWebSocketClient ws = new MyWebSocketClient(URL1);
                ws.connect();
                while (!ws.getReadyState().equals(ReadyState.OPEN)) {
                    System.out.println("[1] Connecting...");
                    Thread.sleep(1);
                }
                ws.insertReview(review);
                int i = 0;
                while (ws.getRes() == null) {
                    System.out.println("[1] Processing...");
                    Thread.sleep(1000);
                    i++;
                    if (i >= 5) {
                        System.out.println("Timeout!!!");
                        break;
                    }
                }
                InsertResponse res = (InsertResponse) ws.getRes();
                rowNum = res.getRowNum();
                ws.close();
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                MyWebSocketClient ws = new MyWebSocketClient(URL2);
                ws.connect();
                while (!ws.getReadyState().equals(ReadyState.OPEN)) {
                    System.out.println("[2] Connecting...");
                    Thread.sleep(1);
                }
                ws.insertReview(review);
                int i = 0;
                while (ws.getRes() == null) {
                    System.out.println("[2] Processing...");
                    Thread.sleep(1000);
                    i++;
                    if (i >= 5) {
                        System.out.println("Timeout!!!");
                        break;
                    }
                }
                InsertResponse res = (InsertResponse) ws.getRes();
                rowNum = res.getRowNum();
                ws.close();
            } catch (URISyntaxException | InterruptedException e) {
                e.printStackTrace();
            }

        }

        // 同时更新movie reviewCount
        int count = reviewMapper.selectCountByMovieId(review.getMovieId());
        movieMapper.updateReviewCount(review.getMovieId(), count);

        return rowNum;
    }

    public Review findReviewByReviewId(int id) {
        return reviewMapper.selectReviewByReviewId(id);
    }

}
