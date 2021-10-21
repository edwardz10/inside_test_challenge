package com.inside.mc3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mc3")
public class Mc3Controller {

    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }
}
