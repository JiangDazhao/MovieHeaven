package com.cuhk.MovieHeaven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NettyRequest {
    private int type;
    private int movieId;

    public NettyRequest(int type, int movieId) {
        this.type = type;
        if (type == 1) {
            this.movieId = movieId;
        }
    }
}
