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
import vn.BlogWeb.helper.ApiResponse;
import vn.BlogWeb.model.User;
import vn.BlogWeb.model.dto.UserRequestDTO;
import vn.BlogWeb.model.dto.UserResponseDTO;
import vn.BlogWeb.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody User inputUser) {
        UserResponseDTO userInDB = this.userService.createUser(inputUser);
        return ApiResponse.created(userInDB);
    }

    // GET ALL
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> users = this.userService.fetchUsers();
        return ApiResponse.success(users);
    }


    // GET BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable int id) {

        UserResponseDTO user = this.userService.findUserById(id);
        return ApiResponse.success(user);

    }


    // UPDATE
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable int id,
            @RequestBody UserRequestDTO inputUser) {

        this.userService.updateUser(id, inputUser);

        return ApiResponse.success("Updated successfully");
    }

    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {

        this.userService.deleteUserById(id);

        return ApiResponse.success("Delete successfully");
    }
}
