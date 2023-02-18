package shadow.dev.spring;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableAdminServer
public class ApplicationRunner {
    private final MeterRegistry meterRegistry;

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class);
        System.out.println(context.getBean("pool1"));
    }

//    public void init() {
//        Hooks.onNextDropped(c -> meterRegistry.counter("reactor.dropped.events").increment());
//        Schedulers.setFactory(new MeteredSchedulersFactory(meterRegistry));
//        log.info("Updated Scheduler factory with a custom instance");
//    }

}
