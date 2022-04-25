package com.github.fashionbrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ValidatedSpringbootStarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatedSpringbootStarterTestApplication.class,args);
    }

}
