package com.example.testapi;

import com.example.testapi.entity.Article;
import com.example.testapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.Map;

@SpringBootApplication
public class TestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestApiApplication.class, args);
	}

}
