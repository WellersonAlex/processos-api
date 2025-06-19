/*
 * package com.wellerson.processo_api.security;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration;
 * 
 * 
 * @Configuration public class SecurityConfig {
 * 
 * @Autowired private JwtAuthFilter jwtAuthFilter;
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
 * Exception { http.csrf().disable() .authorizeHttpRequests(auth -> auth
 * .requestMatchers( "/auth/login", "/v3/api-docs/**", "/swagger-ui/**",
 * "/swagger-ui.html" ).permitAll() .anyRequest().authenticated() )
 * .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
 * 
 * return http.build(); } }
 * 
 */