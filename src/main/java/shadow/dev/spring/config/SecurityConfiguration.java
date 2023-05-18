package shadow.dev.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import shadow.dev.spring.datatabase.entity.Role;
import shadow.dev.spring.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(urlConfig->
                urlConfig
                        .antMatchers("/login","/users/registration",
                                "/v3/api-docs/**","/swagger-ui/**").permitAll()
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.getAuthority())
                        .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated());
        http.formLogin(login ->
                login.loginPage("/login")
                        .defaultSuccessUrl("/users")
                        .permitAll());
//        http.httpBasic(Customizer.withDefaults());
        http.logout(logout -> {
            logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID");
        });
//        http.csrf().disable();
//        http.oauth2Login();
        http.oauth2Login(config -> config
                .loginPage("/login")
                .defaultSuccessUrl("/users")
                .userInfoEndpoint(userInfo->userInfo.oidcUserService(oidUserService())))
                ;

    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            //todo: create user userService.create
            var userDetails = userService.loadUserByUsername(email);
//new OidcUserService().loadUser()
            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());


//            return new DefaultOidcUser(null, null);

            var userDetailsMethods = Set.of(UserDetails.class.getMethods());
            return  (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> {
                      return   userDetailsMethods.contains(method)
                                ? method.invoke(userDetails, args)
                                : method.invoke(oidcUser, args);
                    });
        };
    }


}
