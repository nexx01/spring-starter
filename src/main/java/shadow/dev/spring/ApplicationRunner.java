package shadow.dev.spring;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.spring.ioc.Container;
import shadow.dev.spring.service.UserService;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

public class ApplicationRunner {

    public static void main(String[] args) {
        var value = "hello";
        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
        System.out.println(BeanFactoryPostProcessor.class.isAssignableFrom(value.getClass()));
        System.out.println(Serializable.class.isAssignableFrom(value.getClass()));

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {
            var connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);
            var companyRepository = context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
    }

}
