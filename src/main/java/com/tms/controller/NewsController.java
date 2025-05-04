package com.tms.controller;

import com.tms.model.dto.NewsResponseDto;
import com.tms.model.entity.News;
import com.tms.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
@Tag(name = "News Controller", description = "News Management")
public class NewsController {
    private final NewsService newsService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Operation(summary = "Create news", description = "Adds a new news to the system")
    @ApiResponse(responseCode = "201", description = "News successfully created")
    @ApiResponse(responseCode = "409", description = "Conflict: News not created")
    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody News news) {
        Optional<NewsResponseDto> newsResponseDto = newsService.createNews(news);
        if (newsResponseDto.isEmpty()) {
            logger.error("Failed to create news: {}", news);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("News created successfully: {}", newsResponseDto.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get news by ID", description = "Returns a news by their unique ID")
    @ApiResponse(responseCode = "200", description = "News found")
    @ApiResponse(responseCode = "404", description = "News not found")
    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") @Parameter(description = "News ID") Long newsId) {
        logger.info("Received request to fetch news with ID: {}", newsId);
        Optional<News> news = newsService.getNewsById(newsId);
        if (news.isEmpty()) {
            logger.warn("News with ID {} not found", newsId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("News successfully fetched: {}", news);
        return new ResponseEntity<>(news.get(), HttpStatus.OK);
    }

    @Operation(summary = "Update news", description = "Updates news information")
    @ApiResponse(responseCode = "200", description = "News updated successfully")
    @ApiResponse(responseCode = "409", description = "Conflict when updating news")
    @PutMapping
    public ResponseEntity<NewsResponseDto> updateNews(@RequestBody News news) {
        logger.info("Received request to update news: {}", news);
        Optional<NewsResponseDto> newsUpdated = newsService.updateNews(news);
        if (newsUpdated.isEmpty()) {
            logger.error("Failed to update news: {}", news);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("News updated successfully: {}", newsUpdated.get());
        return new ResponseEntity<>(newsUpdated.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all news", description = "Return all news")
    @ApiResponse(responseCode = "200", description = "News list successfully retrieved")
    @ApiResponse(responseCode = "204", description = "The news list is empty")
    @GetMapping("/all")
    public ResponseEntity<List<NewsResponseDto>> getAllNews() {
        logger.info("Received request to fetch all news");
        Optional<List<NewsResponseDto>> newsList = newsService.getAllNews();
        if (newsList.isEmpty()) {
            logger.warn("News not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("News successfully fetched: Quantity {}", newsList.get().size());
        return new ResponseEntity<>(newsList.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete news", description = "Deletes news by ID")
    @ApiResponse(responseCode = "204", description = "News successfully deleted")
    @ApiResponse(responseCode = "409", description = "Error deleting news")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") @Parameter(description = "News ID") Long newsId) {
        logger.info("Received request to delete news with ID: {}", newsId);
        Boolean result = newsService.deleteNews(newsId);
        if (!result) {
            logger.warn("Failed to delete news with ID {}", newsId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("News with ID {} deleted successfully", newsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
