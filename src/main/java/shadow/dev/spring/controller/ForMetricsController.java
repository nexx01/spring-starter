//package shadow.dev.spring.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
//import java.time.Duration;
//
//@RestController
//public class ForMetricsController {
//
//    @GetMapping(
//            path = "/temperature-stream",
//            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Long> events() {
//        return Flux.interval(Duration.ofMillis(1000))
//                .name("temperature.stream")
//                .metrics();
//    }
//}
