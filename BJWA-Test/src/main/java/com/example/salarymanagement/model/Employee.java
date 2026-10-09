package com.example.salarymanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

// Entity là lớp được JPA ánh xạ vào database; mỗi đối tượng tương ứng một dòng trong bảng employees.
@Entity
@Table(name = "employees")
public class Employee {

    // Khóa chính của nhân viên. IDENTITY yêu cầu database tự sinh ID khi thêm mới.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NotBlank: không được null, rỗng hoặc chỉ có khoảng trắng; Size giới hạn độ dài tên.
    // Column quy định cột database: không nhận NULL và dài tối đa 80 ký tự.
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 80, message = "Full name must be between 2 and 80 characters")
    @Column(nullable = false, length = 80)
    private String fullName;

    // Email kiểm tra định dạng; unique tạo ràng buộc không trùng email trong database.
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // Biểu thức chính quy: từ đầu (^) đến cuối ($) chỉ chứa 9-12 chữ số.
    // Lưu điện thoại bằng String để giữ số 0 ở đầu.
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "Phone number must contain 9 to 12 digits")
    @Column(nullable = false, length = 12)
    private String phone;

    // Phòng ban bắt buộc nhập và có tối đa 60 ký tự.
    @NotBlank(message = "Department is required")
    @Size(max = 60, message = "Department must be at most 60 characters")
    @Column(nullable = false, length = 60)
    private String department;

    // Chức vụ bắt buộc nhập và có tối đa 60 ký tự.
    @NotBlank(message = "Position is required")
    @Size(max = 60, message = "Position must be at most 60 characters")
    @Column(nullable = false, length = 60)
    private String position;

    // NotNull yêu cầu có giá trị; inclusive=false nghĩa là lương phải > 0, không nhận 0.
    // BigDecimal dùng cho tiền; precision=14 là tổng số chữ số, scale=2 là số chữ số thập phân.
    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal salary;

    // Ngày sinh phải trước hôm nay; DateTimeFormat chuyển chuỗi yyyy-MM-dd từ form sang LocalDate.
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    // Ngày vào làm được phép là hôm nay hoặc trước hôm nay, không được ở tương lai.
    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate hireDate;

    // Constructor không tham số cần cho JPA và cho việc tạo đối tượng nhận dữ liệu từ form.
    public Employee() {
    }

    // Constructor có tham số dùng để tạo nhanh nhân viên, ví dụ khi thêm dữ liệu mẫu.
    public Employee(String fullName, String email, String phone, String department, String position,
                    BigDecimal salary, LocalDate dateOfBirth, LocalDate hireDate) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
    }

    // Getter trả về giá trị; setter cập nhật giá trị của thuộc tính private.
    // Spring dùng setter để gán dữ liệu form; Thymeleaf đọc thuộc tính qua getter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
