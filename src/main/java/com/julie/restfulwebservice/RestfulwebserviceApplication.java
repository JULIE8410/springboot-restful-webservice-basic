package com.julie.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulwebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulwebserviceApplication.class, args);
    }

    @Bean  // 다국어 처리를 위한 internationalization 구현
    public SessionLocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.CANADA);
        return localeResolver;
    }

}
