package shadow.dev.spring.datatabase.repository;

import org.springframework.stereotype.Repository;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

@Repository
public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

}
