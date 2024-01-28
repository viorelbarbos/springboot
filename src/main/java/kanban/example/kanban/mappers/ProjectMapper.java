package kanban.example.kanban.mappers;

import java.util.List;

import kanban.example.kanban.collections.Project;
import kanban.example.kanban.dto.ProjectDto;

public class ProjectMapper {

    public static ProjectDto mapToDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .createdByUser(UserMapper.mapToDto(project.getCreatedByUser()))
                .members(UserMapper.mapToDtoList(project.getMembers()))
                .boards(BoardMapper.mapToDtoList(project.getBoards()))
                .build();

    }

    public static List<ProjectDto> mapToDtoList(List<Project> projects) {
        return projects.stream().map(ProjectMapper::mapToDto).collect(java.util.stream.Collectors.toList());
    }

}
