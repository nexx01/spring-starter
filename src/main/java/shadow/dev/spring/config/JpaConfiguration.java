package shadow.dev.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import shadow.dev.spring.config.condition.JPACondition;

import javax.annotation.PostConstruct;

@Conditional(JPACondition.class)
@Configuration

public class JpaConfiguration {

//    @Bean
//    @ConfigurationProperties(prefix = "db")
//    public DatabaseProperties1 databaseProperties1() {
//        return new DatabaseProperties1();
//    }
    @PostConstruct
    void init() {
        System.out.println("Jpa configuration is enable");
    }
}
