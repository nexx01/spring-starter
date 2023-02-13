package shadow.dev.spring.datatabase.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import shadow.dev.spring.bpp.Auditing;
import shadow.dev.spring.bpp.Transaction;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Auditing
@Transaction
@Repository
@Scope(value = SCOPE_PROTOTYPE)
public class CompanyRepository implements CrudRepository<Integer, Company> {

    private Integer poolSize;
    private ConnectionPool pool1;
    private List<ConnectionPool> connectionPools;

    public CompanyRepository(@Value("${db.pool.size}") Integer poolSize,
                             ConnectionPool pool1,
                             List<ConnectionPool> connectionPools) {
        this.poolSize = poolSize;
        this.pool1 = pool1;
        this.connectionPools = connectionPools;
    }

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
