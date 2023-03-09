package com.cuhk.MovieHeaven.controller;


import com.cuhk.MovieHeaven.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DataController {
    @Autowired
    DataService dataService;

    @RequestMapping(path="/data/aveScore/{movieId}",method = RequestMethod.GET)
    public String getaveScore(@PathVariable("movieId") int movieId, Model model){
        float aveScoreResult=dataService.calAveScore(movieId);
        model.addAttribute("aveScoreResult",aveScoreResult);
        return "forward:/moviepost/detail/"+movieId;
    }
}
