package shadow.dev.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.ClientResponseWrapper;
import reactor.core.publisher.Mono;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.web.ConfigWebConfiguration;

@Configuration(proxyBeanMethods = true)
@ComponentScan(useDefaultFilters = false,
        includeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Component.class),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
        })
//@ImportResource("classpath:application.xml")
@Import(ConfigWebConfiguration.class)
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

    @Bean
    @Profile("prod|web")
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

    @Bean
    public UserRepository userRepository3() {
        ConnectionPool connectionPool1 = pool3();
        ConnectionPool connectionPool2 = pool3();
        return new UserRepository(pool3());
    }

    @Bean
    public WebClient webClientFromConfiguration() {
      return   WebClient.create("http://localhost:8090");
    }

    @Bean
    public WebClient.Builder webClientFromBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8090")
                .defaultHeaders(header ->
                        header.setBasicAuth("userName", "password")
                )
                .defaultCookie("DEFAULT_COOKIE", "COOKIE_VALUE");
//                .build();
    }
}
