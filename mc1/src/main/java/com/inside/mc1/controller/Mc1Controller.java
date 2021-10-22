package com.inside.mc1.controller;

import com.inside.mc1.service.Mc1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mc1")
@RequiredArgsConstructor
public class Mc1Controller {

    private final Mc1Service mc1Service;

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }

    @GetMapping("/start")
    public String start() {
        mc1Service.sendMessageToMc2();
        return "{ \"health\": \"OK\" }";
    }

    @GetMapping("/stop")
    public String stop() {
        return "{ \"health\": \"OK\" }";
    }
}
