/*
package com.example.employeesecurity.controller;

import com.example.employeesecurity.model.LoginForm;
import com.example.employeesecurity.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;


@Controller
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


   @GetMapping("/login")
    public String loginForm(){
        return "login-form";
    }
    @PostMapping (value ={"/authenticate"})
    public ModelAndView authenticateUser(@RequestBody LoginForm loginForm, @RequestParam(value = "error", required = false) String error) {

        System.out.println("in authenticalte funtion....");

        if (error != null) {
        ModelAndView  modelAndView = new ModelAndView("login-form");
            modelAndView.addObject("error", true);
            return modelAndView;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

       ModelAndView modelAndView = new ModelAndView("redirect:/api/employees");
        modelAndView.addObject("token", jwt);
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logoutForm() {

        return new ModelAndView("logout-form");
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logoutUser() {

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully");
    }

   */
/* private void clearAuthenticationCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie[] cookies = request.getCookies(); if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }*//*

}*/
