package com.inside.mc1.service;

import com.inside.mc1.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.TimerTask;

@RequiredArgsConstructor
@Slf4j
public class SendMessageTask extends TimerTask {

    private final String mc2Endpoint;
    private final RestTemplate restTemplate = new RestTemplate();

    private static int count = 0;
    private static final HttpHeaders httpHeaders;

    static {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public void run() {
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

    public Message generateMessage() {
        int currentCount = ++count;
        return Message.builder()
                .sessionId(currentCount)
                .mc1Timestamp(new Date())
                .build();
    }
}
