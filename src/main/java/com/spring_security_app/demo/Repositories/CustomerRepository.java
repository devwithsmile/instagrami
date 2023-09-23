package com.spring_security_app.demo.Repositories;

import com.spring_security_app.demo.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Long> {

    List<Customers> findByEmail(String email);
}
