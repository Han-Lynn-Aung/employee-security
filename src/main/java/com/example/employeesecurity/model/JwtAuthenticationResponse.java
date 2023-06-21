package com.example.employeesecurity.model;


import lombok.*;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

   private String jwtToken;
}
