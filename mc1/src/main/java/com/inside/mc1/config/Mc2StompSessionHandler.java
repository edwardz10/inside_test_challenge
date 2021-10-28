package com.inside.mc1.config;

import com.inside.mc1.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
@RequiredArgsConstructor
public class Mc2StompSessionHandler extends StompSessionHandlerAdapter {

    private final String topicName;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("STOMP session established");
        subscribeTopic(topicName, session);
    }

    private void subscribeTopic(String topic, StompSession session) {
        session.subscribe(topic, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                log.info(payload.toString());
            }
        });
    }
}

