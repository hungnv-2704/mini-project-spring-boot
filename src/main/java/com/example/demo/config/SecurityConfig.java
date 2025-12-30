package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Mã hóa mật khẩu
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 1. USER và ADMIN đều được xem danh sách và thống kê
                .requestMatchers("/view/employees/list", "/view/employees/statistics").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/employees").hasAnyRole("USER", "ADMIN")
                
                // 2. Chỉ ADMIN mới được vào trang Thêm/Sửa/Xóa
                .requestMatchers("/view/employees/add", "/view/employees/save").hasRole("ADMIN")
                .requestMatchers("/api/employees/**").hasRole("ADMIN")
                
                .anyRequest().authenticated() // Các trang khác phải đăng nhập
            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());
            
        return http.build();
    }
}