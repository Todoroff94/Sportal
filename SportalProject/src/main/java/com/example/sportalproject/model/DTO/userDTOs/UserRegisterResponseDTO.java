package com.example.sportalproject.model.DTO.userDTOs;

import com.example.sportalproject.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
@AllArgsConstructor
public class UserRegisterResponseDTO {

    private long id;
    private String username;
    private String email;

    public UserRegisterResponseDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
