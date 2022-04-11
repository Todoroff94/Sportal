package com.example.sportalproject.model.DTO.commentDTOs;

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
public class CommentRequestDTO {

    private long id;
    private String name;

}
