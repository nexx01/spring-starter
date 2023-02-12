package shadow.dev.spring.datatabase.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import shadow.dev.spring.bpp.Auditing;
import shadow.dev.spring.bpp.InjectBean;
import shadow.dev.spring.bpp.Transaction;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.pool.ConnectionPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Auditing
@Transaction
public class CompanyRepository implements CrudRepository<Integer, Company> {

    @Autowired
//    public void setPool1(ConnectionPool pool1) {
//        this.pool1 = pool1;
//    }

    @Value("${db.pool.size}")
    private Integer poolSize;

    //    @Autowired
//    @Qualifier("pool1")
//    @Resource(name = "pool2")
    private ConnectionPool pool1;
    @Autowired
    private List<ConnectionPool> connectionPools;

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
