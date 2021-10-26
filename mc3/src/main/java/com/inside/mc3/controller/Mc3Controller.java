package com.inside.mc3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller class that communicates
 * with the outside world via HTTP.
 */
@RestController
@RequestMapping("/mc3")
public class Mc3Controller {

    /**
     * Health endpoint that indicates that
     * the microservice is up & running.
     * @return
     */
    @GetMapping("/health")
    public String health() {
        return "{ \"health\": \"OK\" }";
    }
}
