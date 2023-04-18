package shadow.dev.spring.datatabase.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import shadow.dev.spring.bpp.Auditing;
import shadow.dev.spring.bpp.Transaction;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Auditing
@Transaction
@Repository
@Scope(value = SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class CompanyRepository implements CrudRepository<Integer, Company> {

    @Value("${db.pool.size}")
    private final Integer poolSize;
    private final ConnectionPool pool1;
    private final List<ConnectionPool> connectionPools;

    @PostConstruct
    private void init() {
        System.out.println("init company repository...");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id,null, Collections.emptyMap()));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}
