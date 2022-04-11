package com.example.sportalproject.model.DTO.commentDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class DislikeCommentDTO {

    private long id;
    private String content;
}
