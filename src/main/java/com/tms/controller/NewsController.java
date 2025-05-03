package com.tms.controller;

import com.tms.model.dto.NewsResponseDto;
import com.tms.model.entity.News;
import com.tms.model.entity.User;
import com.tms.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody News news) {
        Optional<NewsResponseDto> newsResponseDto = newsService.createNews(news);
        if (newsResponseDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id")  Long newsId) {
        Optional<News> news = newsService.getNewsById(newsId);
        if (news.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(news.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<NewsResponseDto> updateNews(@RequestBody News news) {
        Optional<NewsResponseDto> newsUpdated = newsService.updateNews(news);
        if (newsUpdated.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newsUpdated.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsResponseDto>> getAllNews() {
        Optional<List<NewsResponseDto>> newsList = newsService.getAllNews();
        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsList.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") Long newsId) {
        Boolean result = newsService.deleteNews(newsId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
