package com.tms.controller;

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

    public ResponseEntity<HttpStatus> createNews(@RequestBody News news) {
        Boolean result = newsService.createNews(news);
        if (!result) {
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
    public ResponseEntity<News> updateNews(@RequestBody News news) {
        Optional<News> newsUpdated = newsService.updateNews(news);
        if (newsUpdated.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newsUpdated.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsList, HttpStatus.OK);
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
