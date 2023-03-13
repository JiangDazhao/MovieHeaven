package com.cuhk.MovieHeaven.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cuhk.MovieHeaven.handler.NettyServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;

@Component
public class NettyServer implements DisposableBean {

    @Autowired
    NettyServerHandler nettyServerHandler;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start(int port, String path) {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(4096));
                        pipeline.addLast(new WebSocketServerProtocolHandler(path));
                        pipeline.addLast(nettyServerHandler);
                    };
                });

        ChannelFuture sync = null;
        try {
            sync = serverBootstrap.bind(port).sync();
            System.out.println("Netty server starts successfully! Port Number: " + port);
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void destroy() throws Exception {
        close();
        System.out.println("The netty server is closed!");
    }

    public void close() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
