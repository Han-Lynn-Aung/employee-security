package com.example.employeesecurity.config;

import com.example.employeesecurity.filter.JwtAuthenticationFilter;
import com.example.employeesecurity.service.EmployeeDetailsService;
import com.example.employeesecurity.utils.JwtAuthenticationEntryPoint;
import com.example.employeesecurity.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, employeeDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http.csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/api/employees/login").permitAll()
                        .antMatchers("/api/employees/**").authenticated()
                        .anyRequest().permitAll()
                        .and()
                        .formLogin(form -> form
                                .loginPage("/api/employees/login")
                                .defaultSuccessUrl("/api/employees/")
                                .loginProcessingUrl("/api/employees/authenticate")
                                .failureForwardUrl("/api/employees/login?error=true")
                                .permitAll()
                        )
                        .logout()
                        .logoutUrl("/api/employees/logout")
                        .logoutSuccessUrl("/api/employees/login")
                        .invalidateHttpSession(true)
                        .permitAll()
                        .and()
                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

   @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
