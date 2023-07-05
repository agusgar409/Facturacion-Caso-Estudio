package com.example.Items.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.NumberGenerator;

@Configuration
public class Config {
    @Bean
    NumberGenerator generator() {
        return new NumberGenerator();
    }

}
