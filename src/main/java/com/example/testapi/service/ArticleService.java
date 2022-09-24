package com.example.testapi.service;

import com.example.testapi.entity.Article;
import com.example.testapi.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public ResponseEntity<List<Article>> getList(int page, int size) {
        var pageRequest = PageRequest.of(page, size, Sort.unsorted());
        var list = articleRepository.findAll(pageRequest.previousOrFirst());
        return ResponseEntity.ok(list.getContent());
    }

    public ResponseEntity<Long> saveArticle(Article article) {
        if (article.getContent() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found content");
        if (article.getPublishDate() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found publishDate");
        Article saveArticle = articleRepository.save(article);
        return ResponseEntity.ok(saveArticle.getId());
    }

    public ResponseEntity<Article> getData(Long id) {
        var article = articleRepository.findById(id);
        if (article.isPresent()) {
            return ResponseEntity.ok(article.get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found article by id " + id);
    }

    List<Article> findAllByPublishDateBetween(Instant todayString, Instant sevenDaysBeforeString){
        return articleRepository.findAllByPublishDateBetween(todayString,sevenDaysBeforeString);
    }

}
