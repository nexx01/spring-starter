package shadow.dev.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.dto.dto.UserCreateEditDto;
import shadow.dev.spring.dto.dto.UserReadDto;
import shadow.dev.spring.integration.IntegrationTestBase;
import shadow.dev.spring.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT  extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Long COMPANY_1 = 1L;

    private final UserService userService;

    @Test
    void findAll() {
        var all = userService.findAll();
        assertThat(all).hasSize(5);
    }

    @Test
    void findById() {
        var maybeUser = userService.findById(USER_1);
        assertThat(maybeUser.isPresent());
        maybeUser.ifPresent(user->{
            assertThat(user.getUserName()).isEqualTo("ivan@gmail.com");

        });
    }

    @Test
    void create() {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
               new MockMultipartFile("test", new byte[0])
        );
        var actualResult = userService.create(userDto);
        assertThat(actualResult.getUserName()).isEqualTo(userDto.getUserName());
        assertThat(actualResult.getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(actualResult.getBirthDate()).isEqualTo(userDto.getBirthDate());
        assertThat(actualResult.getRole()).isSameAs(userDto.getRole());
    }

    @Test
    void update() {
        var userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])
        );

        var actualResult = userService.update(USER_1, userDto);
        Assertions.assertThat(actualResult).isPresent();

        actualResult.ifPresent(user->{
            assertThat(user.getUserName()).isEqualTo(userDto.getUserName());
            assertThat(user.getFirstName()).isEqualTo(userDto.getFirstName());
            assertThat(user.getBirthDate()).isEqualTo(userDto.getBirthDate());
            assertThat(user.getRole()).isSameAs(userDto.getRole());
        });


    }


    @Test
    void delete() {
        var result = userService.delete(USER_1);
        assertTrue(result);
        assertFalse(userService.delete(-124L));
    }
}
