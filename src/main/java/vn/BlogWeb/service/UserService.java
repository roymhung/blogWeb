package vn.BlogWeb.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceAlreadyExistsException;
import vn.BlogWeb.model.User;
import vn.BlogWeb.model.dto.UserResponseDTO;
import vn.BlogWeb.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> fetchUsers() {
        List<User> userList = this.userRepository.findAll();

        return userList;
    }

    public UserResponseDTO convertUserToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UserResponseDTO createUser(User user) {
        // check email
        if (this.userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email đã tồn tại: " + user.getEmail());
        }

        // hash password
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return convertUserToDTO(this.userRepository.save(user));
    }

    public User findUserById(int id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user not found with id = " + id));
    }

    public void updateUser(User inputUser) {
        User currentUserInDB = this.findUserById(inputUser.getId());
        if (currentUserInDB != null) {
            currentUserInDB.setName(inputUser.getName());
            currentUserInDB.setEmail(inputUser.getEmail());
            currentUserInDB.setAddress(inputUser.getAddress());

            this.userRepository.save(currentUserInDB);
        }
    }

    public void deleteUserById(int id) {
        this.userRepository.deleteById(id);
    }

}
