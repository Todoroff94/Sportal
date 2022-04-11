package com.example.sportalproject.model.entity;

import com.example.sportalproject.model.DTO.articleDTOs.ArticleAddDTO;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleWithoutOwnerDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="articles")
@Getter
@Setter
@NoArgsConstructor
@Component
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String headline;
    @Column
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_category")
    @JsonBackReference
    private Category category;
    @Column
    private int article_views;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_author")
    @JsonBackReference
    private User author;

    @ManyToMany(mappedBy = "likedArticles",fetch = FetchType.LAZY)
    private Set<User> articleLikers;

    @ManyToMany(mappedBy = "dislikedArticles",fetch = FetchType.LAZY)
    private Set<User> articleDislikers;

    @OneToMany(mappedBy = "videoArticle")
    private Set<Video>videos;


    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    private Set<Picture> pictures;


    @OneToMany(mappedBy = "article",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Comment> comments;


    public Article(ArticleWithoutOwnerDTO articleWithoutOwnerDTO){
        this.headline=articleWithoutOwnerDTO.getHeadline();
        this.content=articleWithoutOwnerDTO.getContent();
    }

    public Article(ArticleAddDTO article){
        this.headline=article.getHeadline();
        this.content=article.getContent();
    }
}
