package com.inside.mc2.controller;

import com.inside.mc2.model.Message;
import com.inside.mc2.service.Mc2Service;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSocketController {

    private final Tracer tracer;
    private final Mc2Service mc2Service;

    @MessageMapping("/chat/{topic}")
    @SendTo("/topic/messages")
    public void send(@DestinationVariable("topic") String topic, Message message) throws Exception {
        log.info("Received a message: {}", message);
        try (Scope scope = tracer.buildSpan("communication").startActive(true)) {
            log.info("Received message {}", message);
            mc2Service.processMessage(message);
            Span span = scope.span();
            span.setTag("message", message.toString());
        }
    }
}
