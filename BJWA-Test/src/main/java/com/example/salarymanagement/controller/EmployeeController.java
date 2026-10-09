package com.example.salarymanagement.controller;

import com.example.salarymanagement.model.Employee;
import com.example.salarymanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Nhận yêu cầu HTTP; các hàm trả về tên template Thymeleaf hoặc redirect.
@Controller
// Tất cả đường dẫn bên dưới đều bắt đầu bằng /employees.
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Spring tự cung cấp EmployeeService qua constructor (dependency injection).
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /employees hoặc /employees?keyword=IT: đọc từ khóa trên URL, không bắt buộc có.
    @GetMapping
    public String listEmployees(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        // Model mang dữ liệu sang HTML: ${employees} là danh sách, ${keyword} giữ nội dung tìm kiếm.
        model.addAttribute("employees", employeeService.findAll(keyword));
        model.addAttribute("keyword", keyword);
        // Tên view tương ứng src/main/resources/templates/employees/list.html.
        return "employees/list";
    }

    // GET /employees/new: mở form trống để thêm nhân viên.
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Create Employee");
        return "employees/form";
    }

    // POST /employees: nhận dữ liệu khi nhấn Save trên form tạo mới.
    // ModelAttribute gán các ô form vào Employee; Valid kiểm tra annotation trong entity.
    // BindingResult phải đứng ngay sau Employee được validate để nhận lỗi nhập liệu.
    @PostMapping
    public String createEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        // Nếu sai dữ liệu, trả lại form cùng giá trị đã nhập và thông báo lỗi, chưa lưu database.
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Create Employee");
            return "employees/form";
        }

        try {
            employeeService.save(employee);
            // Flash attribute chuyển thông báo sang yêu cầu tiếp theo sau redirect.
            redirectAttributes.addFlashAttribute("successMessage", "Employee created successfully.");
            // Chuyển sang GET danh sách, tránh gửi lại POST khi người dùng refresh trang.
            return "redirect:/employees";
        } catch (IllegalArgumentException exception) {
            // Lỗi email trùng từ Service được gắn vào trường email để Thymeleaf hiển thị.
            bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            model.addAttribute("pageTitle", "Create Employee");
            return "employees/form";
        }
    }

    // GET /employees/1/edit: PathVariable lấy số 1 từ URL làm id, rồi nạp dữ liệu vào form.
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("pageTitle", "Edit Employee");
        return "employees/form";
    }

    // POST /employees/1: nhận và lưu thông tin sửa của nhân viên số 1.
    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        // Lấy ID từ URL để lưu đúng nhân viên và giữ đúng action của form nếu có lỗi.
        employee.setId(id);

        // Kiểm tra dữ liệu sửa giống như khi tạo mới; có lỗi thì mở lại form Edit.
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Employee");
            return "employees/form";
        }

        try {
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully.");
            return "redirect:/employees";
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("email", "duplicate", exception.getMessage());
            model.addAttribute("pageTitle", "Edit Employee");
            return "employees/form";
        }
    }

    // POST /employees/1/delete: form xác nhận xóa gửi ID qua đường dẫn này.
    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully.");
        return "redirect:/employees";
    }

    // Chuẩn hóa URL có dấu / cuối thành URL danh sách /employees.
    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/employees";
    }
}
