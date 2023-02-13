package shadow.dev.spring.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.web.ConfigWebConfiguration;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "shadow.dev.spring",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Component.class),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, value = CrudRepository.class),
                @Filter(type = FilterType.REGEX, pattern = "com\\..+Repository")
        })
//@ImportResource("classpath:application.xml")
@Import(ConfigWebConfiguration.class)
public class ApplicationConfiguration {
}
