package shadow.dev.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import shadow.dev.spring.datatabase.entity.Role;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(urlConfig->
                urlConfig
                        .antMatchers("/login","users/registration",
                                "/v3/api-docs/**","swagger-ui/**").permitAll()
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
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
