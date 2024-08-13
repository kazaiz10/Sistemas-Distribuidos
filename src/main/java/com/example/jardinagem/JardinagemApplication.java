package com.example.jardinagem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.jardinagem")
public class JardinagemApplication {

    public static void main(String[] args) {
        SpringApplication.run(JardinagemApplication.class, args);
    }

}
