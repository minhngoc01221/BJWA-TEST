package com.example.salarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Khởi tạo Spring context để kiểm tra các bean và cấu hình có nạp được không.
@SpringBootTest
class SalaryManagementApplicationTests {

    // Test không cần assertion: nếu Spring không khởi động được context thì test sẽ thất bại.
    // Test này chưa kiểm tra từng chức năng CRUD hay giao diện.
    @Test
    void contextLoads() {
    }
}
