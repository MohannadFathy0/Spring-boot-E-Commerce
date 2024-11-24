package com.fog.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigration {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfigration(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Allow all origins. Adjust as needed.
        configuration.addAllowedMethod("*"); // Allow all HTTP methods.
        configuration.addAllowedHeader("*"); // Allow all headers.
        configuration.setAllowCredentials(false); // Allow credentials if needed.

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
                                .requestMatchers("/cart/**").authenticated()
                                .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/ws-offers/**").permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
