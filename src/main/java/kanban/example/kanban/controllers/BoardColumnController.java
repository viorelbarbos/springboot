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

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.BoardColumn;
import kanban.example.kanban.dto.BoardColumnDto;
import kanban.example.kanban.mappers.BoardColumnMapper;
import kanban.example.kanban.services.BoardColumnService;
import kanban.example.kanban.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/board-column")
public class BoardColumnController {

    @Autowired
    private BoardColumnService boardColumnService;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardColumnDto>> createBoardColumn(HttpServletRequest request,
            @RequestBody BoardColumn boardColumn) {

        try {

            String boardId = boardColumn.getBoardId();

            BoardColumn boardColumnSaved = boardColumnService.createBoardColumn(boardId, boardColumn);

            BoardColumnDto boardColumnDto = BoardColumnMapper.mapToDto(boardColumnSaved);

            return new ResponseEntity<>(
                    ApiResponse.success("Board column created succesfully", HttpStatus.OK.value(), boardColumnDto),
                    HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while creating board column", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while creating board column",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardColumnDto>>> getAllBoardColumns(HttpServletRequest request) {

        try {

            List<BoardColumn> boardColumns = boardColumnService.getAllBoardColumns();
            List<BoardColumnDto> boardColumnDtos = BoardColumnMapper.mapToDtoList(boardColumns);

            return new ResponseEntity<>(ApiResponse.success("Board columns fetched succesfully", HttpStatus.OK.value(),
                    boardColumnDtos), HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching board columns", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching board columns",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/{boardColumnId}")
    public ResponseEntity<ApiResponse<BoardColumnDto>> getBoardColumnById(HttpServletRequest request,
            @PathVariable String boardColumnId) {

        try {

            BoardColumn boardColumn = boardColumnService.getBoardColumnById(boardColumnId);

            BoardColumnDto boardColumnDto = BoardColumnMapper.mapToDto(boardColumn);

            return new ResponseEntity<>(
                    ApiResponse.success("Board column fetched succesfully", HttpStatus.OK.value(), boardColumnDto),
                    HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching board column", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching board column",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<ApiResponse<List<BoardColumnDto>>> getBoardColumnsByBoardId(HttpServletRequest request,
            @PathVariable String boardId) {

        try {

            List<BoardColumn> boardColumns = boardColumnService.getBoardColumnByBoardId(boardId);

            List<BoardColumnDto> boardColumnDtos = BoardColumnMapper.mapToDtoList(boardColumns);

            return new ResponseEntity<>(
                    ApiResponse.success("Board column fetched succesfully", HttpStatus.OK.value(), boardColumnDtos),
                    HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching board column", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while fetching board column",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<BoardColumnDto>> updateBoardColumn(HttpServletRequest request,
            @RequestBody BoardColumn boardColumn) {

        try {

            BoardColumn oldBoardColumn = boardColumnService.getBoardColumnById(boardColumn.getId());

            if (oldBoardColumn == null) {
                return new ResponseEntity<>(ApiResponse.error("Board column not found", HttpStatus.NOT_FOUND.value()),
                        HttpStatus.NOT_FOUND);
            }

            if (boardColumn.getTitle() != null) {
                oldBoardColumn.setTitle(boardColumn.getTitle());
            }

            if (boardColumn.getColor() != null) {
                oldBoardColumn.setColor(boardColumn.getColor());
            }

            BoardColumn updatedBoardColumn = boardColumnService.updateBoardColumn(oldBoardColumn);

            BoardColumnDto boardColumnDto = BoardColumnMapper.mapToDto(updatedBoardColumn);

            return new ResponseEntity<>(
                    ApiResponse.success("Board column updated succesfully", HttpStatus.OK.value(), boardColumnDto),
                    HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while updating board column", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while updating board column",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @DeleteMapping("/{boardColumnId}")
    public ResponseEntity<ApiResponse<String>> deleteBoardColumn(HttpServletRequest request,
            @PathVariable String boardColumnId) {

        try {

            BoardColumn boardColumn = boardColumnService.getBoardColumnById(boardColumnId);

            String boardId = boardColumn.getBoardId();

            boardColumnService.deleteBoardColumn(boardColumnId, boardId);

            return new ResponseEntity<>(
                    ApiResponse.success("Board column deleted succesfully", HttpStatus.OK.value(), null),
                    HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while deleting board column", e);

            return new ResponseEntity<>(ApiResponse.error("An error occured while deleting board column",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
