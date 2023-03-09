package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.Movie;
import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.service.MovieService;
import com.cuhk.MovieHeaven.service.UserService;
import com.cuhk.MovieHeaven.util.HostHolder;
import com.cuhk.MovieHeaven.util.MovieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Year;
import java.util.Date;

@Controller
@RequestMapping("/moviepost")
public class MoviePostController {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @Autowired
    MovieService movieService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
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
}
