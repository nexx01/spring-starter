package shadow.dev.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class);
        System.out.println(context.getBean("pool1"));
    }

}
