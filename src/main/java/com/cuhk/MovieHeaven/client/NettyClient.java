package com.cuhk.MovieHeaven.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.config.NettyConfig;
import com.cuhk.MovieHeaven.handler.NettyClientHandler;
import com.cuhk.MovieHeaven.pojo.NettyMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

public class NettyClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final String URL = System.getProperty("nettyUrl", "ws://127.0.0.1:8084/netty");
    static final int PORT = 8084;

    public void sendMsg(NettyMessage msg) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            URI nettyUri = new URI(URL);
            WebSocketClientProtocolHandler wsHandler = new WebSocketClientProtocolHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(
                            nettyUri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()));
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpObjectAggregator(4096));
                            pipeline.addLast(wsHandler);
                            pipeline.addLast(new NettyClientHandler());
                        };
                    });
            ChannelFuture future = bootstrap.connect(HOST, PORT);
            future.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
            future.channel().closeFuture().sync();
            group.shutdownGracefully();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
