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
    private Long id;

    @Column(name = "employee_name", nullable = false)
    @Size(min = 5, max = 250)
    private String name;

    @Column(name = "employee_email", nullable = false, unique = true)
    private String email;

    @Column(name = "employee_address", nullable = false)
    private String address;


}