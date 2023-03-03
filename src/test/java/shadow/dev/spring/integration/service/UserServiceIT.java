package shadow.dev.spring.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import shadow.dev.spring.annotation.IT;
import shadow.dev.spring.datatabase.pool.ConnectionPool;
import shadow.dev.spring.service.UserService;

@IT
@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT {

    private final UserService userService;
    private final ConnectionPool pool1;


    @Test
    void test() {
        System.out.println();
    }

    @Test
    void name() {
        System.out.println();
    }

}
