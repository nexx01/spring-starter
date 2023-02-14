package shadow.dev.spring.datatabase.pool;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

import java.util.Map;


@Component("pool1")
//public class ConnectionPool {
@RequiredArgsConstructor
public class ConnectionPool {

    @Value("${db.username}")
    private final String username;

    @Value("${db.pool.size}")
    private final Integer poolSize;


    @PostConstruct
    private void init() {
        System.out.println("Init connection pool");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Clean connection pool");
    }

}
