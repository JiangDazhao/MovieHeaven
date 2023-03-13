package com.cuhk.MovieHeaven.pojo;

import java.util.ArrayList;
import java.util.List;

import com.cuhk.MovieHeaven.entity.Review;

import lombok.Data;

@Data
public class QueryResponse extends NettyResponse {
    private List<Review> reviewList;

    public QueryResponse(List<Review> reviewList) {
        super(1);
        this.reviewList = new ArrayList<>();
        this.reviewList.addAll(reviewList);
    }
}
