package kanban.example.kanban.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kanban.example.kanban.collections.Task;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.dto.TaskDto;
import kanban.example.kanban.mappers.TaskMapper;
import kanban.example.kanban.services.AuthenticationService;
import kanban.example.kanban.services.TaskService;
import kanban.example.kanban.services.UserService;
import kanban.example.kanban.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Task>> createTask(HttpServletRequest request, @RequestBody Task task) {
        try {

            String id = authenticationService.getUserIdFromToken(request);

            User user = userService.getUserById(id);

            task.setCreatedByUser(user);

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

    @GetMapping("/board-column/{boardColumnId}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByBoardColumn(HttpServletRequest request,
            @PathVariable String id) {

        try {

            List<Task> tasks = taskService.getTasksByBoardId(id);

            List<TaskDto> taskDtos = TaskMapper.mapToDtoList(tasks);

            ApiResponse<List<TaskDto>> response = ApiResponse.<List<TaskDto>>success("Tasks found",
                    HttpStatus.CREATED.value(), taskDtos);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<List<TaskDto>> response = ApiResponse.<List<TaskDto>>error("Tasks not found",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDto>> getTaskById(HttpServletRequest request, @PathVariable String id) {

        try {

            Task task = taskService.getTaskById(id);

            TaskDto taskDto = TaskMapper.mapToDto(task);

            ApiResponse<TaskDto> response = ApiResponse.<TaskDto>success("Task found", HttpStatus.CREATED.value(),
                    taskDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<TaskDto> response = ApiResponse.<TaskDto>error("Task not found",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(HttpServletRequest request,
            @RequestBody Task task) {

        try {

            Task oldTask = taskService.getTaskById(task.getId());

            if (task.getTitle() != null) {
                oldTask.setTitle(task.getTitle());
            }

            if (task.getDescription() != null) {
                oldTask.setDescription(task.getDescription());
            }

            Task updatedTask = taskService.updateTask(oldTask);

            TaskDto taskDto = TaskMapper.mapToDto(updatedTask);

            ApiResponse<TaskDto> response = ApiResponse.<TaskDto>success("Task updated", HttpStatus.CREATED.value(),
                    taskDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<TaskDto> response = ApiResponse.<TaskDto>error("Task not updated",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/status/{taskId}/{newColumnBoardId}")
    public ResponseEntity<ApiResponse<String>> updateTaskStatus(HttpServletRequest request,
            @PathVariable String taskId, @PathVariable String newColumnBoardId) {

        try {

            taskService.updateTaskStatus(taskId, newColumnBoardId);

            ApiResponse<String> response = ApiResponse.<String>success("Task status updated",
                    HttpStatus.CREATED.value(), "Task status updated");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.<String>error("Task status not updated",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/assign/{taskId}/{userId}")
    public ResponseEntity<ApiResponse<String>> assignTask(HttpServletRequest request, @PathVariable String taskId,
            @PathVariable String userId) {

        try {

            taskService.assignTaskToUser(taskId, userId);

            ApiResponse<String> response = ApiResponse.<String>success("Task assigned", HttpStatus.CREATED.value(),
                    "Task assigned");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.<String>error("Task not assigned",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unassign/{taskId}")
    public ResponseEntity<ApiResponse<String>> unassignTask(HttpServletRequest request, @PathVariable String taskId) {

        try {

            taskService.unassignTask(taskId);

            ApiResponse<String> response = ApiResponse.<String>success("Task unassigned", HttpStatus.CREATED.value(),
                    "Task unassigned");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.<String>error("Task not unassigned",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{taskId}")
    public ResponseEntity<ApiResponse<String>> deleteTask(HttpServletRequest request, @PathVariable String taskId) {

        try {

            taskService.deleteTask(taskId);

            ApiResponse<String> response = ApiResponse.<String>success("Task deleted", HttpStatus.CREATED.value(),
                    "Task deleted");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.<String>error("Task not deleted",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
