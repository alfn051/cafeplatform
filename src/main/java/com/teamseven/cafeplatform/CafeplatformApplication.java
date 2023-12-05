package com.teamseven.cafeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CafeplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeplatformApplication.class, args);
    }

}
