package kanban.example.kanban.collections;

import java.util.Date;
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
    
    @DocumentReference
    private User createdByUserId;
    private List<BoardColumn> columns;
    private Date createdAt;
    private Date updatedAt;

}
