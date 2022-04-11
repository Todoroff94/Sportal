package com.example.sportalproject.model.DTO.userDTOs;

import com.example.sportalproject.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Component
@NoArgsConstructor
public class UserLikeArticleDTO {

    private Set<User> articleLikers= new HashSet<>();
}
