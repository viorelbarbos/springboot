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
@Document(collection = "boardColumns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardColumn {

    @Id
    private String id;

    private String title;

    private String color;

    private String boardId;

    @DocumentReference
    private List<Task> tasks;
}
