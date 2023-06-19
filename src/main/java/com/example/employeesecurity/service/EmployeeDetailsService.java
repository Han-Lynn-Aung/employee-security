package com.example.employeesecurity.service;

import com.example.employeesecurity.model.Employee;
import com.example.employeesecurity.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with this name : " + username));

        return new User(employee.getName(), employee.getPassword(), new ArrayList<>());
    }
}
