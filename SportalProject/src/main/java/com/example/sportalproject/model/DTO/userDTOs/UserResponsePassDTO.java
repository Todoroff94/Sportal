package com.example.sportalproject.model.DTO.userDTOs;

import com.example.sportalproject.model.DTO.articleDTOs.ArticleWithoutOwnerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserResponsePassDTO {

    private String message;
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    List<ArticleWithoutOwnerDTO> articles;
}
