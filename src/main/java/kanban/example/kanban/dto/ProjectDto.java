package kanban.example.kanban.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private String id;

    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;

    private UserDto createdByUser;

    private List<UserDto> members;

    private List<BoardDto> boards;
}
