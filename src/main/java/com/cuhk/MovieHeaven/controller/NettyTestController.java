package com.cuhk.MovieHeaven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cuhk.MovieHeaven.config.NettyConfig;

@Controller
public class NettyTestController {
    @Autowired
    NettyConfig nettyConfig;

    @RequestMapping(path = "/netty_test", method = RequestMethod.GET)
    public String getNettyTestPage() {
        return "/netty_test";
    }
}
