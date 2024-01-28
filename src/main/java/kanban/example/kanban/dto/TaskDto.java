package kanban.example.kanban.dto;

import java.util.Date;

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

    private UserDto assignedUser;

    private UserDto createdByUser;
    private Date dueDate;

    private Date createdAt;
    private Date updatedAt;
    private Date finishedAt;
}
