package com.example.invoice.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import util.NumberGenerator;
import util.TemplateResponse;

@Configuration
public class Config {

    @Bean
    NumberGenerator generator(){
        return new NumberGenerator();
    }

}
