package shadow.dev.spring.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import shadow.dev.spring.condition.JPACondition;

import javax.annotation.PostConstruct;

@Conditional(JPACondition.class)
@Configuration
public class JpaConfiguration {

    @PostConstruct
    void init() {
        System.out.println("Jpa configuration is enable");
    }
}
