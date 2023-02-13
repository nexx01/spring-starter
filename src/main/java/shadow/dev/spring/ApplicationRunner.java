package shadow.dev.spring;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import shadow.dev.spring.config.ApplicationConfiguration;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.service.CompanyService;

import java.io.Serializable;

public class ApplicationRunner {

    public static void main(String[] args) {
        var value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));
        System.out.println(Serializable.class.isAssignableFrom(value.getClass()));

        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.getEnvironment().setActiveProfiles("web","prod");
            context.refresh();
            var connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            var companyService = context.getBean("companyService", CompanyService.class);
            System.out.println(companyService.findById(1));
        }
    }

}
