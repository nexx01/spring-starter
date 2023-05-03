package shadow.dev.spring.dto.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import shadow.dev.spring.datatabase.entity.Role;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {
     String userName;

     @DateTimeFormat(pattern = "yyyy-MM-dd")
     LocalDate birthDate;
    String firstName;
    String lastName;
    Role role;
    Long companyId;
}
