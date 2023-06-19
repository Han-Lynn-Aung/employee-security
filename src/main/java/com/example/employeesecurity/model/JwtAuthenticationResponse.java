package com.example.employeesecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

   private String jwtToken;
}
