package com.ipn.mx.springbootwebceroaexperto.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
//@EnableScheduling
@EnableCaching
public class ApplicationConfig {
}
