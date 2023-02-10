package shadow.dev.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.datatabase.repository.CompanyRepository;
import shadow.dev.spring.datatabase.repository.UserRepository;
import shadow.dev.spring.ioc.Container;
import shadow.dev.spring.service.UserService;

public class ApplicationRunner {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("application.xml");
        var connectionPool = context.getBean("pool1", ConnectionPool.class);
        System.out.println(connectionPool);
        var companyRepository = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(companyRepository);
    }

}
