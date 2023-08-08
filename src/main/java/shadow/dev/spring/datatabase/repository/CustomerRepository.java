package shadow.dev.spring.datatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shadow.dev.spring.datatabase.entity.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


    List<Customer> findByNameAndEmail(String name, String email);

    List<Customer> findByName(String name);

    @Query("SELECT c FROM Customer c WHERE (:name IS NULL or c.name=:name)" +
            " and (:email is null or c.email=:email)")
    List<Customer> findCustomerByNameAndEmail(@Param("name") String name,
                                              @Param("email") String email);


}
