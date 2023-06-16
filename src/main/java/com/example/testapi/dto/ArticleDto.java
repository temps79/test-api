package com.example.testapi.dto;

import com.example.testapi.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ArticleDto {
    private Long id;

    private String title;

    private String author;


    private Map<String, String> content;


    private String publisherUsername;
    private UserDto publisher;

    private Instant publishDate;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.author = article.getAuthor();
        this.content = article.getContent();
        this.publisherUsername = article.getPublisherUsername();
        this.publishDate = article.getPublishDate();
    }
}
