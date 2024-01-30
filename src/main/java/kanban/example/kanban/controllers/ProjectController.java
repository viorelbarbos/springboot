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
import kanban.example.kanban.pojo.RemoveMemberFromProjectRequest;
import kanban.example.kanban.services.AuthenticationService;
import kanban.example.kanban.services.ProjectService;
import kanban.example.kanban.services.UserService;
import kanban.example.kanban.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

        try {

            String userId = authenticationService.getUserIdFromToken(request);
            List<Project> projects = projectService.getProjectsByUser(userId);

            List<ProjectDto> projectDtos = ProjectMapper.mapToDtoList(projects);

            ApiResponse<List<ProjectDto>> apiResponse = ApiResponse.success(" Projects fetched succesfully ",
                    HttpStatus.OK.value(), projectDtos);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while fetching projects", e);

            ApiResponse<List<ProjectDto>> apiResponse = ApiResponse.error("An error occured while fetching projects",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(HttpServletRequest request,
            @RequestBody Project project) {

        try {

            String id = authenticationService.getUserIdFromToken(request);
            User user = userService.getUserById(id);

            project.setCreatedByUser(user);
            Project newProject = projectService.createProject(project);

            ProjectDto projectDto = ProjectMapper.mapToDto(newProject);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project created succesfully ",
                    HttpStatus.CREATED.value(), projectDto);

            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("An error occured while creating project", e);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.error("An error occured while creating project",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<ProjectDto>> addMembersToProject(HttpServletRequest request,
            @RequestBody AddMembersToProjectRequest data) {

        try {
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
        } catch (Exception e) {
            log.error("An error occured while adding members to project", e);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.error("An error occured while adding members to project",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/member/remove")
    public ResponseEntity<ApiResponse<ProjectDto>> removeMemberFromProject(HttpServletRequest request,
            @RequestBody RemoveMemberFromProjectRequest data) {

        try {
            String projectId = data.getProjectId();
            String memberId = data.getMemberId();

            if (projectId == null || memberId == null)
                return null;

            String userId = authenticationService.getUserIdFromToken(request);

            Project project = projectService.getProjectById(projectId);

            if (project == null)
                return null;

            if (!project.getCreatedByUser().getId().equals(userId))
                return null;

            User user = userService.getUserById(memberId);

            Project updatedProject = projectService.removeMemberFromProject(projectId, user);

            ProjectDto projectDto = ProjectMapper.mapToDto(updatedProject);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project updated succesfully ",
                    HttpStatus.OK.value(), projectDto);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            log.error("An error occured while removing member from project", e);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.error(
                    "An error occured while removing member from project",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<ProjectDto>> updateProject(HttpServletRequest request,
            @RequestBody Project project) {

        try {

            String userId = authenticationService.getUserIdFromToken(request);

            Project projectFromDb = projectService.getProjectById(project.getId());

            if (projectFromDb == null)
                return null;

            if (!projectFromDb.getCreatedByUser().getId().equals(userId))
                return null;

            if (project.getName() != null)
                projectFromDb.setName(project.getName());

            if (project.getDescription() != null)
                projectFromDb.setDescription(project.getDescription());

            Project updatedProject = projectService.updateProject(projectFromDb);

            ProjectDto projectDto = ProjectMapper.mapToDto(updatedProject);

            ApiResponse<ProjectDto> apiResponse = ApiResponse.success(" Project updated succesfully ",
                    HttpStatus.OK.value(), projectDto);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {

            log.error("An error occured while updating project", e);
            ApiResponse<ProjectDto> apiResponse = ApiResponse.error("An error occured while updating project",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
