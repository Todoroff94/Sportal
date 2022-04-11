package com.example.sportalproject.model.DTO.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
public class SubCommentDTO {

    private long id;
    private String content;
}
