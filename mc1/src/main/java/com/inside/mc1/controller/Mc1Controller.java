package com.inside.mc1.controller;

import com.inside.mc1.model.Message;
import com.inside.mc1.service.Mc1Service;
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
@RequestMapping("/mc1")
@RequiredArgsConstructor
@Slf4j
public class Mc1Controller {

    private final Mc1Service mc1Service;
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
     * Triggers the Mc1Service message
     * sending timer task.
     * @return
     */
    @GetMapping("/start")
    public String start() {
        mc1Service.startSendingTask();
        return "{ \"result\": \"OK\" }";
    }

    /**
     * Stops the Mc1Service message
     * sending timer task.
     * @return
     */
    @GetMapping("/stop")
    public String stop() {
        mc1Service.stopSendingTask();
        return "{ \"result\": \"OK\" }";
    }

    /**
     * Receives messages from the mc3 microservice, stores them
     * to the database and sends spans to Jaeger.
     * @param message
     * @return
     */
    @PostMapping("/communication")
    public String communication(@RequestBody Message message) {
        try (Scope scope = tracer.buildSpan("communication").startActive(true)) {
            log.info("Received message {}", message);
            mc1Service.saveToDb(message);
            Span span = scope.span();
            span.setTag("message", message.toString());
            return "{ \"result\": \"OK\" }";
        }
    }

}
