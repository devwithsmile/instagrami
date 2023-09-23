package com.spring_security_app.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private String email;
    private String password;
    private String role;
}
