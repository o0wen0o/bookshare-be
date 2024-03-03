package com.fyp.bookshare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fyp.bookshare.mapper")
public class BookshareBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookshareBeApplication.class, args);
    }

}
