package shadow.dev.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "db")
public record DatabaseProperties2(
        String username,
        String password,
        String driver,
        String url,
        String hosts,
        DatabaseProperties2.PoolProperties2 pool,
        List<DatabaseProperties2.PoolProperties2> pools,

        Map<String, Object> properties) {


    public static record PoolProperties2(
            Integer size,
            Integer timeout) {
    }

}

