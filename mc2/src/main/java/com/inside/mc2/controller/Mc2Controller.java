package com.inside.mc2.controller;

import com.inside.mc2.model.Message;
import com.inside.mc2.service.Mc2Service;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller class that communicates
 * with the outside world via HTTP.
 */
@RestController
@RequestMapping("/mc2")
@Slf4j
@RequiredArgsConstructor
public class Mc2Controller {

    private final Mc2Service mc2Service;
    private final Tracer tracer;

    /**
     * Health endpoint that indicates that
     * the microservice is up & running.
     * @return
     */
    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }

    /**
     * Receives messages from the mc1 microservice, sends them
     * to Kafka and sends spans to Jaeger.
     * @param message
     * @return
     */
    @PostMapping("/communication")
    public String communication(@RequestBody Message message) {
        try (Scope scope = tracer.buildSpan("communication").startActive(true)) {
            log.info("Received message {}", message);
            mc2Service.processMessage(message);
            Span span = scope.span();
            span.setTag("message", message.toString());
            return "{ \"result\": \"OK\" }";
        }
    }

}
