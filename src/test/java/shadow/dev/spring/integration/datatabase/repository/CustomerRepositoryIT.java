package shadow.dev.spring.integration.datatabase.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Transactional;
import shadow.dev.spring.datatabase.entity.Customer;
import shadow.dev.spring.datatabase.repository.CustomerRepository;
import shadow.dev.spring.integration.IntegrationTestBase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class CustomerRepositoryIT extends IntegrationTestBase {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        entityManager.persist(new Customer("A","A@example.com"));
        entityManager.persist(new Customer("D",null));
        entityManager.persist(new Customer("D","D@example.com"));
    }

    @Test
    void whenRequestWithNullParametr_shouldOnlyOneResult() {
        var customers = customerRepository.findByNameAndEmail("D", null);

        assertEquals(1, customers.size());

        Customer actual = customers.get(0);

        assertEquals(null,actual.getEmail());
        assertEquals("D",actual.getName());
    }

    @Test
    void whenWeAvoidNullParametr_thenAllResult() {
        var customers = customerRepository.findByName("D");
        assertEquals(2, customers.size());
    }

    @Test

    void whenWeAvoidNullParametrQithQuery_thenAllResult() {
        var customers = customerRepository.findCustomerByNameAndEmail("D", null);
        assertEquals(2,customers.size());
    }
}
