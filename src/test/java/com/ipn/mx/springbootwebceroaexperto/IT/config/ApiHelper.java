package com.ipn.mx.springbootwebceroaexperto.IT.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ApiHelper {

    @Bean
    public TestRestTemplate gestRestTempla() {
        return new TestRestTemplate(
                new RestTemplateBuilder()
                        .basicAuthentication("spring", "spring")
                        .connectTimeout(java.time.Duration.ofSeconds(10))
        );
    }


}
