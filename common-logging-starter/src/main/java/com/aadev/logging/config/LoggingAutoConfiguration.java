package com.aadev.logging.config;

import com.aadev.logging.aop.CommonPoincut;
import com.aadev.logging.aop.FirstAspect;
import com.aadev.logging.aop.SecondAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.common-logging",name = "enabled",havingValue = "true")
public class LoggingAutoConfiguration {

    @PostConstruct
    void init() {
      log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    public CommonPoincut commonPoincut() {
        return new CommonPoincut();
    }

    @Bean
    @Order(1)
    public FirstAspect firstAspect() {
        return new FirstAspect();
    }

    @Bean
    @Order(2)
    public SecondAspect secondAspect() {
        return new SecondAspect();
    }
}
