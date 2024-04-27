package com.lcwd.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());


        return docket;
    }

    private ApiInfo getApiInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Backend : APIS",
                "This is backend project created by sarvesh",
                "1.0.0V",
                "https://www.linkedin.com/in/sarvesh-dutt/",
                new Contact("sarvesh","https://www.linkedin.com/in/sarvesh-dutt/","sarveshdutt1999@gmail.com"),
                "License of APIS",
                "https://www.linkedin.com/in/sarvesh-dutt/",
                new ArrayList<>()
        );



        return apiInfo;
    }

}
