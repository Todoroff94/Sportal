package com.example.sportalproject.model.DTO.userDTOs;

import com.example.sportalproject.model.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@Getter
@Setter
@NoArgsConstructor
public class UserWithoutArticlesDTO {


    private String username;


}
