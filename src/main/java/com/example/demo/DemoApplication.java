package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Hàm này sẽ tự động chạy ngay sau khi ứng dụng khởi động thành công
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Kiểm tra nếu chưa có user nào trong DB thì mới tạo mới
            if (userRepository.count() == 0) {
                
                // Tạo tài khoản Admin
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Mã hóa mật khẩu
                admin.setRole("ROLE_ADMIN");
                userRepository.save(admin);

                // Tạo tài khoản User thường
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole("ROLE_USER");
                userRepository.save(user);

                System.out.println(">>> Đã khởi tạo tài khoản mặc định:");
                System.out.println(">>> Admin: admin / admin123");
                System.out.println(">>> User: user / user123");
            }
        };
    }
}
