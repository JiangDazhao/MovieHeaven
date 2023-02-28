package com.cuhk.MovieHeaven.controller;

import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cuhk.MovieHeaven.entity.Page;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ReviewService reviewService;

    @RequestMapping(path="/index",method = RequestMethod.GET)
    public String getIndexPage(Model model,Page page){
        // 方法调用前,SpringMVC会自动实例化Model和Page,并将Page注入Model，这里注入的主要是current
        // 所以,在thymeleaf中可以直接访问Page对象中的数据.
        page.setRows(reviewService.findReviewRows());
        page.setPath("/index");

        List<Review> list = reviewService.findAllReview(page.getOffset(), page.getLimit());


        return "/index";
    }
}
