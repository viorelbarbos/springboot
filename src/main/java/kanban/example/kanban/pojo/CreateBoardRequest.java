package kanban.example.kanban.pojo;

import kanban.example.kanban.collections.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardRequest {
    String projectId;
    Board board;

}
