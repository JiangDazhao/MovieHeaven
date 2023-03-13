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

    public NettyResponse(int type) {
        this.type = type;
    }
}
