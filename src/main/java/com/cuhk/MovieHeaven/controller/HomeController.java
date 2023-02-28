package com.cuhk.MovieHeaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.cuhk.MovieHeaven.entity.Page;
@Controller
public class HomeController {
    @RequestMapping(path="/index",method = RequestMethod.GET)
    public String getIndexPage(Model model,Page page){



        return "/index";
    }
}
