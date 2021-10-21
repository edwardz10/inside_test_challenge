package com.inside.mc2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("session_id")
    private Integer sessionId;
    @JsonProperty("MC1_timestamp")
    private Date mc1Timestamp;
    @JsonProperty("MC2_timestamp")
    private Date mc2Timestamp;
    @JsonProperty("MC3_timestamp")
    private Date mc3Timestamp;
}
