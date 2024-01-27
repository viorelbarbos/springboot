package kanban.example.kanban.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Repository
@Document(collection = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    private String id;

    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;

    @DocumentReference
    private User createdByUserId;

    @DocumentReference
    private List<User> members;

    @DocumentReference
    private List<Board> boards;

}
