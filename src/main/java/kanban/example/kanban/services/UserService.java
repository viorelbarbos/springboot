package kanban.example.kanban.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.User;
import kanban.example.kanban.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(String id) {
        if (id == null)
            return null;
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(@NonNull User user) {

        User existingUser = getUserByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            return null;
        }

        return userRepository.save(user);
    }

    public User updateUser(@NonNull User user) {

        user.setUpdatedAt((new java.util.Date()).toString());
        return userRepository.save(user);
    }

    public void deleteUser(@NonNull String id) {

        if (id == null)
            return;

        userRepository.deleteById(id);
    }

    public List<User> getUsersByIds(List<String> ids) {
        if (ids == null || ids.isEmpty())
            return null;

        List<User> users = new ArrayList<>();

        for (String id : ids) {
            users.add(getUserById(id));
        }

        return users;

    }

}
