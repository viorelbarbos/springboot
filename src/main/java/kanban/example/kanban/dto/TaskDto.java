package kanban.example.kanban.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;

    private String title;
    private String description;
    private String status;
    private String boardColumnId;

    private UserDto assignedUser;

    private UserDto createdByUser;
    private String dueDate;

    private String createdAt;
    private String updatedAt;
    private String finishedAt;
}
