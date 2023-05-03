package shadow.dev.spring.mapper;

import org.springframework.stereotype.Component;
import shadow.dev.spring.datatabase.entity.Company;
import shadow.dev.spring.dto.dto.CompanyReadDto;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }
}
