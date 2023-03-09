package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.service.ReviewService;
import com.cuhk.MovieHeaven.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping(path="/review")
public class ReviewController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    ReviewService reviewService;

    @RequestMapping(path = "/add/{movieId}",method = RequestMethod.POST)
    public String addReview(@PathVariable("movieId") int movieId, Review review){ // review里面已经携带了name="stars"，name="reviewContent"，name="movieId"
        review.setUserId(hostHolder.getUser().getUserId());
        review.setReviewTime(new Date());
        reviewService.addReview(review);
        
        return "redirect:/moviepost/detail/"+movieId; // 加了review以后继续重定向到该movie的详情页面上
    }
}
