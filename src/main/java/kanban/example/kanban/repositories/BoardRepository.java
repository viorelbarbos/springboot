package kanban.example.kanban.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.Board;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {

    List<Board> findByProjectId(String projectId);

}
