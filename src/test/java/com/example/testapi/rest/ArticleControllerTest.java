package com.example.testapi.rest;

import com.example.testapi.entity.Article;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    void unauthorizedAddArticle() {
        final String username = "user";
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), username, Instant.now());
        article.setId(null);
        assertThrows(ResourceAccessException.class, () -> template.postForEntity("/api/v1/article", article, Article.class));
    }

    @Test
    void foreordainedAddArticle() {
        final String username = "user";
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), username, Instant.now());
        article.setId(null);
        ResponseEntity<Article> result = template.withBasicAuth("user", "userPass")
                .postForEntity("/api/v1/article", article, Article.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void addArticle() {
        final String username = "admin";
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), username, Instant.now());
        article.setId(null);
        ResponseEntity<Long> result = template.withBasicAuth("admin", "adminPass")
                .postForEntity("/api/v1/article", article, Long.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void noValidTitleAddArticle() {
        final String username = "user";
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), username, Instant.now());
        article.setId(null);
        article.setTitle("s".repeat(101));
        assertThrows(RestClientException.class, () -> template.withBasicAuth("admin", "adminPass")
                .postForEntity("/api/v1/article", article, Long.class));
    }

    @Test
    void getData() {
        ResponseEntity<Article> result = template.getForEntity("/api/v1/article/100", Article.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void list() {
        ResponseEntity<List> result = template.getForEntity("/api/v1/article?page=1&size=1", List.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getSuccessData() {
        final String username = "admin";
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), username, Instant.now());
        template.withBasicAuth(username, "adminPass")
                .postForEntity("/api/v1/article", article, Long.class);
        ResponseEntity<Article> result = template.getForEntity("/api/v1/article/1", Article.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}