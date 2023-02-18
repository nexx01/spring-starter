package shadow.dev.spring.actuator;


import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.Random;

public class AppModelContributor implements InfoContributor {
    private final Random rnd = new Random();
    @Override
    public void contribute(Info.Builder builder) {
        boolean appMode = rnd.nextBoolean();
        builder
                .withDetail("application-mode", appMode ? "experimental" : "stable");
    }
}
