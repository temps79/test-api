package com.example.testapi.service;

import com.example.testapi.dto.ArticleDto;
import com.example.testapi.dto.UserDto;
import com.example.testapi.entity.Article;
import com.example.testapi.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserDetailsService userDetailsService;

    public ResponseEntity<List<ArticleDto>> getList(int page, int size) {
        var pageRequest = PageRequest.of(page, size, Sort.unsorted());
        var list = articleRepository.findAll(pageRequest.previousOrFirst()).stream().map(this::convertEntityToDto).toList();
        return ResponseEntity.ok(list);
    }

    private ArticleDto convertEntityToDto(Article article) {
        ArticleDto articleDto = new ArticleDto(article);
        UserDetails userDetails = userDetailsService.loadUserByUsername(article.getPublisherUsername());
        if (userDetails != null) {
            articleDto.setPublisher(new UserDto(userDetails));
        }
        return articleDto;
    }

    public ResponseEntity<Long> saveArticle(Article article) {
        if (article.getContent() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found content");
        if (article.getPublishDate() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found publishDate");
        Article saveArticle = articleRepository.save(article);
        return ResponseEntity.ok(saveArticle.getId());
    }

    public ResponseEntity<ArticleDto> getData(Long id) {
        var optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            return ResponseEntity.ok(convertEntityToDto(article));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found article by id " + id);
    }

    List<Article> findAllByPublishDateBetween(Instant todayString, Instant sevenDaysBeforeString) {
        return articleRepository.findAllByPublishDateBetween(todayString, sevenDaysBeforeString);
    }

}
