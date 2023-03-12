package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.client.MyWebSocketClient;
import com.cuhk.MovieHeaven.entity.Review;

import org.java_websocket.enums.ReadyState;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    public float calAveScore(int movieId) {
        List<Review> reviews = new ArrayList<>();
        try {
            MyWebSocketClient ws = new MyWebSocketClient("ws://127.0.0.1:8084/netty");
            ws.connect();
            while (!ws.getReadyState().equals(ReadyState.OPEN)) {
                System.out.println("Connecting...");
                Thread.sleep(1000);
            }
            ws.sendQuery(movieId);
            while (ws.getRes() == null) {
                System.out.println("Processing...");
                Thread.sleep(1000);
            }
            reviews.addAll(ws.getRes().getReviewList());
            ws.close();
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        float sum = 0;
        int size = reviews.size();
        if (reviews != null) {
            for (Review review : reviews) {
                sum += review.getStars();
            }
        }
        return size == 0 ? 0 : sum / size;
    }
}
