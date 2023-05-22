package com.aadev.logging.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Data
@NoArgsConstructor
@ConfigurationProperties("app.common-logging")
@Slf4j
public class LoggingProperties {

    /**
    *  to enable common logging aop on service layer
    */
    private boolean enabled;
    private String level;

    @PostConstruct
    void init() {
        log.info("logging properties initialazing {}", this);
    }

}
