package com.example.employeesecurity.controller;

import com.example.employeesecurity.model.JwtAuthenticationResponse;
import com.example.employeesecurity.model.LoginForm;
import com.example.employeesecurity.repository.EmployeeRepository;
import com.example.employeesecurity.model.Employee;
import com.example.employeesecurity.service.EmployeeService;
import com.example.employeesecurity.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/login")
    private String loginForm(){

        return "login-form";
    }

    @RequestMapping(value = {"/authenticate"}, method = {RequestMethod.GET})
    public ModelAndView authenticateUser(@RequestBody LoginForm loginForm, @RequestParam(value = "error", required = false) String error) {

        System.out.println("in authentication function....");

        if (error != null) {
            ModelAndView  modelAndView = new ModelAndView("login-form");
            modelAndView.addObject("error", true);
            return modelAndView;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String  jwt = jwtTokenProvider.generateToken(authentication);

        ModelAndView modelAndView = new ModelAndView("redirect: /api/employees/");
        modelAndView.addObject("token", jwt);
        return modelAndView;
    }

    @GetMapping("/")
    private String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-list";
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



    @GetMapping("/create")
    private String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @PostMapping("/create")
    private String createEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employee-form";
        }
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    private String showEditForm(@PathVariable("id") long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));

        model.addAttribute("employee", employee);
        return "employee-form";
    }

    @PostMapping("/edit/{id}")
    private String updateEmployee(@PathVariable("id") long id,@Valid @ModelAttribute("employee") Employee employee,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "employee-form";
        }

        employee.setId(id);
        employeeRepository.save(employee);
        return "redirect:/api/employees/";
    }

    @GetMapping("/delete/{id}")
    private String deleteEmployee(@PathVariable("id") long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee id: " + id));
        employeeRepository.delete(employee);
        return "redirect:/api/employees/";
    }

    /*    @GetMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }*/

   /* @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }*/
}
