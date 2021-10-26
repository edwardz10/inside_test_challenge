package com.inside.mc2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inside.mc2.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Main working service of the application.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class Mc2Service {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    /**
     * Sets a timestamp to the message,
     * and sends it to Kafka
     * @param message
     */
    public void processMessage(Message message) {
        message.setMc2Timestamp(new Date());
        sendMessageToMc3ViaKafka(message);
    }

    @SneakyThrows
    private void sendMessageToMc3ViaKafka(Message message) {
        log.info("Done.");

        val listenableFuture = kafkaProducer.sendMessage("INPUT_DATA", "IN_KEY", objectMapper.writeValueAsString(message));

        SendResult<String, String> result = listenableFuture.get();
        log.info(String.format("Produced:\ntopic: {}\noffset: {}\npartition: {}\nvalue size: {}",
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().serializedValueSize()));
    }
}
