package engine.security;

import engine.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
                 .csrf().disable()
                 .authorizeHttpRequests(auth -> auth
                         .mvcMatchers("/api/register").permitAll()
                         .mvcMatchers("/actuator/shutdown").permitAll()
                         .anyRequest().authenticated())
                 .userDetailsService(jpaUserDetailsService)
                 .httpBasic(Customizer.withDefaults())
                 .build();


         /*.authorizeRequests()
                 .mvcMatchers("/test").permitAll()
                 .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/actuator/shutdown").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

         return http.build();*/

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
