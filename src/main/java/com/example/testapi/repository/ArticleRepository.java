package com.example.testapi.repository;

import com.example.testapi.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article,Long> {
    @Override
    Page<Article> findAll(Pageable pageable);

    List<Article> findAllByPublishDateBetween(Instant todayString, Instant sevenDaysBeforeString);

}
