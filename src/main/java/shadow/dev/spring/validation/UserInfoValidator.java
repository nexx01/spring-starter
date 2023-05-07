package shadow.dev.spring.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import shadow.dev.spring.dto.dto.UserCreateEditDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UserInfoValidator implements ConstraintValidator<UserInfo, UserCreateEditDto> {

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        return StringUtils.isNotBlank(value.getFirstName())
                || StringUtils.isNotBlank(value.getLastName());
    }
}
