package shadow.dev.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.web.ConfigWebConfiguration;

@Configuration(proxyBeanMethods = true)
@ComponentScan(useDefaultFilters = false,
        includeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Component.class),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CompanyRepository.class),
                @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
        })
//@ImportResource("classpath:application.xml")
@Import(ConfigWebConfiguration.class)
//@EnableJpaRepositories("com\\..+Repository")
public class ApplicationConfiguration {
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }


    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ConnectionPool pool3() {
        return new ConnectionPool("test-pool", 20);
    }

}
