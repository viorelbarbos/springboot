package kanban.example.kanban.mappers;

import java.util.List;

import kanban.example.kanban.collections.Task;
import kanban.example.kanban.dto.TaskDto;

public class TaskMapper {

    public static TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .dueDate(task.getDueDate())
                .createdByUser(UserMapper.mapToDto(task.getCreatedByUser()))
                .assignedUser(UserMapper.mapToDto(task.getAssignedUser()))
                .finishedAt(task.getFinishedAt())
                .build();

    }

    public static List<TaskDto> mapToDtoList(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::mapToDto).collect(java.util.stream.Collectors.toList());
    }

}
