package com.example.testapi.service;

import com.example.testapi.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final ArticleService articleService;

    public ResponseEntity<Map<Instant, Integer>> getCountPublishArticleForSevenDays() {
        Instant today=Instant.now().plus(1,ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        Instant sevenDaysBefore=today.minus(7,ChronoUnit.DAYS);
        List<Article> articles=articleService.findAllByPublishDateBetween(sevenDaysBefore,today);
        var instantIntegerMap=articles.stream()
                .collect(Collectors.groupingBy(Article::getPublishDate))
                .entrySet()
                .stream().map(instantListEntry  -> new AbstractMap.SimpleEntry<>(instantListEntry.getKey().truncatedTo(ChronoUnit.DAYS), instantListEntry.getValue().size()))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        return ResponseEntity.ok(instantIntegerMap);
    }

}
