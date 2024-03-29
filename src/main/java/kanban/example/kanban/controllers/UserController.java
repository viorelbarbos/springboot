package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.ProjectDto;
import kanban.example.kanban.dto.UserDto;
import kanban.example.kanban.mappers.UserMapper;
import kanban.example.kanban.services.UserService;
import kanban.example.kanban.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {

        List<User> users = userService.allUsers();

        List<UserDto> userDtos = UserMapper.mapToDtoList(users);

        ApiResponse<List<UserDto>> apiResponse = ApiResponse.success("Users fetched successfully",
                HttpStatus.OK.value(),
                userDtos);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {

        User newUser = userService.createUser(user);

        if (newUser == null) {
            ApiResponse<User> response = ApiResponse.error("User already exists",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ApiResponse<User> response = ApiResponse.success("User created successfully", HttpStatus.CREATED.value(),
                newUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {

        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
