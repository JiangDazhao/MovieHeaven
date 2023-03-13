package com.cuhk.MovieHeaven.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.dao.ReviewMapper;
import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.pojo.NettyRequest;
import com.cuhk.MovieHeaven.pojo.NettyResponse;

@ChannelHandler.Sharable
@Component
public class NettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    ReviewMapper reviewMapper;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("new connection!");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("The message received from the front end:" + msg.text());
        NettyRequest rcv_msg = JSON.parseObject(msg.text(), NettyRequest.class);
        if (rcv_msg.getType() == 1) {
            List<Review> reviews = reviewMapper.selectAllReviewsByMovieId(rcv_msg.getMovieId());
            NettyResponse res = new NettyResponse(1, reviews);
            ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(res)));
            return;
        } else if (rcv_msg.getType() == 2) {
            System.out.println("type = 2");
            System.out.println(rcv_msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("disconnected!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Error!");
        System.out.println(cause);
    }
}
