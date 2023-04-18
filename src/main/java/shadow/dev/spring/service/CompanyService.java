package shadow.dev.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.datatabase.repository.CrudRepository;
import shadow.dev.spring.dto.dto.CompanyReadDto;
import shadow.dev.spring.listeners.entity.AccessType;
import shadow.dev.spring.listeners.entity.EntityEvent;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private  final  UserService userService;
    private final CrudRepository<Integer, Company> companyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity,AccessType.READ));
                    return new CompanyReadDto(entity.getId());
                });

    }
}
