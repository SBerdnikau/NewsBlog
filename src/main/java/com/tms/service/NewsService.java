package com.tms.service;

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

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public Optional<News> updateNews(News news) {
        return Optional.of(newsRepository.save(news));
    }

    public Boolean deleteNews(Long id) {
        newsRepository.deleteById(id);
        return !newsRepository.existsById(id);
    }

    public Boolean createNews(News news) {
        try {
            newsRepository.save(news);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
