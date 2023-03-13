package com.cuhk.MovieHeaven.pojo;

import com.cuhk.MovieHeaven.entity.Review;

import lombok.Data;

@Data
public class InsertRequest extends NettyRequest {
    private Review review;

    public InsertRequest(Review review) {
        super(2);
        this.review = review;
    }
}
