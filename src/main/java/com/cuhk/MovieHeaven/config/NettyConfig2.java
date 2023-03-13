package com.cuhk.MovieHeaven.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "netty2")
public class NettyConfig2 {
    private int port;
    private String path;
}
