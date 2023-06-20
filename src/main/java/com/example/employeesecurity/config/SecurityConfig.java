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
                http.authorizeRequests()
                        .antMatchers("/api/authenticate")
                        .permitAll()
                        .antMatchers("/api/employees/**")
                        .authenticated()
                        .anyRequest().authenticated()
                        .and()
                        .formLogin(form -> form
                                .loginPage("/api/authenticate")
                                .defaultSuccessUrl("/api/employees")
                                .loginProcessingUrl("/authenticate")
                                .failureForwardUrl("/api/authenticate?error=true")
                                .permitAll()
                        )
                        .logout()
                        .logoutUrl("/api/logout")
                        .logoutSuccessUrl("/api/authenticate")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll().
                        and()
                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().migrateSession()
                        .maximumSessions(1).maxSessionsPreventsLogin(true)
                        .expiredUrl("/api/authenticate?expired=true");

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
        return new BCryptPasswordEncoder();
    }
}
