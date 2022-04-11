package com.example.sportalproject.model.DTO.articleDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ArticleAddPIctureDTO {

    private long id;
    private String imageUrl;

}
