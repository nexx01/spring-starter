package shadow.dev.spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shadow.dev.spring.datatabase.entity.User;
import shadow.dev.spring.dto.dto.CompanyReadDto;
import shadow.dev.spring.dto.dto.UserReadDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {
        var company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstName(),
                object.getLastName(),
                object.getRole(),
                company
        );
    }
}
