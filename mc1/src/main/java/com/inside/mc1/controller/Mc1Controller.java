package com.inside.mc1.controller;

import com.inside.mc1.model.Message;
import com.inside.mc1.service.Mc1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mc1")
@RequiredArgsConstructor
@Slf4j
public class Mc1Controller {

    private final Mc1Service mc1Service;

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
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
