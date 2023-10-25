package com.example.blog.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtConfigurationFilter JwtAuthenticationFilter;

    private final AuthenticationProvider AuthenticationProvider;

    private static final Logger logger=LoggerFactory.getLogger(SecurityConfig.class);
    public SecurityConfig( JwtConfigurationFilter jwtAuthenticationFilter,
                          AuthenticationProvider authenticationProvider) {
        this.JwtAuthenticationFilter = jwtAuthenticationFilter;
        this.AuthenticationProvider = authenticationProvider;
    }

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/",
            "/swagger",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            //"/api/v1/files/**",
            "/api/v1/blogPosts"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    throws  Exception {

        logger.info("Filter chain is executing");
        httpSecurity
                .headers(h->h.disable())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(requests->
                        requests.requestMatchers( AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(AuthenticationProvider)
                .addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.httpBasic(Customizer.withDefaults())
                 ;

        return httpSecurity.build();
    }
}
