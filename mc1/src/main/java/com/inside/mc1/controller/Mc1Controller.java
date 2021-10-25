package com.inside.mc1.controller;

import com.inside.mc1.model.Message;
import com.inside.mc1.service.Mc1Service;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/mc1")
@RequiredArgsConstructor
@Slf4j
public class Mc1Controller {

    private final Mc1Service mc1Service;
    private final Tracer tracer;

    @GetMapping("/health")
    public String health() {

        try (Scope scope = tracer.buildSpan("say-hello-handler").startActive(true)) {
            Span span = scope.span();
            Map<String, String> fields = new LinkedHashMap<>();
            fields.put("event", "event");
            fields.put("message", "this is a log message for name ");
            span.log(fields);
            // you can also log a string instead of a map, key=event value=<stringvalue>
            // span.log("this is a log message for name " + name);
            span.setBaggageItem("my-baggage", "name");
            String response = "{ \"health\": \"OK\" }";
            span.setTag("response", response);
            return response;
        }
    }

    @GetMapping("/start")
    public String start() {
        mc1Service.startSendingTask();
        return "{ \"result\": \"OK\" }";
    }

    @GetMapping("/stop")
    public String stop() {
        mc1Service.stopSendingTask();
        return "{ \"result\": \"OK\" }";
    }

    @PostMapping("/communication")
    public String communication(@RequestBody Message message) {
        log.info("Received message {}", message);
        mc1Service.saveToDb(message);
        return "{ \"result\": \"OK\" }";
    }

}
