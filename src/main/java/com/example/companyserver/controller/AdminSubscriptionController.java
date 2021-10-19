package com.example.companyserver.controller;

import com.example.companyserver.service.InfoCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminSubscriptionController {

    public final InfoCompanyService infoCompanyService;

    @PostMapping("/save/quotes")
    public HttpStatus saveAllQuotes() {
        infoCompanyService.saveQuotes();
        return HttpStatus.OK;
    }

    @PostMapping("/save/metrics")
    public HttpStatus saveAllMetrics() {
        infoCompanyService.saveMetrics();
        return HttpStatus.OK;
    }
}
