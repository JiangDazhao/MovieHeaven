package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.Movie;
import com.cuhk.MovieHeaven.entity.Page;
import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.service.MovieService;
import com.cuhk.MovieHeaven.service.ReviewService;
import com.cuhk.MovieHeaven.service.UserService;
import com.cuhk.MovieHeaven.util.HostHolder;
import com.cuhk.MovieHeaven.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Year;
import java.util.*;

@Controller
@RequestMapping(path="/moviepost")
public class MoviePostController {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MovieService movieService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public String addMoviePost(String title, String info, String genre, Year year){
        User user = hostHolder.getUser();
        if(user==null){
            return MovieUtil.getJSONString(403,"You haven't logined, no authentication");
        }

        Movie movie = new Movie();
        movie.setUserId(user.getUserId());
        movie.setTitle(title);
        movie.setInfo(info);
        movie.setGenre(genre);
        movie.setYear(year);
        movie.setPostTime(new Date());
        movieService.addMovie(movie);

        return MovieUtil.getJSONString(200,"Pushlish Successfully");
    }

    @RequestMapping(path = "/detail/{movieId}",method = RequestMethod.GET)
    public String getMoviePostReviwDetail(@PathVariable("movieId")int movieId, Model model, Page page){
        // movie
        Movie movie = movieService.findMovieByMovieId(movieId);
        model.addAttribute("post",movie);

        // movie's postUser
        User user = userService.findUserById(movie.getUserId());
        model.addAttribute("user",user);

        page.setLimit(5);
        page.setPath("/moviepost/detail/"+movieId);
        page.setRows(movie.getReviewCount());

        List<Review> reviewsByMovieId = reviewService.findReviewsByMovieId(movieId,page.getOffset(),page.getLimit());

        List<Map<String,Object>> reviewDetailList=new ArrayList<>();
        if(reviewDetailList!=null){
            for(Review review:reviewsByMovieId){
                Map<String,Object> reviewDetail=new HashMap<>();
                reviewDetail.put("review",review);
                reviewDetail.put("user",userService.findUserById(review.getUserId()));
                reviewDetailList.add(reviewDetail);
            }
        }

        model.addAttribute("reviewDetailList",reviewDetailList);
        return "site/moviePost-detail";
    }
}
