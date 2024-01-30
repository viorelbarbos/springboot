package kanban.example.kanban.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.Board;
import kanban.example.kanban.collections.BoardColumn;
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

}
