package com.example.sportalproject.model.DTO.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserEditResponseDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String profileImgUrl;
    private LocalDateTime updated_at;
    private String email;

}
