package kanban.example.kanban.collections;

import java.util.Date;

import org.bson.types.ObjectId;
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
    private ObjectId id;

    private String title;
    private String description;
    private String status;

    @DocumentReference 
    private User assignedUserId;

    @DocumentReference
    private User createdByUserId;
    private Date dueDate;
    
    @DocumentReference
    private Board boardId;
    private Date createdAt;
    private Date updatedAt;
    private Date finishedAt;
}
