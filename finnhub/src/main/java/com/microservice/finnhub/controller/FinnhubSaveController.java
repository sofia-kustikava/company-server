package com.microservice.finnhub.controller;

import com.microservice.finnhub.service.SaveFinnhubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/save")
public class FinnhubSaveController {
    private final SaveFinnhubService saveService;

    @PostMapping("/quote")
    public ResponseEntity<String> saveAllQuotes() {
        saveService.saveQuotes();
        return new ResponseEntity<>("All quotes were successfully saved", HttpStatus.OK);
    }

    @PostMapping("/metric")
    public ResponseEntity<String> saveAllMetrics() {
        saveService.saveMetrics();
        return new ResponseEntity<>("All quotes were successfully saved", HttpStatus.OK);
    }
}
