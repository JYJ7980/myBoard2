package com.example.queryMethodTest.repository;


import com.example.queryMethodTest.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}
