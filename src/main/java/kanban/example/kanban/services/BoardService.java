package kanban.example.kanban.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.Board;
import kanban.example.kanban.collections.BoardColumn;
import kanban.example.kanban.repositories.BoardRepository;
import kanban.example.kanban.utils.Utils;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private Utils utils;

    public Board createBoard(String projectId, Board board) {

        board.setCreatedAt(utils.getDate());
        Board savedBoard = boardRepository.save(board);

        projectService.addBoardToProject(projectId, savedBoard);

        return savedBoard;

    }

    public Board getBoardById(String boardId) {

        if (boardId == null)
            return null;

        return boardRepository.findById(boardId).orElse(null);
    }

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board updateBoard(Board board) {
        if (board.getId() == null) {
            return null;
        }
        board.setUpdatedAt(utils.getDate());
        return boardRepository.save(board);
    }

    public void deleteBoard(String boardId, String projectId) {

        if (boardId == null)
            return;

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null)
            return;

        projectService.removeBoardFromProject(projectId, board);

        boardRepository.deleteById(boardId);
    }

    public Board addColumnToBoard(String boardId, BoardColumn column) {

        if (boardId == null || column == null)
            return null;

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null)
            return null;

        board.getColumns().add(column);
        return boardRepository.save(board);
    }

    public Board removeColumnFromBoard(String boardId, BoardColumn column) {

        if (boardId == null || column == null)
            return null;

        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null)
            return null;

        board.getColumns().remove(column);
        return boardRepository.save(board);
    }

}
