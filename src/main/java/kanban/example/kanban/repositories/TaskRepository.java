package kanban.example.kanban.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByBoardId(String boardId);

    List<Task> findByCreatedByUser(String userId);

}
