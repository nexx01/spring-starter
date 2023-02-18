package shadow.dev.spring.service;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import shadow.dev.spring.pool.MeteredScheduledThreadPoolExecutor;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Random;
import java.util.logging.Level;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureSensor {
    private final MeterRegistry meterRegistry;
    private final Random rnd = new Random();

    private Flux<Temperature> dataStream;

    @PostConstruct
    public void init() {
        var executor = new MeteredScheduledThreadPoolExecutor
                ("temp.sensor", 3, meterRegistry);

        var eventSchedulers = Schedulers.fromExecutor(executor);

        dataStream = Flux
                .range(0, 10)
                .repeat()
                .concatMap(ignore ->
                        this.probe()
                                .delayElement(randomDelay(1000), eventSchedulers)
                                .name("temperature.measurement")
                                .log("temperature.masurement", Level.FINE)
                ).publish()
                .refCount();
        log.info("Temperature Sensor is ready");
    }

    public Flux<Temperature> temperatureStream() {
        return dataStream;
    }

    private Mono<Temperature> probe() {
        return Mono.fromCallable(() -> {
            var delay = randomDelay(100).toMillis();
            Thread.sleep(delay);
            return new Temperature(15 + rnd.nextGaussian() * 10);
        });
    }
    public Mono<Integer> batteryLevel() {
        return Mono.fromCallable(() -> {
            int level = rnd.nextInt(100);
            if (level <= 2 ) {
                throw new RuntimeException("Can not connect to the sensor");
            }
            return level;
        });
    }
    private Duration randomDelay(int maxMillis) {
        return Duration.ofMillis(rnd.nextInt(maxMillis));
    }
}
