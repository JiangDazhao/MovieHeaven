package com.cuhk.MovieHeaven.pojo;

import lombok.Data;

@Data
public class QueryRequest extends NettyRequest {
    private int movieId;

    public QueryRequest(int movieId) {
        super(1);
        this.movieId = movieId;
    }
}
