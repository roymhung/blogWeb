package vn.BlogWeb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import vn.BlogWeb.model.User;
import vn.BlogWeb.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> fetchUsers() {
        List<User> userList = this.userRepository.findAll();

        return userList;
    }

    public User createUser(User user) {
        return this.userRepository.save(user);

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
