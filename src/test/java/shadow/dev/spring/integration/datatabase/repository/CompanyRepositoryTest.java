package shadow.dev.spring.integration.datatabase.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.datatabase.entity.Company;

import javax.persistence.EntityManager;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
//@Commit
class CompanyRepositoryTest {

    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;
    @Test
    void findById() {
        transactionTemplate.executeWithoutResult(tx -> {
            var company = entityManager.find(Company.class, 1);
            assertNotNull(company);
            assertThat(company.getLocales()).hasSize(2);
        });
        //entityManager.getTransaction().begin();
    }

    @Test
    void save() {
        var company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "Apple орисание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);

        assertNotNull(company.getId());
    }
}