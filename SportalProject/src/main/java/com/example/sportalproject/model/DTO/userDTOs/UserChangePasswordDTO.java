package com.example.sportalproject.model.DTO.userDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserChangePasswordDTO {

    long id;
    String oldPassword;
    String newPassword;
    String newConfPassword;
}
