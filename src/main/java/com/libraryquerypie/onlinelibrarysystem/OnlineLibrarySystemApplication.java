package com.libraryquerypie.onlinelibrarysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class OnlineLibrarySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineLibrarySystemApplication.class, args);
    }

}
