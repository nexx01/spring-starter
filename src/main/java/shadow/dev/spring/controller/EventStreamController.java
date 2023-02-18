package shadow.dev.spring.controller;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import shadow.dev.spring.service.Temperature;
import shadow.dev.spring.service.TemperatureSensor;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EventStreamController {

    private final MeterRegistry meterRegistry;
    private final TemperatureSensor temperatureSensor;

    private AtomicInteger activeStream;

    @PostConstruct
    public void init() {
        activeStream = meterRegistry.gauge("sse.stream", new AtomicInteger(0));
    }

    @GetMapping(path = "/temperature-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Temperature> events() {
        return temperatureSensor.temperatureStream()
                .doOnSubscribe(subs -> activeStream.incrementAndGet())
                .name("temperature.sse-stream")
                .metrics()
                .log("sse.temperature", Level.FINE)
                .doOnCancel(() -> activeStream.decrementAndGet())
                .doOnTerminate(() -> activeStream.decrementAndGet());
    }
}
