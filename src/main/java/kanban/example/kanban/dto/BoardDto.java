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
public class BoardDto {
    private String id;
    private String name;
    private String description;
    private String projectId;

    private UserDto createdByUser;
    private List<BoardColumnDto> columns;
    private String createdAt;
    private String updatedAt;
}
