package com.tms.service;

import com.tms.model.dto.NewsResponseDto;

import com.tms.model.entity.News;
import com.tms.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final SecurityService securityService;

    @Autowired
    public NewsService(NewsRepository newsRepository, SecurityService securityService) {
        this.newsRepository = newsRepository;
        this.securityService = securityService;
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
        if(securityService.canAccessNews(news.getAuthorNewsId())) {
        return Optional.of(newsRepository.save(news))
                .map(newsRequestDto -> NewsResponseDto.builder()
                            .title(newsRequestDto.getTitle())
                            .imageNews(newsRequestDto.getImageNews())
                            .descriptionNews(newsRequestDto.getDescriptionNews())
                            .authorNewsId(newsRequestDto.getAuthorNewsId())
                            .build());
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + news.getAuthorNewsId());
    }

    public Boolean deleteNews(Long id) {
        if(securityService.canAccessNews(id)) {
            newsRepository.deleteById(id);
            return !newsRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Optional<NewsResponseDto> createNews(News news) {
        if(securityService.canAccessNews(news.getAuthorNewsId())) {
            return Optional.of(newsRepository.save(news))
                    .map(newsRequestDto -> NewsResponseDto.builder()
                            .title(newsRequestDto.getTitle())
                            .imageNews(newsRequestDto.getImageNews())
                            .descriptionNews(newsRequestDto.getDescriptionNews())
                            .authorNewsId(newsRequestDto.getAuthorNewsId())
                            .build());
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + news.getAuthorNewsId());
    }
}
