package com.inside.mc1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mc1")
public class Mc1Controller {

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }
}
