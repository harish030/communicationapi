package com.example.communication.communicationapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @GetMapping("/test")
    public String getHelloWorld(){
        return "Hello World";
    }
}
