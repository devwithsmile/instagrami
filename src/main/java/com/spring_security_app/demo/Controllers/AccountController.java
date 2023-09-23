package com.spring_security_app.demo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @GetMapping("/myAccount")
    public String getAccountDetails(){
        return "Account Details will be returned from here !!";
    }

}
