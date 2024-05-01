package com.example.queryMethodTest.Dto;

import com.example.queryMethodTest.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public static ArticleDto fromArticleEntity(Article article) {
        return new ArticleDto(
                article.getId(), article.getTitle(), article.getContent()
        );
    }

    public Article fromMemberDto(ArticleDto dto) {
        Article article = new Article();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        return article;
    }

}
