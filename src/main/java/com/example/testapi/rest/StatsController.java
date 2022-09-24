package com.example.testapi.rest;

import com.example.testapi.service.ArticleService;
import com.example.testapi.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("api/v1/stats")
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN"})
public class StatsController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<Map<Instant,Integer>> getCountPublishArticleForSevenDays(){
        return statisticService.getCountPublishArticleForSevenDays();
    }

}
