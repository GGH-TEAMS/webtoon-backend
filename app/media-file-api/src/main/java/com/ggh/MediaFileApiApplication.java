package com.ggh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MediaFileApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaFileApiApplication.class, args);
    }

}
