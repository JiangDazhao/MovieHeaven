package com.cuhk.MovieHeaven.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cuhk.MovieHeaven.config.NettyConfig;
import com.cuhk.MovieHeaven.handler.NettyHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

@Component
public class NettyChannelInitializer extends ChannelInitializer {
    @Autowired
    NettyConfig nettyConfig;

    @Autowired
    NettyHandler nettyHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new WebSocketServerProtocolHandler(nettyConfig.getPath()));
        pipeline.addLast(new HttpObjectAggregator(4096));
        pipeline.addLast(nettyHandler);
    }
}
