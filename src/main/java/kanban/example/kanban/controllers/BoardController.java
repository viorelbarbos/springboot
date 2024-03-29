package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kanban.example.kanban.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Board;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.BoardDto;
import kanban.example.kanban.dto.UserDto;
import kanban.example.kanban.mappers.BoardMapper;
import kanban.example.kanban.mappers.UserMapper;
import kanban.example.kanban.pojo.CreateBoardRequest;
import kanban.example.kanban.services.BoardService;
import kanban.example.kanban.services.ProjectService;
import kanban.example.kanban.services.UserService;
import kanban.example.kanban.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardDto>>> getBoards(HttpServletRequest request) {

        try {

            List<Board> board = boardService.getBoards();

            List<BoardDto> boardDtos = BoardMapper.mapToDtoList(board);

            return new ResponseEntity<>(ApiResponse.success("Boards fetched succesfully", HttpStatus.OK.value(),
                    boardDtos), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching boards", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching boards",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping
    public ResponseEntity<ApiResponse<BoardDto>> createBoard(HttpServletRequest request,
            @RequestBody CreateBoardRequest data) {

        try {

            String userId = authenticationService.getUserIdFromToken(request);
            User user = userService.getUserById(userId);

            Board board = data.getBoard();
            board.setCreatedByUser(user);

            String projectId = data.getProjectId();

            Board createdBoard = boardService.createBoard(projectId, board);

            BoardDto boardDto = BoardMapper.mapToDto(createdBoard);

            return new ResponseEntity<>(ApiResponse.success("Board created succesfully", HttpStatus.CREATED.value(),
                    boardDto), HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("An error occured while creating board", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while creating board" + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<String>> deleteBoard(HttpServletRequest request, @PathVariable String boardId) {

        try {

            String userId = authenticationService.getUserIdFromToken(request);
            User user = userService.getUserById(userId);

            Board board = boardService.getBoardById(boardId);

            if (board == null) {
                return new ResponseEntity<>(ApiResponse.error("Board not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }

            if (!board.getCreatedByUser().getId().equals(user.getId())) {
                return new ResponseEntity<>(ApiResponse.error("You are not authorized to delete this board",
                        HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
            }

            boardService.deleteBoard(boardId, board.getProjectId());

            return new ResponseEntity<>(ApiResponse.success("Board deleted succesfully", HttpStatus.OK.value(),
                    boardId), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while deleting board", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while deleting board",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardDto>> getBoardById(HttpServletRequest request,
            @PathVariable String boardId) {

        try {

            Board board = boardService.getBoardById(boardId);

            if (board == null) {
                return new ResponseEntity<>(ApiResponse.error("Board not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }

            BoardDto boardDto = BoardMapper.mapToDto(board);

            return new ResponseEntity<>(ApiResponse.success("Board fetched succesfully", HttpStatus.OK.value(),
                    boardDto), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching board", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching board",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    // update board by id
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<BoardDto>> updateBoard(HttpServletRequest request, @RequestBody Board board) {

        try {

            Board oldBoard = boardService.getBoardById(board.getId());

            if (oldBoard == null) {
                return new ResponseEntity<>(ApiResponse.error("Board not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }

            if (board.getName() != null) {
                oldBoard.setName(board.getName());
            }

            if (board.getDescription() != null) {
                oldBoard.setDescription(board.getDescription());
            }

            Board updatedBoard = boardService.updateBoard(oldBoard);

            BoardDto boardDto = BoardMapper.mapToDto(updatedBoard);

            return new ResponseEntity<>(ApiResponse.success("Board updated succesfully", HttpStatus.OK.value(),
                    boardDto), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while updating board", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while updating board",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<BoardDto>>> getBoardsByProjectId(HttpServletRequest request,
            @PathVariable String projectId) {

        try {

            List<Board> boards = boardService.getBoardsByProjectId(projectId);

            List<BoardDto> boardDtos = BoardMapper.mapToDtoList(boards);

            return new ResponseEntity<>(ApiResponse.success("Boards fetched succesfully", HttpStatus.OK.value(),
                    boardDtos), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching boards", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching boards",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    // get all users that are part of the project of the board
    @GetMapping("/users/{boardId}")
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsersByBoardId(HttpServletRequest request,
            @PathVariable String boardId) {

        try {

            // get the project id of the board
            Board board = boardService.getBoardById(boardId);

            if (board == null) {
                return new ResponseEntity<>(ApiResponse.error("Board not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }

            List<User> users = projectService.getMembersByProjectId(board.getProjectId());

            List<UserDto> userDtos = UserMapper.mapToDtoList(users);

            return new ResponseEntity<>(ApiResponse.success("Users fetched succesfully", HttpStatus.OK.value(),
                    userDtos), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching users", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching users",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
