package com.micro.sale.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import util.CalculateTotal;
import util.NumberGenerator;

/**
 * @author jrodriguez
 */
@Configuration
public class Config {
    @Bean
    NumberGenerator generator() {
        return new NumberGenerator();
    }

    @Bean
    CalculateTotal total() {
        return new CalculateTotal();
    }

}
