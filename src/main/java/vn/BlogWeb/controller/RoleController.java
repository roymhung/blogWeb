package vn.BlogWeb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.ApiResponse;
import vn.BlogWeb.model.Role;
import vn.BlogWeb.service.RoleService;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // API tạo mới Role
    @PostMapping("/roles")
    public ResponseEntity<ApiResponse<Role>> postRole(@Valid @RequestBody Role inputRole) {
        // @RequestBody: Chuyển JSON thành object Role
        // @Valid: Kiểm tra dữ liệu theo các annotation validation trong class Role

        // Gọi Service để tạo Role mới
        Role role = this.roleService.createRole(inputRole);

        // Trả về HTTP 201 Created cùng dữ liệu vừa tạo
        return ApiResponse.created(role);
    }


    // API lấy danh sách Role
    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<Role>>> getRoles() {
        // Gọi Service lấy tất cả Role từ Database
        List<Role> roles = this.roleService.fetchRoles();

        // Trả về HTTP 200 OK cùng danh sách Role
        return ApiResponse.success(roles);
    }


    @GetMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Role>> getRole(@PathVariable long id) {
        Role role = this.roleService.fetchRoleById(id);
        return ApiResponse.success(role);
    }


    // API cập nhật Role theo id
    @PutMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<Role>> putRole(@PathVariable long id,
            @Valid @RequestBody Role inputRole) {

        // Gọi Service cập nhật Role
        Role role = this.roleService.updateRoleById(id, inputRole);

        // Trả về HTTP 200 OK cùng dữ liệu sau khi cập nhật
        return ApiResponse.success(role);
    }


    // API xóa Role theo id
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable long id) {
        // Gọi Service xóa Role
        this.roleService.deleteRoleById(id);

        // Trả về HTTP 200 OK
        return ApiResponse.success("Xóa Role thành công.");
    }

}
