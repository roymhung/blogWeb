package vn.BlogWeb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import vn.BlogWeb.helper.ApiResponse;
import vn.BlogWeb.model.User;
import vn.BlogWeb.service.UserService;



@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User inputUser) {
        User userInDB = this.userService.createUser(inputUser);
        return ApiResponse.created(userInDB);
    }

    // GET ALL
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = this.userService.fetchUsers();
        return ApiResponse.success(users);
    }


    // Try-catch cục bộ trong controller
    // GET BY ID
    // @GetMapping("/users/{id}")
    // public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int id) {
    // try {
    // User user = this.userService.findUserById(id);
    // return ApiResponse.success(user);
    // } catch (Exception e) {
    // return ApiResponse.error(HttpStatus.BAD_REQUEST, "Id hong hop le!");
    // }

    // }

    // @ExceptionHandler (local): trong 1 controller cụ thể
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    // GET BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int id) {
        if (id == 1000) {
            throw new EntityNotFoundException("user not found with id = " + id);
        }

        User user = this.userService.findUserById(id);
        return ApiResponse.success(user);

    }


    // UPDATE
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable int id,
            @RequestBody User inputUser) {

        inputUser.setId(id);
        this.userService.updateUser(inputUser);

        return ApiResponse.success("Updated successfully");
    }

    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {

        this.userService.deleteUserById(id);

        return ApiResponse.success("Delete successfully");
    }
}
