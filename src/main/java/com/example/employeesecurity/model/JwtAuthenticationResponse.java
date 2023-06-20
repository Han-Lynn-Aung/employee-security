package com.example.employeesecurity.model;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

   private String jwtToken;
}
