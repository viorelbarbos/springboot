package kanban.example.kanban.mappers;

import java.util.List;

import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.UserDto;

public class UserMapper {
    public static UserDto mapToDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static User mapToUser(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public static List<UserDto> mapToDtoList(List<User> users) {
        return users.stream().map(UserMapper::mapToDto).collect(java.util.stream.Collectors.toList());
    }

}
