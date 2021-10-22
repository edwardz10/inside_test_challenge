package com.inside.mc2.service;

import com.inside.mc2.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class Mc2Service {

    private final KafkaProducer kafkaProducer;

    public void processMessage(Message message) {
        message.setMc2Timestamp(new Date());
        sendMessageToMc3ViaKafka(message);
    }

    @SneakyThrows
    private void sendMessageToMc3ViaKafka(Message message) {
        log.info("Done.");

        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaProducer.sendMessage("INPUT_DATA", "IN_KEY", LocalDate.now().toString());

        SendResult<String, String> result = listenableFuture.get();
        log.info(String.format("Produced:\ntopic: {}\noffset: {}\npartition: {}\nvalue size: {}",
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset(),
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().serializedValueSize()));
    }
}
