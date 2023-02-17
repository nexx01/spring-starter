package shadow.dev.spring;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import shadow.dev.spring.client.SelfClient;
import shadow.dev.spring.datatabase.entity.Company;

@SpringBootApplication
@RequiredArgsConstructor
public class ApplicationRunner {

//    private final SelfClient selfClient;

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class);
        System.out.println(context.getEnvironment().getProperty("server.port"));
        System.out.println(context.getBean("pool1"));

        var selfClient = (SelfClient) context.getBean("selfClient");
        selfClient.init();
        selfClient.getInfoWithWebClientConfiguration();
        selfClient.getInfoWithWebClientFromBuilder();

        selfClient.sentCompany(new Company(1));


    }

}
