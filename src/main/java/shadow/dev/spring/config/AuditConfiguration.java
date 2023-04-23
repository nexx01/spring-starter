package shadow.dev.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import shadow.dev.spring.ApplicationRunner;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        //SecurityContext.getCurrentUser.getEmail()
        return () -> Optional.of("dmdev");
    }
}
