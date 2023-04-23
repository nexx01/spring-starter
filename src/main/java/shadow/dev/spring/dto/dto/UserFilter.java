package shadow.dev.spring.dto.dto;

import java.time.LocalDate;

public record UserFilter(String firsName,
                         String lastName,
                         LocalDate birthDate) {
}
