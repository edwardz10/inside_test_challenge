package com.inside.mc2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mc2")
public class Mc2Controller {

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }
}
