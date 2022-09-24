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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatsControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    void unauthorizedNoAccessCountPublishArticleForSevenDays() {
        ResponseEntity<Map> result = template.getForEntity("/api/v1/stats", Map.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
    @Test
    void forbiddenNoAccessCountPublishArticleForSevenDays() {
        ResponseEntity<Map> result = template.withBasicAuth("user","userPass")
                .getForEntity("/api/v1/stats", Map.class);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }
    @Test
    void getCountPublishArticleForSevenDays() {
        ResponseEntity<Map> result = template.withBasicAuth("admin","adminPass")
                .getForEntity("/api/v1/stats", Map.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}