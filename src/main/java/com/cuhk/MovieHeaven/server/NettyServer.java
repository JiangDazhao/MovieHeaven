package com.cuhk.MovieHeaven.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

@Component
public class NettyServer implements DisposableBean {

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public void start(int port) {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(serverChannelInitializer);

        ChannelFuture sync = null;
        try {
            sync = serverBootstrap.bind(port).sync();
            System.out.println("Netty server starts successfully! Port Number: " + port);
            sync.channel().closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void destroy() throws Exception {
        close();
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
