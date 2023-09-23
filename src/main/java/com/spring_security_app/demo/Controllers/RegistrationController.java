package com.spring_security_app.demo.Controllers;

import com.spring_security_app.demo.DTO.RegistrationDTO;
import com.spring_security_app.demo.Model.Customers;
import com.spring_security_app.demo.Repositories.CustomerRepository;
import com.spring_security_app.demo.ServicesImpl.CustomerServicesImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private CustomerServicesImpl customerServices;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationDTO registrationDTO){
        if(customerServices.doesUserExists(registrationDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists. Please choose a different Email.");

        }

        Customers customers = new Customers();
        customers.setEmail(registrationDTO.getEmail());
        customers.setPwd(passwordEncoder.encode(registrationDTO.getPassword()));
        customers.setRole(registrationDTO.getRole());

        customerRepository.save(customers);

        return ResponseEntity.ok("Registration Successful");

    }

    @GetMapping
    public String getMapping(){
        return "getMapping for Register";
    }
}
