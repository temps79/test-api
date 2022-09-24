package com.example.testapi.service;

import com.example.testapi.entity.Article;
import com.example.testapi.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class StatisticServiceTest {
    private final ArticleService articleService = mock(ArticleService.class);
    private final StatisticService statisticService = new StatisticService(articleService);


    @Test
    void checkCountPublishArticleForSevenDays1() {
        Article article1 = new Article(1L, "title", "author", Map.of("1", "1"), Instant.now().minus(1, ChronoUnit.DAYS));
        Article article2 = new Article(2L, "title", "author", Map.of("1", "1"), Instant.now().minus(2, ChronoUnit.DAYS));
        Mockito.when(articleService.findAllByPublishDateBetween(any(Instant.class),any(Instant.class))).thenReturn(List.of(article1,article2));
        var result=statisticService.getCountPublishArticleForSevenDays();
        assertEquals(result.getBody().size(),2);
    }
    @Test
    void checkOutsideItem() {
        Article article1 = new Article(1L, "title", "author", Map.of("1", "1"), Instant.now().minus(1, ChronoUnit.DAYS));
        Article article2 = new Article(2L, "title", "author", Map.of("1", "1"), Instant.now().minus(2, ChronoUnit.DAYS));
        Article article3 = new Article(3L, "title", "author", Map.of("1", "1"), Instant.now().minus(8, ChronoUnit.DAYS));
        Mockito.when(articleService.findAllByPublishDateBetween(any(Instant.class),any(Instant.class))).thenReturn(List.of(article1,article2));
        var result=statisticService.getCountPublishArticleForSevenDays();
        assertEquals(result.getBody().size(),2);
    }
    @Test
    void checkCountInGroup() {
        Article article1 = new Article(1L, "title", "author", Map.of("1", "1"), Instant.now().minus(1, ChronoUnit.DAYS));
        Article article2 = new Article(2L, "title", "author", Map.of("1", "1"), Instant.now().minus(2, ChronoUnit.DAYS));
        Article article3 = new Article(3L, "title", "author", Map.of("1", "1"), Instant.now().minus(1, ChronoUnit.DAYS));
        Mockito.when(articleService.findAllByPublishDateBetween(any(Instant.class),any(Instant.class))).thenReturn(List.of(article1,article2,article3));
        var result=statisticService.getCountPublishArticleForSevenDays();
        Instant checkInstant=Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        var resultMap=result.getBody();
        assertEquals(resultMap.get(checkInstant),2);
    }
}