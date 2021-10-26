package com.inside.mc1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Message class for HTTP level.
 */
@Builder
@Data
public class Message {
    @JsonProperty("session_id")
    private Integer sessionId;
    @JsonProperty("MC1_timestamp")
    private Date mc1Timestamp;
    @JsonProperty("MC2_timestamp")
    private Date mc2Timestamp;
    @JsonProperty("MC3_timestamp")
    private Date mc3Timestamp;
}
