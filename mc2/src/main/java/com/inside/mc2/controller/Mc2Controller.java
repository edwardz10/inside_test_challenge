package com.inside.mc2.controller;

import com.inside.mc2.model.Message;
import com.inside.mc2.service.Mc2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mc2")
@Slf4j
@RequiredArgsConstructor
public class Mc2Controller {

    private final Mc2Service mc2Service;

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }

    @PostMapping("/communication")
    public String communication(@RequestBody Message message) {
        log.info("Received message {}", message);
        mc2Service.processMessage(message);
        return "{ \"result\": \"OK\" }";
    }

}
