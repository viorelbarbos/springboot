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

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Project;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.ProjectDto;
import kanban.example.kanban.mappers.ProjectMapper;
import kanban.example.kanban.pojo.AddMembersToProjectRequest;
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
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(HttpServletRequest request,
            @RequestBody Project project) {

        String id = authenticationService.getUserIdFromToken(request);
        User user = userService.getUserById(id);

        project.setCreatedByUser(user);
        Project newProject = projectService.createProject(project);

        ProjectDto projectDto = ProjectMapper.mapToDto(newProject);

        ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project created succesfully ",
                HttpStatus.CREATED.value(), projectDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<ProjectDto>> addMembersToProject(HttpServletRequest request,
            @RequestBody AddMembersToProjectRequest data) {

        String projectId = data.getProjectId();
        List<String> newMembersIds = data.getNewMembersIds();

        if (newMembersIds == null || projectId == null)
            return null;

        String userId = authenticationService.getUserIdFromToken(request);

        Project project = projectService.getProjectById(projectId);

        if (project == null)
            return null;

        if (!project.getCreatedByUser().getId().equals(userId))
            return null;

        List<User> newMembers = userService.getUsersByIds(newMembersIds);

        if (newMembers == null)
            return null;

        Project updatedProject = projectService.addMembersToProject(projectId, newMembers);

        ProjectDto projectDto = ProjectMapper.mapToDto(updatedProject);

        ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project updated succesfully ",
                HttpStatus.OK.value(), projectDto);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
