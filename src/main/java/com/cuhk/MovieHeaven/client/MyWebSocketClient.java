package com.cuhk.MovieHeaven.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.pojo.NettyRequest;
import com.cuhk.MovieHeaven.pojo.NettyResponse;

public class MyWebSocketClient extends WebSocketClient {
    private NettyResponse res;

    public NettyResponse getRes() {
        return this.res;
    }

    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
        this.res = null;
    }

    public void sendQuery(int movieId) {
        NettyRequest queryId = new NettyRequest(1, movieId);
        this.send(JSON.toJSONString(queryId));
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        System.out.println("Closed.");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Error: " + e);
    }

    @Override
    public void onMessage(String msg) {
        System.out.println("Received: " + msg);
        NettyResponse rcv_res = JSON.parseObject(msg, NettyResponse.class);
        this.res = new NettyResponse(rcv_res.getType(), rcv_res.getReviewList());
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("Handshaking...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext();) {
            String key = it.next();
            System.out.println(key + ": " + shake.getFieldValue(key));
        }
    }

}
