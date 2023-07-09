package com.momentum.momentumtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MomentumTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomentumTestApplication.class, args);
    }

}
