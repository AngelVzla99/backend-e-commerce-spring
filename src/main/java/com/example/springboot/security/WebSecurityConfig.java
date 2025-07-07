package com.example.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(UserDetailsService userDetailsService, JWTAuthorizationFilter jwtAuthorizationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

//        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
//        jwtAuthenticationFilter.setAuthenticationManager(authManager);
//        jwtAuthenticationFilter.setFilterProcessesUrl("/login-delete");

        return http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                // public endpoints
                .requestMatchers(
                        "/api/user/customer", //  this makes public all the endpoints under /cus ?
                        "/api/products/search-by-text/**",
                        "/api/products/most-popular",
                        "/api/products/best-sales",
                        "/docs/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/health",
                        "/api/auth/login",
//                        "ws-order", // TODO: add authentication
                        "/api/auth/reset-password-email",
                        "/api/auth/reset-password"
                )
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/{id}")
                .permitAll()
                // private endpoints
                .anyRequest()
                .authenticated()
                .and()
                // user sessions
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // JWT authentication and logic to validate the token
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
