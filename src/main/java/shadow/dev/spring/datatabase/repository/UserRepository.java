package shadow.dev.spring.datatabase.repository;

import shadow.dev.spring.datatabase.pool.ConnectionPool;

public class UserRepository {

    private final ConnectionPool connectionPool;

    public UserRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

}
