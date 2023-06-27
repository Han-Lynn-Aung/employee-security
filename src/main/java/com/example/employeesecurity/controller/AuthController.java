package com.example.employeesecurity.controller;

import com.example.employeesecurity.model.JwtResponse;
import com.example.employeesecurity.model.JwtRequest;
import com.example.employeesecurity.model.User;
import com.example.employeesecurity.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid JwtRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateAccessToken(user);
            JwtResponse response = new JwtResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
    /*@PostMapping (value ={"/authenticate"})
    public String authenticateUser(@ModelAttribute("employee")Employee employee, @RequestParam(value = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", true);
            return "login-form";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employee.getName(), employee.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        model.addAttribute("token", jwt);
        return "redirect:/api/employees/list";
    }*/

