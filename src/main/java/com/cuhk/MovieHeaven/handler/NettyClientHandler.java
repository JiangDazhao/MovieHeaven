package com.cuhk.MovieHeaven.handler;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.pojo.NettyRequest;
import com.cuhk.MovieHeaven.pojo.NettyResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@Component
public class NettyClientHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private NettyResponse res;

    public NettyClientHandler() {
        super();
        this.res = null;
    }

    public NettyResponse getRes() {
        return this.res;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Netty Client channel active!");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("Receive msg from Netty Server: " + msg.text());
        NettyResponse rcv_res = JSON.parseObject(msg.text(), NettyResponse.class);
        if (rcv_res.getType() == 1) {
            this.res = new NettyResponse(1, rcv_res.getReviewList());
            System.out.println("copy list done!");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception is caught by the client handler!!!!!!!!!!1");
        cause.printStackTrace();
        ctx.close();
    }
}
