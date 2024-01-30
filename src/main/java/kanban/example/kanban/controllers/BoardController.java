package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kanban.example.kanban.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Board;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.BoardDto;
import kanban.example.kanban.mappers.BoardMapper;
import kanban.example.kanban.pojo.CreateBoardRequest;
import kanban.example.kanban.services.BoardService;
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

}
