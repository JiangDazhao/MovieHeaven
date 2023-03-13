package com.cuhk.MovieHeaven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NettyRequest {
    private int type;

    public NettyRequest(int type) {
        this.type = type;
    }
}
