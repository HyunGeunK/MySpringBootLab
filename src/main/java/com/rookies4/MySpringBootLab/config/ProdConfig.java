package com.rookies4.MySpringBootLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class ProdConfig {
    @Bean
    public MyEnviroment myEnviroment(){
        return new MyEnviroment("운영환경");
    }
}
