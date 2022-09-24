package com.example.testapi.service;

import com.example.testapi.entity.Article;
import com.example.testapi.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class ArticleServiceTest {
    private final ArticleRepository articleRepository = mock(ArticleRepository.class);
    private final ArticleService articleService = new ArticleService(articleRepository);
    private final Article article = new Article(1L, "title", "author", Map.of("1", "1"), Instant.now());

    @Test
    void getEmptyList() {
        int page = 1;
        int size = 1;
        Page<Article> articles = new PageImpl<>(new ArrayList<>());
        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(articles);
        var result = articleService.getList(page, size);
        assertEquals(articles.getSize(), result.getBody().size());
    }

    @Test
    void getList() {
        int page = 1;
        int size = 1;
        Page<Article> articles = new PageImpl<>(List.of(article));
        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(articles);
        var result = articleService.getList(page, size);
        var resultArticle = Objects.requireNonNull(result.getBody()).get(0);
        assert (article.equals(resultArticle));
    }

    @Test
    void notFoundArticle() {
        int page = 2;
        int size = 1;
        Page<Article> articles = new PageImpl<>(List.of());
        Mockito.when(articleRepository.findAll(any(Pageable.class))).thenReturn(articles);
        var result = articleService.getList(page, size);
        assert (result.getBody().size() == 0);
    }


    @Test
    void notFoundContentArticle() {
        Article copyArticle = article;
        copyArticle.setContent(null);
        assertThrows(ResponseStatusException.class,()->articleService.saveArticle(copyArticle));
    }
    @Test
    void notFoundPublishDateArticle() {
        Article copyArticle = article;
        copyArticle.setPublishDate(null);
        assertThrows(ResponseStatusException.class,()->articleService.saveArticle(copyArticle));
    }
    @Test
    void saveArticle() {
        Mockito.when(articleRepository.save(any(Article.class))).thenReturn(article);
        articleService.saveArticle(article);
    }

    @Test
    void getData() {
        Mockito.when(articleRepository.findById(any(Long.class))).thenReturn(Optional.of(article));
        articleService.getData(1L);
    }
    @Test
    void notFoundArticleData() {
        Mockito.when(articleRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,()->articleService.getData(1L));
    }

    @Test
    void testfindAllByPublishDateBetween(){
        Article article = new Article(1L, "title", "author", Map.of("1", "1"), Instant.now());
        Instant today=Instant.now().plus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        Instant sevenDaysBefore=today.minus(7,ChronoUnit.DAYS);
        Mockito.when(articleRepository.findAllByPublishDateBetween(any(Instant.class),any(Instant.class))).thenReturn(List.of(article));
        var list=articleService.findAllByPublishDateBetween(today,sevenDaysBefore);
        assertEquals(list.size(),1);
    }


}