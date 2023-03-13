package com.cuhk.MovieHeaven.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.pojo.InsertRequest;
import com.cuhk.MovieHeaven.pojo.InsertResponse;
import com.cuhk.MovieHeaven.pojo.NettyRequest;
import com.cuhk.MovieHeaven.pojo.NettyResponse;
import com.cuhk.MovieHeaven.pojo.QueryRequest;
import com.cuhk.MovieHeaven.pojo.QueryResponse;

public class MyWebSocketClient extends WebSocketClient {
    private NettyResponse res;

    public NettyResponse getRes() {
        return this.res;
    }

    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
        this.res = null;
    }

    public void queryReviews(int movieId) {
        NettyRequest req = new QueryRequest(movieId);
        this.send(JSON.toJSONString(req));
    }

    public void insertReview(Review review) {
        NettyRequest req = new InsertRequest(review);
        this.send(JSON.toJSONString(req));
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
        NettyResponse rcv_msg = JSON.parseObject(msg, NettyResponse.class);
        if (rcv_msg.getType() == 1) {
            QueryResponse res = JSON.parseObject(msg, QueryResponse.class);
            this.res = new QueryResponse(res.getReviewList());
        } else if (rcv_msg.getType() == 2) {
            InsertResponse res = JSON.parseObject(msg, InsertResponse.class);
            this.res = new InsertResponse(res.getRowNum());
        }
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
