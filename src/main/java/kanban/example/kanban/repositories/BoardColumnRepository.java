package kanban.example.kanban.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.BoardColumn;

@Repository
public interface BoardColumnRepository extends MongoRepository<BoardColumn, String> {

    List<BoardColumn> findByBoardId(String boardId);
}
