package com.example.salarymanagement.service;

import com.example.salarymanagement.model.Employee;
import com.example.salarymanagement.repository.EmployeeRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// Service chứa xử lý nghiệp vụ, được Controller gọi thay vì truy cập database trực tiếp.
@Service
// Các thao tác database chạy trong transaction: commit khi thành công, rollback khi có RuntimeException.
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // Constructor injection: Spring cung cấp Repository; final giữ nguyên tham chiếu sau khi khởi tạo.
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // readOnly báo đây là thao tác chỉ đọc dữ liệu.
    @Transactional(readOnly = true)
    public List<Employee> findAll(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            // Không có từ khóa (null, rỗng hoặc toàn khoảng trắng) thì lấy toàn bộ nhân viên.
            return employeeRepository.findAll();
        }

        // Bỏ khoảng trắng đầu/cuối và dùng cùng từ khóa tìm trên cả năm trường.
        String normalizedKeyword = keyword.trim();
        return employeeRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPositionContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                normalizedKeyword,
                normalizedKeyword,
                normalizedKeyword,
                normalizedKeyword,
                normalizedKeyword
        );
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        // findById trả về Optional: có nhân viên thì lấy ra, không có thì ném lỗi.
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    public Employee save(Employee employee) {
        // Kiểm tra email trước khi lưu để Controller có thể hiển thị lỗi ngay ở ô email.
        if (emailExists(employee)) {
            throw new IllegalArgumentException("Email already exists");
        }
        // ID null thường là thêm mới; ID đã có dùng để lưu thay đổi của nhân viên.
        return employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        // Xóa nhân viên theo khóa chính.
        employeeRepository.deleteById(id);
    }

    private boolean emailExists(Employee employee) {
        // Nhân viên mới chưa có ID: kiểm tra email trong toàn bộ bảng.
        if (employee.getId() == null) {
            return employeeRepository.existsByEmailIgnoreCase(employee.getEmail());
        }
        // Nhân viên đang sửa được giữ email của mình, nhưng không được lấy email của người khác.
        return employeeRepository.existsByEmailIgnoreCaseAndIdNot(employee.getEmail(), employee.getId());
    }
}
