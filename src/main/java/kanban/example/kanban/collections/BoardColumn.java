package kanban.example.kanban.collections;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class BoardColumn {
    private String title;
    
    @DocumentReference
    private List<Task> tasksIds;
}
