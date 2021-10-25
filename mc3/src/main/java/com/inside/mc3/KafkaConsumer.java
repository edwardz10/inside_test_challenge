package com.inside.mc3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inside.mc3.model.Message;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final Tracer tracer;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${application.mc1Endpoint}")
    private String mc1Endpoint;

    private static final HttpHeaders httpHeaders;

    static {
        httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", MediaType.APPLICATION_JSON_VALUE);
    }

    @KafkaListener(topics = {"INPUT_DATA"})
    @SneakyThrows
    public void consume(final @Payload String message,
                        final @Header(KafkaHeaders.OFFSET) Integer offset,
                        final @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        final @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        final @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        final @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        try (Scope scope = tracer.buildSpan("communication").startActive(true)) {
            val messageObject = objectMapper.readValue(message, Message.class);
            log.info("#### -> Consumed message -> TIMESTAMP: {}\n{}\noffset: {}\nkey: {}\npartition: {}\ntopic: {}", ts, messageObject, offset, key, partition, topic);
            Span span = scope.span();
            span.setTag("message", messageObject.toString());
            messageObject.setMc3Timestamp(new Date());
            sendMessageToMc1(messageObject);
        }
    }

    public void sendMessageToMc1(Message message) {
        try {
            val request = new HttpEntity<>(message, httpHeaders);
            val response = restTemplate.exchange(mc1Endpoint, HttpMethod.POST, request, String.class);
            log.info("Successfully sent a message to Mc1, received {}", response.getBody());
        } catch (RestClientException e) {
            log.error("Failed to send a message to MC1", e);
        }
    }

}