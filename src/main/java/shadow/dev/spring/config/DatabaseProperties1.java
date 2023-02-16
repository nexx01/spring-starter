package shadow.dev.spring.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class DatabaseProperties1 {
    String username;
    String password;
    String driver;
    String url;
    String hosts;
    DatabaseProperties1.PoolProperties pool;
    List<DatabaseProperties1.PoolProperties> pools;

    Map<String, Object> properties;

    @Data
    @NoArgsConstructor
    public static class PoolProperties {
        Integer size;
        Integer timeout;
    }

}
