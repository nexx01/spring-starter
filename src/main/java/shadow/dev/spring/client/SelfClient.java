package shadow.dev.spring.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shadow.dev.spring.datatabase.entity.Company;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
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
                                Mono.error(new RuntimeException("ERROR_MSG"))
                )
                .bodyToMono(Company.class)
                .subscribe(d-> System.out.println("sentCompany : "+d));


    }

    /**
     * Reads the complete file in-memory. Thus, only useful for very large file
     */
    public void downloadUsingByteArray() throws IOException {
        Mono<byte[]> monoContents = webClientFromBuilder
//                .baseUrl(props.getFileServerUrl())
                .build()
                .get()
                .uri("/largefiles/1")
                .retrieve()
                .bodyToMono(byte[].class);

        Files.write(
                Path.of("largefile/Mono"),
                Objects.requireNonNull(monoContents.share().block()),
                StandardOpenOption.CREATE);

    }

    /**
     * Reading file using Mono will try to fit the entire file into the DataBuffer.
     * Results in exception when the file is larger than the DataBuffer capacity.
     */
    public void downloadUsingMono(Path destination) {
        Mono<DataBuffer> dataBuffer = webClientFromBuilder.build()
                .get()
                .uri("/largefiles/1")
                .retrieve()
                .bodyToMono(DataBuffer.class);

        DataBufferUtils.write(dataBuffer, destination,
                        StandardOpenOption.CREATE)
                .share().block();
    }


    /**
     * Having using Flux we can download files of any size safely.
     * Optionally, we can configure DataBuffer capacity for better memory utilization.
     */
    public void downloadUsingFlux() throws IOException {

        var webClient = WebClient.builder()
                .baseUrl("http://localhost:8182")
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer ->
                                configurer.defaultCodecs()
                                        .maxInMemorySize(2 * 1024)
                        )
                        .build())
                .build();

//        DataBufferUtils.
        Flux<DataBuffer> dataBuffer = webClient
                .get()
                .uri("/largefiles/1")
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        DataBufferUtils.write(dataBuffer,  Path.of("largefile/Flux"),
                        StandardOpenOption.CREATE)
                .share().block();

    }

}
