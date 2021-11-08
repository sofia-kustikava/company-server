package com.example.companyserver.controller;

import com.example.companyserver.dto.CompanyDto;
import com.example.companyserver.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracking")
public class TrackingController {

    public final TrackingService trackingService;

    @PostMapping("/add/{symbol}")
    public ResponseEntity<String> addTrackingCompany(@PathVariable("symbol") String symbol) {
        trackingService.addUserCompany(symbol);
        return new ResponseEntity<>("You chose company with symbol " + symbol, HttpStatus.OK);
    }

    @GetMapping("/track/companies")
    public List<CompanyDto> getTrackingCompanies() {
        return trackingService.getUserCompanies();
    }
}
