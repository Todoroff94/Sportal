package com.example.sportalproject.model.DTO.commentDTOs;

import com.example.sportalproject.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Set;

@Getter
@Setter
@Component
@NoArgsConstructor
public class LikeCommentDTO {

    private long id;
    private String content;


}
