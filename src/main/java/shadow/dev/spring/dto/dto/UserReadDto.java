package shadow.dev.spring.dto.dto;

import lombok.Value;
import shadow.dev.spring.datatabase.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Long id;
    String userName;
    LocalDate birthDate;
    String firstName;
    String lastName;
    String image;
    Role role;
    CompanyReadDto company;
}
