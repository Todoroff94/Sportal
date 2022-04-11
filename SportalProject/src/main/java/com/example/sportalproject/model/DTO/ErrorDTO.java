package com.example.sportalproject.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private String msg;
    private HttpStatus status;
    private LocalDateTime dateTime;
}
