package kanban.example.kanban.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.lang.NonNull;

import kanban.example.kanban.collections.User;
import kanban.example.kanban.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(String id) {
        if (id == null)
            return null;
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(@NonNull User user) {

        // Check if user already exists by email
        User existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return null;
        }

        return userRepository.save(user);
    }

    public User updateUser(@NonNull User user) {
        return userRepository.save(user);
    }

    public void deleteUser(@NonNull String id) {
        userRepository.deleteById(id);
    }

}
