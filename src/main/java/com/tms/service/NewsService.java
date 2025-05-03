package com.tms.service;

import com.tms.model.dto.NewsResponseDto;

import com.tms.model.entity.News;
import com.tms.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Optional<List<NewsResponseDto>> getAllNews() {
        return Optional.of(newsRepository.findAll().stream()
                .map( newsRequestDto -> NewsResponseDto.builder()
                        .title(newsRequestDto.getTitle())
                        .imageNews(newsRequestDto.getImageNews())
                        .descriptionNews(newsRequestDto.getDescriptionNews())
                        .authorNewsId(newsRequestDto.getAuthorNewsId())
                        .build()
                ).toList());
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public Optional<NewsResponseDto> updateNews(News news) {
        return Optional.of(newsRepository.save(news))
                .map(newsRequestDto -> NewsResponseDto.builder()
                        .title(newsRequestDto.getTitle())
                        .imageNews(newsRequestDto.getImageNews())
                        .descriptionNews(newsRequestDto.getDescriptionNews())
                        .authorNewsId(newsRequestDto.getAuthorNewsId())
                        .build());
    }

    public Boolean deleteNews(Long id) {
        newsRepository.deleteById(id);
        return !newsRepository.existsById(id);
    }

    public Optional<NewsResponseDto> createNews(News news) {
        return Optional.of(newsRepository.save(news))
                .map( newsRequestDto -> NewsResponseDto.builder()
                        .title(newsRequestDto.getTitle())
                        .imageNews(newsRequestDto.getImageNews())
                        .descriptionNews(newsRequestDto.getDescriptionNews())
                        .authorNewsId(newsRequestDto.getAuthorNewsId())
                        .build());
    }
}
