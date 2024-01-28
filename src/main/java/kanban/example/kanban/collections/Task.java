package kanban.example.kanban.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;

    private String title;
    private String description;
    private String status;
    private String boardId;

    @DocumentReference
    private User assignedUser;

    @DocumentReference
    private User createdByUser;
    private String dueDate;

    private String createdAt;
    private String updatedAt;
    private String finishedAt;
}
