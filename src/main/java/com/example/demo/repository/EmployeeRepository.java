package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Tự động tạo câu lệnh tìm kiếm theo tên (dùng cho Module 4)
    List<Employee> findByNameContainingIgnoreCase(String name);

    // 1. Thống kê tổng số nhân viên trong hệ thống
    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();

    // 2. Thống kê số lượng nhân viên theo từng phòng ban
    // Trả về danh sách gồm mảng Object: [Tên phòng ban, Số lượng]
    @Query("SELECT e.department.name, COUNT(e) FROM Employee e GROUP BY e.department.name")
    List<Object[]> countEmployeesByDepartment();
}
