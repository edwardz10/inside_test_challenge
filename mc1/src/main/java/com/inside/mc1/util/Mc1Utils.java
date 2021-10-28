package com.inside.mc1.util;

import com.inside.mc1.model.Message;

import java.util.Date;

public class Mc1Utils {
    private static int count = 0;

    public static Message generateMessage() {
        int currentCount = ++count;
        return Message.builder()
                .sessionId(currentCount)
                .mc1Timestamp(new Date())
                .build();
    }
}

