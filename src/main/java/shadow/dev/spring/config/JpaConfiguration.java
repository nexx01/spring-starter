package shadow.dev.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import shadow.dev.spring.config.condition.JPACondition;

import javax.annotation.PostConstruct;

@Conditional(JPACondition.class)
@Configuration
@Slf4j
public class JpaConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "db")
//    public DatabaseProperties1 databaseProperties1() {
//        return new DatabaseProperties1();
//    }
    @PostConstruct
    void init() {
        log.info("Jpa configuration is enable");
    }
}
