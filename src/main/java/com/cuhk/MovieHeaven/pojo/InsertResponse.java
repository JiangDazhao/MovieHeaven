package com.cuhk.MovieHeaven.pojo;

import lombok.Data;

@Data
public class InsertResponse extends NettyResponse {
    private int rowNum;

    public InsertResponse(int rowNum) {
        super(2);
        this.rowNum = rowNum;
    }
}
