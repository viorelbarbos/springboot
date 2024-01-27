package kanban.example.kanban.collections;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class BoardColumn {

    @Id
    private String id;

    private String title;

    private String color;

    @DocumentReference
    private List<Task> tasksIds;
}
