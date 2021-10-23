package com.inside.mc1.service;

import com.inside.mc1.entity.MessageEntity;
import com.inside.mc1.model.Message;
import com.inside.mc1.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Timer;

@Service
@Slf4j
@RequiredArgsConstructor
public class Mc1Service {

    private final MessageRepository messageRepository;

    @Value("${application.mc2Endpoint}")
    private String mc2Endpoint;
    @Value("${application.sendingIntervalInSecs}")
    private Integer sendingIntervalInSecs;

    private SendMessageTask sendMessageTask;
    private Timer timer = new Timer();

    @PostConstruct
    public void setup() {
        sendMessageTask = new SendMessageTask(mc2Endpoint);
    }

    public void startSendingTask() {
        timer.scheduleAtFixedRate(sendMessageTask, 0, sendingIntervalInSecs*1000);
        log.info("SendMessage task started at the interval uf 3 seconds");
    }

    public void stopSendingTask() {
        timer.cancel();
        log.info("SendMessage task stopped");
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
