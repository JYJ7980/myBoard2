package com.example.queryMethodTest.service;


import com.example.queryMethodTest.Dto.ArticleDto;
import com.example.queryMethodTest.entity.Article;
import com.example.queryMethodTest.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void insertArticle(ArticleDto dto) {
//        Article article = dto.fromMemberDto(dto);
        Article article = Article.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .build();
        articleRepository.save(article);
    }

    public List<ArticleDto> showAllArticles() {
        List<ArticleDto> articleDtoList =new ArrayList<>();
        return articleRepository.findAll()
                .stream()
                .map(x -> ArticleDto.fromArticleEntity(x))
                .toList();
    }

    public void updateArticle(ArticleDto dto) {
        Article article = dto.fromMemberDto(dto);
        articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }else
        return ArticleDto.fromArticleEntity(articleRepository.findById(id).orElse(null));
    }

    public Page<Article> pagingList(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
