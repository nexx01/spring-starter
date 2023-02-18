package shadow.dev.spring.actuator.health;

import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import shadow.dev.spring.service.TemperatureSensor;

@RequiredArgsConstructor
@Component
class SensorBatteryHealthIndicator implements ReactiveHealthIndicator {
    private final TemperatureSensor temperatureSensor;

    @Override
    public Mono<Health> health() {
        return temperatureSensor
                .batteryLevel()
                .map(level -> {
                    if (level > 40) {
                        return new Health.Builder()
                                .up()
                                .withDetail("level", level)
                                .build();
                    } else {
                        return new Health.Builder()
                                .status(new org.springframework.boot.actuate.health.Status("Low Battery"))
                                .withDetail("level", level)
                                .build();
                    }
                }).onErrorResume(err -> Mono.
                        just(new Health.Builder()
                                .outOfService()
                                .withDetail("error", err.getMessage())
                                .build())
                );
    }
}