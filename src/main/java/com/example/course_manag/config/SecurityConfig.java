package com.example.course_manag.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basic -> {})
                .authorizeHttpRequests(auth -> auth

                        // Public — no credentials needed
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/chapters/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/instructors/**").permitAll()

                        // Admin only — user role management
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Instructor only — course & lesson write operations
                        .requestMatchers(HttpMethod.POST,   "/api/courses/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT,    "/api/courses/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.PATCH,  "/api/courses/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST,   "/api/chapters/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT,    "/api/chapters/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/chapters/**").hasRole("INSTRUCTOR")

                        // Instructor profile management
                        .requestMatchers(HttpMethod.POST,   "/api/instructors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/instructors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/instructors/**").hasRole("INSTRUCTOR")

                        // Enrollment — authenticated users
                        .requestMatchers("/api/enrollments/**").authenticated()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
