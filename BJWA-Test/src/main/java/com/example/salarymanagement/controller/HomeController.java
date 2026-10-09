package com.example.salarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller nhận yêu cầu web và trả về tên view hoặc đường dẫn chuyển hướng.
@Controller
public class HomeController {

    // Khi mở trang gốc, chuyển trình duyệt sang danh sách nhân viên.
    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }
}
