package com.example.sportalproject.model.DTO.articleDTOs;

import com.example.sportalproject.model.DTO.categoryDTOs.CategoryNameDTO;
import com.example.sportalproject.model.DTO.userDTOs.UserWithoutArticlesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@AllArgsConstructor
public class ArticleResponseDTO {


    private long id;
    private String headline;
    private String content;
    CategoryNameDTO category;
    private int article_views;
    UserWithoutArticlesDTO author;



}