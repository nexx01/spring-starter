package shadow.dev.spring;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class MetricsTest {

    @Test
    void name() {
        var subscribe = Flux.interval(Duration.ofMillis(1000))
                .name("someStream")
                .metrics()

                .subscribe(System.out::println);

        while (!subscribe.isDisposed()) {

        }
    }
}
