package com.inside.mc1.service;

import com.inside.mc1.model.Message;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
public class Mc1Service {

    private int count = 0;
    private final String mc2Endpoint = "http://mc2:9010/mc2/communication";
    private final RestTemplate restTemplate = new RestTemplate();

    private static final HttpHeaders httpHeaders;

    static {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
    }

    public Message generateMessage() {
        int currentCount = count++;
        return Message.builder()
                .id(currentCount)
                .sessionId(currentCount)
                .mc1Timestamp(new Date())
                .build();
    }

    public void sendMessageToMc2() {
        try {
            val message = generateMessage();
            log.info("Generated a message: {}", message);
            val request = new HttpEntity<>(message, httpHeaders);
            val response = restTemplate.exchange(mc2Endpoint, HttpMethod.POST, request, String.class);
            log.info("Successfully sent a message to Mc2, received {}", response.getBody());
        } catch (RestClientException e) {
            log.error("Failed to send a message to MC2", e);
        }
    }
}
