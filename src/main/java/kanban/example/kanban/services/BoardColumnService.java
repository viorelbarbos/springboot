package kanban.example.kanban.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.BoardColumn;
import kanban.example.kanban.collections.Task;
import kanban.example.kanban.repositories.BoardColumnRepository;

@Service
public class BoardColumnService {

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private BoardService boardService;

    public BoardColumn createBoardColumn(String boardId, BoardColumn boardColumn) {

        if (boardId == null || boardColumn == null)
            return null;

        boardColumn.setBoardId(boardId);

        BoardColumn boardColumnSaved = boardColumnRepository.save(boardColumn);

        boardService.addColumnToBoard(boardId, boardColumnSaved);

        return boardColumnSaved;
    }

    public BoardColumn getBoardColumnById(String boardColumnId) {

        if (boardColumnId == null)
            return null;

        return boardColumnRepository.findById(boardColumnId).orElse(null);

    }

    public BoardColumn updateBoardColumn(BoardColumn boardColumn) {

        if (boardColumn.getId() == null)
            return null;

        return boardColumnRepository.save(boardColumn);
    }

    public void deleteBoardColumn(String boardColumnId, String boardId) {

        if (boardColumnId == null)
            return;

        BoardColumn boardColumn = getBoardColumnById(boardColumnId);

        boardColumnRepository.deleteById(boardColumnId);

        boardService.removeColumnFromBoard(boardId, boardColumn);

    }

    public List<BoardColumn> getBoardColumnByBoardId(String boardId) {

        if (boardId == null)
            return null;

        return boardColumnRepository.findByBoardId(boardId);
    }

    public void addTaskToBoardColumn(String boardColumnId, Task task) {

        if (boardColumnId == null || task == null)
            return;

        BoardColumn boardColumn = getBoardColumnById(boardColumnId);

        boardColumn.getTasks().add(task);

        boardColumnRepository.save(boardColumn);

    }

    public void removeTaskFromBoardColumn(String boardColumnId, Task task) {

        if (boardColumnId == null || task == null)
            return;

        BoardColumn boardColumn = getBoardColumnById(boardColumnId);

        boardColumn.getTasks().remove(task);

        boardColumnRepository.save(boardColumn);

    }

    public List<BoardColumn> getAllBoardColumns() {
        return boardColumnRepository.findAll();
    }

}
