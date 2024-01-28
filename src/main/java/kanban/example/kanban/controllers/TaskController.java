package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Task;
import kanban.example.kanban.services.AuthenticationService;
import kanban.example.kanban.services.TaskService;
import kanban.example.kanban.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    private final AuthenticationService authenticationService;

    public TaskController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@RequestBody Task task) {
        try {
            Task newTask = taskService.createTask(task);
            ApiResponse<Task> response = ApiResponse.success("Task created successfully", HttpStatus.CREATED.value(),
                    newTask);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<Task> response = ApiResponse.error("Task already exists",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<Task>>> getTasksByUser(HttpServletRequest request) {

        String id = authenticationService.getUserIdFromToken(request);

        try {

            List<Task> tasks = taskService.getTasksByUser(id);

            ApiResponse<List<Task>> response = ApiResponse.<List<Task>>success("Tasks found",
                    HttpStatus.CREATED.value(), tasks);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<List<Task>> response = ApiResponse.<List<Task>>error("Tasks not found",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
