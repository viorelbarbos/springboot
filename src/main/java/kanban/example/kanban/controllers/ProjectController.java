package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Project;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.ProjectDto;
import kanban.example.kanban.mappers.ProjectMapper;
import kanban.example.kanban.services.AuthenticationService;
import kanban.example.kanban.services.ProjectService;
import kanban.example.kanban.services.UserService;
import kanban.example.kanban.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDto>>> getProjects(HttpServletRequest request) {

        String userId = authenticationService.getUserIdFromToken(request);
        List<Project> projects = projectService.getProjectsByUser(userId);

        List<ProjectDto> projectDtos = ProjectMapper.mapToDtoList(projects);

        ApiResponse<List<ProjectDto>> apiResponse = ApiResponse.success(" Projects fetched succesfully ",
                HttpStatus.OK.value(), projectDtos);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(HttpServletRequest request, Project project) {

        String id = authenticationService.getUserIdFromToken(request);
        User user = userService.getUserById(id);

        project.setCreatedByUser(user);
        Project newProject = projectService.createProject(project);

        ProjectDto projectDto = ProjectMapper.mapToDto(newProject);

        ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project created succesfully ",
                HttpStatus.CREATED.value(), projectDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
