package com.example.employeesecurity.controller;

import com.example.employeesecurity.model.JwtAuthenticationResponse;
import com.example.employeesecurity.model.LoginForm;
import com.example.employeesecurity.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/login")
    public ModelAndView authenticateUser(@ModelAttribute LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        ModelAndView modelAndView = new ModelAndView("login-form"); // Replace "dashboard" with the desired view name
        modelAndView.addObject("token", jwt);

        return modelAndView;
    }
}
