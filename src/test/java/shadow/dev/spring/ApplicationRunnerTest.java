package shadow.dev.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApplicationRunnerTest {

    @Autowired
    WebTestClient webTestClient;


    @Test
    void testActuator() {
        var bodyContentSpec = webTestClient.get()
                .uri("/actuator/info")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Reactive Spring App")
                .jsonPath("$.java").isNotEmpty()
                .jsonPath("$.java.version").isNotEmpty();

//
//                        .jsonPath("$.name").equals("Reactive Spring App")
//        var responseBody = bodyContentSpec.returnResult().getResponseBody();
//        System.out.println(Arrays.toString(responseBody));
        System.out.println(new String(bodyContentSpec.returnResult().getResponseBody()));
//        assertEquals(bodyContentSpec.json(""));
    }

    @Test
    void customHealth() {
        var bodyContentSpec = webTestClient.get()
                .uri("/actuator/health")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.components.custom").isNotEmpty()
                .jsonPath("$.components.custom.status").isEqualTo("UP")
                ;

//
//                        .jsonPath("$.name").equals("Reactive Spring App")
//        var responseBody = bodyContentSpec.returnResult().getResponseBody();
//        System.out.println(Arrays.toString(responseBody));
        System.out.println(new String(bodyContentSpec.returnResult().getResponseBody()));
//        assertEquals(bodyContentSpec.json(""));
    }
}