package com.spring_security_app.demo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class homeController {

    @GetMapping
    public String home(){
        return "home it is.";
    }
}
