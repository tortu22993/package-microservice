package com.enrique.apiGatewayPaquetes.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    public RestTemplate template(){
        return new RestTemplate();
    }
}
