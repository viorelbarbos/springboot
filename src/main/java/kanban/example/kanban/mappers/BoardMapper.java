package kanban.example.kanban.mappers;

import java.util.List;

import kanban.example.kanban.collections.Board;
import kanban.example.kanban.dto.BoardDto;

public class BoardMapper {

    public static BoardDto mapToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .createdByUser(UserMapper.mapToDto(board.getCreatedByUser()))
                .columns(BoardColumnMapper.mapToDtoList(board.getColumns()))
                .build();

    }

    public static List<BoardDto> mapToDtoList(List<Board> boards) {

        if (boards == null)
            return null;

        return boards.stream().map(BoardMapper::mapToDto).collect(java.util.stream.Collectors.toList());
    }

}
