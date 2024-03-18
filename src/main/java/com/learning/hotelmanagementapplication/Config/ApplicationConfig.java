package com.learning.hotelmanagementapplication.Config;

import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public BCryptPasswordEncoder  bCryptPasswordEncoderCreator(){
        return new BCryptPasswordEncoder();
    }
}
