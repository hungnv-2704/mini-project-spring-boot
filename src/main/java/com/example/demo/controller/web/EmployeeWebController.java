package com.example.demo.controller;

import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/employees")
@RequiredArgsConstructor
public class EmployeeWebController {
    
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list"; 
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new com.example.demo.model.Employee());
        return "employee-form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/save")
    public String saveEmployee(@org.springframework.web.bind.annotation.ModelAttribute("employee") com.example.demo.model.Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/view/employees/list";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {

    model.addAttribute("totalEmployees", employeeService.getTotalEmployees());
    
    model.addAttribute("deptStats", employeeService.getEmployeesByDepartment());
    
    return "employee-statistics";
}
}