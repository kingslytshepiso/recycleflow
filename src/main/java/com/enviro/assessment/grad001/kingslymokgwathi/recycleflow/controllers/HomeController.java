package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<?> home() {
        return ResponseEntity.ok().body("Welcome to RecycleFlow API.");
    }

}
