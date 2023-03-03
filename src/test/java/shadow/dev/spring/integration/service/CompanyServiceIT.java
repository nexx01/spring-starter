package shadow.dev.spring.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shadow.dev.spring.ApplicationRunner;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.config.DatabaseProperties;
import shadow.dev.spring.config.DatabaseProperties1;
import shadow.dev.spring.config.DatabaseProperties2;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.dto.dto.CompanyReadDto;
import shadow.dev.spring.listeners.entity.EntityEvent;
import shadow.dev.spring.service.CompanyService;

import java.util.Optional;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class,
//        initializers = ConfigDataApplicationContextInitializer.class)
//@SpringBootTest
//@ActiveProfiles("test")

@IT
@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CompanyServiceIT {

    final CompanyService companyService;

    final DatabaseProperties2 databaseProperties2;

    public static final Integer COMPANY_ID = 1;


    @Test
    void findById() {
        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }

}
