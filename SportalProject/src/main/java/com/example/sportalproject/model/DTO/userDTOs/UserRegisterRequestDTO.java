package com.example.sportalproject.model.DTO.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
@AllArgsConstructor
public class UserRegisterRequestDTO {

    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private boolean is_admin;
    private String email;

}
