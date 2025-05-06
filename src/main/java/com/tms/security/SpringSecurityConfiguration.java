package com.tms.security;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SpringSecurityConfiguration {
    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers(
                    "/v3/api-docs/swagger-config",
                    "/v3/api-docs");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(new AntPathRequestMatcher("/user", "PUT")).hasAnyRole("ADMIN","MODERATOR" ,"USER")
                                .requestMatchers(new AntPathRequestMatcher("/user", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/user", "DELETE")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/user/**", "GET")).hasRole("ADMIN")

                                .requestMatchers(new AntPathRequestMatcher("/news", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/news", "POST")).hasAnyRole("ADMIN", "MODERATOR")
                                .requestMatchers(new AntPathRequestMatcher("/news", "PUT")).hasAnyRole("ADMIN", "MODERATOR")
                                .requestMatchers(new AntPathRequestMatcher("/news", "DELETE")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/news/**", "GET")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/comment", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comment", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comment", "PUT")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/comment", "DELETE")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/comment/**", "GET")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/registration", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/security", "PUT")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/security", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/security/token", "POST")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/file", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/file", "POST")).hasAnyRole("ADMIN", "MODERATOR")
                                .requestMatchers(new AntPathRequestMatcher("/file", "DELETE")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/file/**", "GET")).permitAll()

                                .requestMatchers(new AntPathRequestMatcher("/image", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/image", "POST")).hasAnyRole("ADMIN", "MODERATOR")
                                .requestMatchers(new AntPathRequestMatcher("/image", "DELETE")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/image/**", "GET")).hasRole("ADMIN")

                                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
