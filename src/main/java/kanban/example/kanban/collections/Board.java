package kanban.example.kanban.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "boards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    private String id;
    private String name;
    private String description;

    private String projectId;
    
    @DocumentReference
    private User createdByUser;

    @DocumentReference
    private List<BoardColumn> columns;
    private String createdAt;
    private String updatedAt;

}
