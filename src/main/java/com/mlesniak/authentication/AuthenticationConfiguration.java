package com.mlesniak.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class AuthenticationConfiguration {
    @Bean
    @SessionScope
    public User getUser() {
        return new User();
    }
}
