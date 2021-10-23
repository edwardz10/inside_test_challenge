package com.inside.mc1.service;

import com.inside.mc1.entity.MessageEntity;
import com.inside.mc1.model.Message;
import com.inside.mc1.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class Mc1Service {

    private final MessageRepository messageRepository;

    private int count = 0;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${application.mc2Endpoint}")
    private String mc2Endpoint;

    private static final HttpHeaders httpHeaders;

    static {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
    }

    public Message generateMessage() {
        int currentCount = ++count;
        return Message.builder()
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

    public void saveToDb(Message message) {
        var messageEntity = convert(message);
        log.info("Created a message entity: {}", messageEntity);
        messageEntity = messageRepository.save(messageEntity);
        log.info("Saved {} into the database", messageEntity);
    }

    private MessageEntity convert(Message message) {
        val messageEntity = new MessageEntity();
        messageEntity.setSessionId(message.getSessionId());
        messageEntity.setMc1Timestamp(message.getMc1Timestamp());
        messageEntity.setMc2Timestamp(message.getMc2Timestamp());
        messageEntity.setMc3Timestamp(message.getMc3Timestamp());
        return messageEntity;
    }
}
