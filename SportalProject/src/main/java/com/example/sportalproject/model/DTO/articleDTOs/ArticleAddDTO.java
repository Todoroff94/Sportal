package com.example.sportalproject.model.DTO.articleDTOs;

import com.example.sportalproject.model.DTO.categoryDTOs.CategoryIdDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAddDTO {


    String headline;
    String content;
    CategoryIdDTO category;
}


