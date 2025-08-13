package com.rookies4.MySpringBootLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {
    @Bean
    public MyEnviroment myEnviroment(){
        return new MyEnviroment("개발환경");
    }
}
