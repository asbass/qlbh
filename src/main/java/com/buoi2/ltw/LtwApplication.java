package com.buoi2.ltw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication

public class LtwApplication {

    public static void main(String[] args) {
        SpringApplication.run(LtwApplication.class, args);
    }

}
