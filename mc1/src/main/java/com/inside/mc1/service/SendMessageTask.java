package com.inside.mc1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.TimerTask;

import static com.inside.mc1.util.Mc1Utils.generateMessage;

/**
 * A task that extends the TimerTask class.
 */
@RequiredArgsConstructor
@Slf4j
public class SendMessageTask extends TimerTask {

    private final StompSession stompSession;
    private final String destination;

    /**
     * Generates a Message, and sends in to the mc2 microservice.
     */
    @Override
    public void run() {
        val message = generateMessage();
        log.info("Generated a message: {}", message);
        stompSession.send(destination, message);
    }
}
