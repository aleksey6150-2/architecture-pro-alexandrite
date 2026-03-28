package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ServiceAResource {


    private final RestClient restClient = RestClient.create();;

    @GetMapping("/")
    public String root() {
        String resp = restClient.get()
                .uri("http://service-b:8080/")
                .retrieve()
                .body(String.class);
        return "service-a -> " + resp;
    }
}
