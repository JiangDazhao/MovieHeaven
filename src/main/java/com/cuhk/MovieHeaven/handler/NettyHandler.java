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

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.pojo.NettyMessage;

@ChannelHandler.Sharable
@Component
public class NettyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    Map<String, ChannelHandlerContext> userChanMap = new ConcurrentHashMap<>(5);
    Map<ChannelId, String> chanUserMap = new ConcurrentHashMap<>(6);
    List<ChannelHandlerContext> chanList = new CopyOnWriteArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        chanList.add(ctx);
        System.out.println("Current number of connections:" + chanList.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("The message received from the front end:" + msg.text());
        NettyMessage rcv_msg = JSON.parseObject(msg.text(), NettyMessage.class);
        if (rcv_msg.getType() == 0) {
            setMap(rcv_msg.getUsername(), ctx);
            for (ChannelHandlerContext handlerContext : userChanMap.values()) {
                if (handlerContext == ctx) {
                    continue;
                }
                handlerContext.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(rcv_msg)));
            }
            return;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String username = chanUserMap.get(ctx.channel().id());
        userChanMap.remove(username);
        for (ChannelHandlerContext handlerContext : userChanMap.values()) {
            NettyMessage msg = new NettyMessage("Server", UUID.randomUUID().toString(),
                    "User--" + username + "--disconneted.", 1);
            handlerContext.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
        }
        chanList.remove(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String username = chanUserMap.get(ctx.channel().id());
        userChanMap.remove(username);
        for (ChannelHandlerContext handlerContext : userChanMap.values()) {
            NettyMessage msg = new NettyMessage("Server", UUID.randomUUID().toString(),
                    "User--" + username + "--disconneted.", 1);
            handlerContext.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
        }
        chanList.remove(ctx);
    }

    private void setMap(String username, ChannelHandlerContext ctx) {
        userChanMap.put(username, ctx);
        chanUserMap.put(ctx.channel().id(), username);
    }
}
