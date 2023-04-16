package shadow.dev.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.http.client.MockClientHttpRequest;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.dto.dto.CompanyReadDto;
import shadow.dev.spring.listeners.entity.EntityEvent;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    public static final Integer COMPANY_ID = 1;

    @Spy
    @InjectMocks
    private CompanyService companyService;

    @Mock
    private UserService userService;
    @Mock
    private CrudRepository<Integer, Company> companyRepository;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Company(COMPANY_ID,null, Collections.emptyMap())))
                .when(companyRepository).findById(COMPANY_ID);

        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        Mockito.verify(applicationEventPublisher).publishEvent(Mockito.any(EntityEvent.class));
        Mockito.verifyNoMoreInteractions(applicationEventPublisher,userService);
    }

}
