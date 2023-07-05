package com.purchase.sale.invoicing.customer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

@TestConfiguration
@EnableJpaRepositories
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.purchase.sale.invoicing.customer.domain")
public class H2Config {

    @Bean
    public DataSource dataSourceWithEmbeddedDatabaseBuilder() {
        return new EmbeddedDatabaseBuilder()
                .setName("test")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

}
