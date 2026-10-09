package com.example.salarymanagement.repository;

import com.example.salarymanagement.model.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// Employee là entity, Long là kiểu khóa chính. Spring tạo implementation khi ứng dụng chạy.
// JpaRepository cung cấp sẵn findAll(), findById(), save(), deleteById() và count().
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Spring Data tạo truy vấn từ tên hàm: Containing = chứa từ khóa, IgnoreCase = bỏ qua hoa/thường.
    // Or nghĩa là chỉ cần một trong năm trường khớp; mỗi tham số ứng với một trường theo thứ tự.
    List<Employee> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPositionContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String fullName, String email, String position, String phone, String department);

    // Trả về true nếu email đã có trong database, dùng khi thêm nhân viên.
    boolean existsByEmailIgnoreCase(String email);

    // Khi sửa: tìm email trùng ở nhân viên khác, bỏ qua chính nhân viên có ID này.
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
