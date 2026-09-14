package com.example.democrud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class LatencyController {

    @GetMapping("/fraud")
    public String fraudCheck(
            @RequestParam(defaultValue = "20") long delayMs)
            throws InterruptedException {
System.out.println("executing this request "+Thread.currentThread().getName());
        Thread.sleep(delayMs);

        return "fraud-check-complete";
    }
}