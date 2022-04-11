package com.example.sportalproject.model.DTO.categoryDTOs;

import com.example.sportalproject.model.DTO.userDTOs.UserCommentAuthorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component

public class CommentEditDTO {


    private long id;
    private String content;
    UserCommentAuthorDTO author;


}