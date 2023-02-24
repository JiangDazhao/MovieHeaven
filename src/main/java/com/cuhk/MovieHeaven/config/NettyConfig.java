package com.cuhk.MovieHeaven.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {
    private int port;
    private String path;

    public int getPort() {
        return this.port;
    }

    public String getPath() {
        return this.path;
    }
}
