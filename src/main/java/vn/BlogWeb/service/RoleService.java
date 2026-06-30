package vn.BlogWeb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceAlreadyExistsException;
import vn.BlogWeb.helper.exception.ResourceNotFoundException;
import vn.BlogWeb.model.Role;
import vn.BlogWeb.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    // Tạo mới Role
    public Role createRole(Role inputRole) {
        // Kiểm tra Role có cùng tên đã tồn tại chưa
        if (this.roleRepository.existsByName(inputRole.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Role với name = " + inputRole.getName() + " đã tồn tại.");
        }

        // Nếu chưa tồn tại thì lưu vào Database
        return this.roleRepository.save(inputRole);
    }

    // Lấy danh sách tất cả Role
    public List<Role> fetchRoles() {
        // Lấy toàn bộ Role từ Database
        return this.roleRepository.findAll();
    }

    // Lấy Role theo id
    public Role fetchRoleById(long id) {
        return this.roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tồn tại Role với id = " + id));
    }


    // Cập nhật Role theo id
    public Role updateRoleById(long id, Role updateRole) {
        // Tìm Role trong Database
        Role roleInDB = this.roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tồn tại Role với id = " + id));

        // Nếu tên Role không thay đổi
        if (roleInDB.getName().equals(updateRole.getName())) {
            // Chỉ cập nhật description
            roleInDB.setDescription(updateRole.getDescription());
        } else {
            // Nếu đổi tên thì kiểm tra tên mới đã tồn tại chưa
            if (this.roleRepository.existsByNameAndIdNot(updateRole.getName(), id)) {
                throw new ResourceAlreadyExistsException(
                        "Role với name = " + updateRole.getName() + " đã tồn tại.");
            }

            // Cập nhật tên và description
            roleInDB.setName(updateRole.getName());
            roleInDB.setDescription(updateRole.getDescription());
        }

        // Lưu thay đổi vào Database
        return this.roleRepository.save(roleInDB);
    }


    // Xóa Role theo id
    public void deleteRoleById(long id) {
        // Kiểm tra Role có tồn tại không
        if (!this.roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tồn tại Role với id = " + id);
        }

        // Xóa Role
        this.roleRepository.deleteById(id);
    }
}
