package com.example.app_incidencias_backend_v2.security;

import com.example.app_incidencias_backend_v2.security.jwt.JwtAuthenticationFilter;
import com.example.app_incidencias_backend_v2.security.jwt.JwtEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailService userDetailService;
    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtEntryPoint entryPoint;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailService userDetailService, JwtAuthenticationFilter authenticationFilter, JwtEntryPoint entryPoint, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.authenticationFilter = authenticationFilter;
        this.entryPoint = entryPoint;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/login").permitAll()
                .antMatchers("/api/v1/incidencias/**").hasAnyAuthority("ROLE_COMUN", "ROLE_ADMIN")
                .antMatchers("/api/v1/clientes/**").hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic()
                .and()
                .csrf().disable();

        return http.build();
    }
}
