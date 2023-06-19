package com.example.employeesecurity.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Employee_Name", nullable = false, unique = true)
    @Size(min = 5, max = 250)
    private String name;

    @Column(name = "Employee_Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Employee_Address", nullable = false)
    private String address;

    @Column(name = "Employee_password", nullable = false)
    private String password;
}