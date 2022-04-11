package com.example.sportalproject.model.DTO.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserEditRequestDTO {



    private long id;
    private String firstName;
    private String lastName;
    private String profileImgUrl;
    private String email;
    private String phone;

}
