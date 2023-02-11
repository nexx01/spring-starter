package shadow.dev.spring.datatabase.repository;

import shadow.dev.spring.bpp.Auditing;
import shadow.dev.spring.bpp.InjectBean;
import shadow.dev.spring.bpp.Transaction;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Auditing
@Transaction
public class CompanyRepository implements CrudRepository<Integer, Company> {
    @InjectBean
    private ConnectionPool connectionPool;

    @PostConstruct
    private void init() {
        System.out.println("init company repository...");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}
