package shadow.dev.spring.datatabase.repository;

import shadow.dev.spring.datatabase.pool.ConnectionPool;

public class CompanyRepository {

    private final ConnectionPool connectionPool;

    public CompanyRepository(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

}
