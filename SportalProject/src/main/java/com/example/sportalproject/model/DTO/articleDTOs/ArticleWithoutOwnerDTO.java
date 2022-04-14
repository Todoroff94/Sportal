package com.example.sportalproject.model.DTO.articleDTOs;

import com.example.sportalproject.model.DTO.categoryDTOs.CategoryIdDTO;
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
public class ArticleWithoutOwnerDTO {

    private long id;
    private String headline;
    private String content;
    private CategoryIdDTO category;

}
