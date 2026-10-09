package com.example.salarymanagement;

import com.example.salarymanagement.model.Employee;
import com.example.salarymanagement.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Bật cấu hình tự động của Spring Boot và tìm các component trong package này cùng package con.
@SpringBootApplication
public class SalaryManagementApplication {

    public static void main(String[] args) {
        // Điểm bắt đầu: tạo Spring context và khởi động web server Tomcat.
        SpringApplication.run(SalaryManagementApplication.class, args);
    }

    // Đăng ký runner để Spring chạy phần tạo dữ liệu mẫu sau khi ứng dụng khởi động.
    @Bean
    CommandLineRunner seedEmployees(EmployeeRepository employeeRepository) {
        return args -> {
            // Nếu database đã có nhân viên thì bỏ qua, tránh thêm lại dữ liệu mẫu.
            if (employeeRepository.count() > 0) {
                return;
            }

            // save() lưu nhân viên xuống database; ID được database tự sinh.
            // Thứ tự tham số: tên, email, điện thoại, phòng ban, chức vụ, lương, ngày sinh, ngày vào làm.
            // BigDecimal từ chuỗi giữ giá trị tiền chính xác; LocalDate.of nhận năm, tháng, ngày.
            employeeRepository.save(new Employee(
                    "Nguyen Van An",
                    "an.nguyen@example.com",
                    "0901234567",
                    "IT",
                    "Java Developer",
                    new BigDecimal("18000000"),
                    LocalDate.of(1998, 5, 15),
                    LocalDate.of(2024, 1, 10)
            ));
            employeeRepository.save(new Employee(
                    "Tran Thi Binh",
                    "binh.tran@example.com",
                    "0912345678",
                    "Finance",
                    "Accountant",
                    new BigDecimal("15000000"),
                    LocalDate.of(1996, 9, 20),
                    LocalDate.of(2023, 8, 1)
            ));
            employeeRepository.save(new Employee(
                    "Le Minh Chau",
                    "chau.le@example.com",
                    "0987654321",
                    "Human Resources",
                    "HR Executive",
                    new BigDecimal("14000000"),
                    LocalDate.of(1997, 12, 3),
                    LocalDate.of(2022, 11, 21)
            ));
        };
    }
}
