package com.cuhk.MovieHeaven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NettyMessage {
    private String username;
    private String msgId;
    private String msg;
    private int type;
}
