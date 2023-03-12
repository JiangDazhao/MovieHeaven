package com.cuhk.MovieHeaven.pojo;

import java.util.ArrayList;
import java.util.List;

import com.cuhk.MovieHeaven.entity.Review;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NettyResponse {
    private int type;
    private List<Review> reviewList;

    public NettyResponse(int type, List<Review> reviewList) {
        this.type = type;
        if (type == 1) {
            this.reviewList = new ArrayList<>();
            this.reviewList.addAll(reviewList);
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "type= " + this.type +
                "reviewList= " + this.reviewList +
                '}';
    }
}
