package com.inside.mc2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
@Slf4j
public class WebSocketEventListener implements ApplicationListener<SessionConnectEvent> {
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        log.info("SessionConnectEvent: {}", event);
    }
}