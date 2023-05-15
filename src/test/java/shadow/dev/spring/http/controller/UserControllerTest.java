package shadow.dev.spring.http.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.dto.dto.UserCreateEditDto;
import shadow.dev.spring.dto.dto.UserCreateEditDto.Fields;
import shadow.dev.spring.integration.IntegrationTestBase;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shadow.dev.spring.dto.dto.UserCreateEditDto.Fields.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @BeforeEach
    void init() {
//        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
//        var testUser = new User("test@gmail.com", "test", roles);
//        var authenticationToken = new TestingAuthenticationToken
//                (testUser, testUser.getPassword(), roles);
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authenticationToken);
//        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .with(
                                SecurityMockMvcRequestPostProcessors.user(
                                        "test@gmail.com").authorities(Role.ADMIN)
                        )
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                .andExpect(model().attributeExists("users"))
        ;
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                .param(Fields.userName,"test@gmail.com")
                .param(firstName,"Test")
                .param(Fields.lastName,"TestTest")
                .param(role,"ADMIN")
                .param(companyId,"1")
                .param(birthDate,"2000-01-01")
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/users/{\\d+}")
        );
    }
}