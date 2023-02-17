package shadow.dev.spring.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shadow.dev.spring.datatabase.entity.Company;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.function.Predicate;

import static reactor.core.publisher.Signal.error;

@Service
@RequiredArgsConstructor
public class SelfClient {
    private final WebClient webClientFromConfiguration;
    private final WebClient.Builder webClientFromBuilder;


//    @PostConstruct
    public void init() {
        WebClient webClient = WebClient
                .create("http://localhost:8090");

        webClient.get()
                .uri("actuator/info")
                .retrieve()
                .bodyToMono(String.class)
                .doOnEach(stringSignal -> {
                    System.out.println(stringSignal.get());
                })
                .subscribe(System.out::println);

    }

    public void getInfoWithWebClientConfiguration() {
        webClientFromConfiguration.get()
                .uri("actuator/info")
                .retrieve()
                .bodyToMono(String.class)
                .doOnEach(stringSignal -> {
                    System.out.println(stringSignal.get());
                })
                .subscribe(System.out::println);

    }


    public void getInfoWithWebClientFromBuilder() {
        var path = "info";

        webClientFromBuilder.build()

                .get()
//                .uri("actuator/info")
//                .uri(URI.create("/users/" + userId))
//                .uri("/users/" + userId)
//                .uri(uriBuilder ->
//                        uriBuilder.pathSegment("users", "{userId}")
//                                .build(userId))
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("actuator", "{path}")
                                .build(path))
                .retrieve()
                .bodyToMono(String.class)
                .doOnEach(stringSignal -> {
                    System.out.println(stringSignal.get());
                })
                .subscribe(System.out::println);

    }

    public void sentCompany(Company company) {
        webClientFromBuilder.build()
                .post()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("", "{company}").build("company"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(company))

                .retrieve()
                .onStatus(
                        Predicate.not(HttpStatus::is2xxSuccessful), clientResponse ->
                                Mono.error(new RuntimeException(ERROR_MSG))
                )
                .bodyToMono(Company.class)
                .subscribe(d-> System.out.println("sentCompany : "+d));


    }

}
