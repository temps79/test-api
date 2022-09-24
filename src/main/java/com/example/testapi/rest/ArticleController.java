package com.example.testapi.rest;

import com.example.testapi.entity.Article;
import com.example.testapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "",consumes={"application/json;charset=UTF-8"})
    public ResponseEntity<Long> addArticle(@Valid @RequestBody Article article){
        return articleService.saveArticle(article);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "",params = { "page", "size" })
    public ResponseEntity<List<Article>> list(@RequestParam("page") int page,
                                              @RequestParam("size") int size){
        return articleService.getList(page,size);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<Article> getData(@PathVariable("id") Long id){
        return articleService.getData(id);
    }
}
