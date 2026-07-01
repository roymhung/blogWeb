package vn.BlogWeb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceAlreadyExistsException;
import vn.BlogWeb.helper.exception.ResourceNotFoundException;
import vn.BlogWeb.model.Role;
import vn.BlogWeb.model.User;
import vn.BlogWeb.model.dto.RoleResponseDTO;
import vn.BlogWeb.model.dto.UserRequestDTO;
import vn.BlogWeb.model.dto.UserResponseDTO;
import vn.BlogWeb.repository.RoleRepository;
import vn.BlogWeb.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> fetchUsersWithRole(String roleName) {

        List<UserResponseDTO> userList = userRepository
                .findByRole_Name(roleName).stream().map(
                        user -> UserResponseDTO.builder().id(user.getId()).name(user.getName())
                                .email(user.getEmail()).address(user.getAddress())
                                .role(new RoleResponseDTO(user.getRole().getId(),
                                        user.getRole().getName()))
                                .build())
                .collect(Collectors.toList());

        return userList;
    }

    public List<UserResponseDTO> fetchUsers() {
        List<UserResponseDTO> userList = userRepository.findAll().stream()
                .map(user -> UserResponseDTO.builder().id(user.getId()).name(user.getName())
                        .email(user.getEmail()).address(user.getAddress())
                        .role(new RoleResponseDTO(user.getRole().getId(), user.getRole().getName()))
                        .build())
                .collect(Collectors.toList());

        return userList;
    }

    public UserResponseDTO convertUserToDTO(User user) {

        RoleResponseDTO userRole =
                new RoleResponseDTO(user.getRole().getId(), user.getRole().getName());
        return UserResponseDTO.builder().id(user.getId()).name(user.getName())
                .email(user.getEmail()).address(user.getAddress()).role(userRole).build();
    }

    public UserResponseDTO createUser(User user) {
        // check email
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email đã tồn tại: " + user.getEmail());
        }

        // check role
        Long roleId = user.getRole().getId();
        String roleName = user.getRole().getName();

        Role roleInDB = this.roleRepository.findByIdOrName(roleId, roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role khong ton tai"));

        // hash password
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(roleInDB);

        return convertUserToDTO(this.userRepository.save(user));
    }

    public UserResponseDTO findUserById(int id) {
        User userInDB = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id = " + id));
        return convertUserToDTO(userInDB);
    }

    public void updateUser(int id, UserRequestDTO inputUser) {
        User userInDB = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id = " + id));

        // Cập nhật Role
        if (inputUser.getRole() != null) {
            userInDB.setRole(inputUser.getRole());
        }

        // Cập nhật thông tin
        userInDB.setName(inputUser.getName());
        userInDB.setAddress(inputUser.getAddress());

        this.userRepository.save(userInDB);
    }

    public void deleteUserById(int id) {
        this.userRepository.deleteById(id);
    }

}
