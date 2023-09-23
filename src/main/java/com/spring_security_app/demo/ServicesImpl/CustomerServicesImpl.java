package com.spring_security_app.demo.ServicesImpl;

import com.spring_security_app.demo.Model.Customers;
import com.spring_security_app.demo.Repositories.CustomerRepository;
import com.spring_security_app.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServicesImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean doesUserExists(String email) {
        List<Customers> customers = customerRepository.findByEmail(email);

        int size = customers.size();

        if (customers.size() == 0) {
            return false;
        }
        return true;
    }
}
