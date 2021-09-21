package com.example.companyserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyServerApplication.class, args);
    }

}
