package kanban.example.kanban.mappers;

import java.util.List;

import kanban.example.kanban.collections.BoardColumn;
import kanban.example.kanban.dto.BoardColumnDto;

public class BoardColumnMapper {

    public static BoardColumnDto mapToDto(BoardColumn boardColumn) {
        return BoardColumnDto.builder()
                .id(boardColumn.getId())
                .title(boardColumn.getTitle())
                .color(boardColumn.getColor())
                .boardId(boardColumn.getBoardId())
                .tasks(TaskMapper.mapToDtoList(boardColumn.getTasks()))
                .build();

    }

    public static List<BoardColumnDto> mapToDtoList(List<BoardColumn> boardColumns) {

        if (boardColumns == null)
            return null;

        return boardColumns.stream().map(BoardColumnMapper::mapToDto).collect(java.util.stream.Collectors.toList());
    }

}
