package com.cuhk.MovieHeaven.service;

import com.cuhk.MovieHeaven.client.MyWebSocketClient;
import com.cuhk.MovieHeaven.entity.Review;
import com.cuhk.MovieHeaven.pojo.QueryResponse;

import org.java_websocket.enums.ReadyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    private static final String URL1 = "ws://127.0.0.1:8084/netty1";
    private static final String URL2 = "ws://127.0.0.1:8085/netty2";

    public float calAveScore(int movieId) {
        Map<Integer, Integer> reviewMap = new HashMap<>();
        List<Review> list1 = new ArrayList<>();
        List<Review> list2 = new ArrayList<>();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyWebSocketClient ws = new MyWebSocketClient(URL1);
                    ws.connect();
                    while (!ws.getReadyState().equals(ReadyState.OPEN)) {
                        System.out.println("[1] Connecting...");
                        Thread.sleep(1);
                    }
                    ws.queryReviews(movieId);
                    int i = 0;
                    while (ws.getRes() == null) {
                        System.out.println("[1] Processing...");
                        Thread.sleep(1);
                        i++;
                        if (i >= 5000) {
                            System.out.println("Timeout!!!");
                            break;
                        }
                    }
                    QueryResponse res = (QueryResponse) ws.getRes();
                    list1.addAll(res.getReviewList());
                    ws.close();
                } catch (URISyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyWebSocketClient ws = new MyWebSocketClient(URL2);
                    ws.connect();
                    while (!ws.getReadyState().equals(ReadyState.OPEN)) {
                        System.out.println("[2] Connecting...");
                        Thread.sleep(1);
                    }

                    ws.queryReviews(movieId);
                    int i = 0;
                    while (ws.getRes() == null) {
                        System.out.println("[2] Processing...");
                        Thread.sleep(1);
                        i++;
                        if (i >= 5000) {
                            System.out.println("Timeout!!!");
                            break;
                        }
                    }
                    QueryResponse res = (QueryResponse) ws.getRes();
                    list2.addAll(res.getReviewList());
                    ws.close();
                } catch (URISyntaxException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Review r : list1) {
            reviewMap.put(r.getReviewId(), r.getStars());
        }
        for (Review r : list2) {
            reviewMap.put(r.getReviewId(), r.getStars());
        }

        float sum = 0;
        int size = reviewMap.size();
        if (reviewMap != null) {
            for (Integer star : reviewMap.values()) {
                sum += star;
            }
        }
        return size == 0 ? 0 : sum / size;
    }
}
