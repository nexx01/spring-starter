package shadow.dev.spring.dto.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.validation.UserInfo;
import shadow.dev.spring.validation.group.CreateAction;
import shadow.dev.spring.validation.group.UpdateAction;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = UpdateAction.class)
public class UserCreateEditDto {
    @Email

     String userName;

     @DateTimeFormat(pattern = "yyyy-MM-dd")
     LocalDate birthDate;

    @NotNull
    @Size(min = 3, max = 64)
    String firstName;

     @NotNull
    String lastName;
    Role role;
    Long companyId;
}
