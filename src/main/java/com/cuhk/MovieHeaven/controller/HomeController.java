package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.Movie;
import com.cuhk.MovieHeaven.entity.User;
import com.cuhk.MovieHeaven.service.MovieService;
import com.cuhk.MovieHeaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cuhk.MovieHeaven.entity.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @RequestMapping(path="/index",method = RequestMethod.GET)
    public String getIndexPage(Model model,Page page){
        // 方法调用前,SpringMVC会自动实例化Model和Page,并将Page注入Model，这里注入的主要是current
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(movieService.findMovieRows());
        page.setPath("/index");

        List<Movie> allMovies = movieService.findAllMovies(page.getOffset(), page.getLimit());

        List<Map<String,Object>> moviePosts=new ArrayList<>();
        if(allMovies!=null){
            for(Movie movie:allMovies){
                Map<String, Object> map = new HashMap<>();
                map.put("post",movie);
                User user=userService.findUserById(movie.getUserId());
                map.put("user",user);
                moviePosts.add(map);
            }
        }
        model.addAttribute("moviePosts",moviePosts);
        return "index";
    }
}
