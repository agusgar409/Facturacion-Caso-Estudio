package com.example.gatewayfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GatewayFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayFilterApplication.class, args);
    }

}
