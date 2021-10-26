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

import java.util.Timer;

/**
 * Main working service of the application.
 */
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
    private Timer timer;

    private int messageCount;
    private long interactionTime;

    /**
     * Starts the SendMessageTask as a timer.
     */
    public void startSendingTask() {
        timer = new Timer();
        sendMessageTask = new SendMessageTask(mc2Endpoint);
        timer.scheduleAtFixedRate(sendMessageTask, 0, sendingIntervalInSecs*1000);
        messageCount = 0;
        interactionTime = System.currentTimeMillis();
        log.info("SendMessage task started at the interval uf 3 seconds");
    }

    /**
     * Stops the SendMessageTask
     */
    public void stopSendingTask() {
        timer.cancel();
        log.info("SendMessage task stopped");
        log.info("Messages sent: {}", messageCount);
        log.info("Interaction time: {} milliseconds", System.currentTimeMillis() - interactionTime);
    }

    /**
     * Converts a Message object to a MessageEntity,
     * and stores it to the DB.
     * @param message
     */
    public void saveToDb(Message message) {
        messageCount++;
        var messageEntity = convert(message);
        log.info("Created a message entity: {}", messageEntity);
        messageEntity = messageRepository.save(messageEntity);
        log.info("Saved {} into the database", messageEntity);
    }

    /**
     * Converts a Message to a MessageObject.
     * @param message
     * @return
     */
    private MessageEntity convert(Message message) {
        val messageEntity = new MessageEntity();
        messageEntity.setSessionId(message.getSessionId());
        messageEntity.setMc1Timestamp(message.getMc1Timestamp());
        messageEntity.setMc2Timestamp(message.getMc2Timestamp());
        messageEntity.setMc3Timestamp(message.getMc3Timestamp());
        return messageEntity;
    }
}
