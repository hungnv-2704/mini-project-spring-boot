package com.example.demo.controller; // Bạn có thể chuyển vào .controller.web nếu muốn

import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/employees") // Thay đổi đường dẫn để không trùng với /api/employees
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
        return "employee-form"; // Tạo file employee-form.html
    }

    // 2. Xử lý lưu dữ liệu từ form và quay về trang danh sách
    @org.springframework.web.bind.annotation.PostMapping("/save")
    public String saveEmployee(@org.springframework.web.bind.annotation.ModelAttribute("employee") com.example.demo.model.Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/view/employees/list";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
    // Gửi tổng số nhân viên sang View
    model.addAttribute("totalEmployees", employeeService.getTotalEmployees());
    
    // Gửi danh sách thống kê theo phòng ban sang View
    model.addAttribute("deptStats", employeeService.getEmployeesByDepartment());
    
    return "employee-statistics"; // Tên file HTML
}
}