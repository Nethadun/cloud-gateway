package com.microservice.cloudgateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RouteController {
    @GetMapping("/")
    public String index(Principal principal){
        return principal.getName();
    }

    @GetMapping("/fallback/response/message")
    public ResponseEntity<String> subscribesFallbackMethod(Exception e) {
        return new ResponseEntity<>("There were some error in connecting. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
