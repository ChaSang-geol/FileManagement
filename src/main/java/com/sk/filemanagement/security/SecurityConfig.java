package com.sk.filemanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable());
                // .cors(cors -> cors.disable());
        http.cors();
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // .antMatchers("/signin/**").permitAll()
                .antMatchers("/api/status").permitAll()
                .anyRequest().authenticated())

                // .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
                // .antMatchers("/api/files/*").authenticated()
                // .and()

                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
