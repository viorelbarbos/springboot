package kanban.example.kanban.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.BoardColumn;
import kanban.example.kanban.collections.Task;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.repositories.TaskRepository;
import kanban.example.kanban.utils.Utils;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private Utils utils;

    @Autowired
    private UserService userService;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(String id) {
        return taskRepository.findByCreatedByUser(id);
    }

    public List<Task> getTasksByBoardId(String boardId) {
        return taskRepository.findByBoardColumnId(boardId);
    }

    public Task getTaskById(String id) {

        if (id == null)
            return null;

        return taskRepository.findById(id).get();
    }

    public Task createTask(Task task) {

        task.setCreatedAt(utils.getDate());

        Task createdTask = taskRepository.save(task);

        boardColumnService.addTaskToBoardColumn(createdTask.getBoardColumnId(), createdTask);

        return createdTask;

    }

    public Task updateTask(Task task) {

        if (task.getId() == null)
            return null;

        task.setUpdatedAt(utils.getDate());

        return taskRepository.save(task);
    }

    public void deleteTask(String id) {

        if (id == null)
            return;

        Task task = taskRepository.findById(id).get();

        boardColumnService.removeTaskFromBoardColumn(task.getBoardColumnId(), task);
        taskRepository.deleteById(id);
    }

    public void updateTaskStatus(String taskId, String newColumnBoardId) {

        if (taskId == null || newColumnBoardId == null)
            return;

        Task task = taskRepository.findById(taskId).get();

        BoardColumn oldBoardColumn = boardColumnService.getBoardColumnById(task.getBoardColumnId());
        BoardColumn newBoardColumn = boardColumnService.getBoardColumnById(newColumnBoardId);

        boardColumnService.removeTaskFromBoardColumn(oldBoardColumn.getId(), task);
        boardColumnService.addTaskToBoardColumn(newBoardColumn.getId(), task);

        task.setBoardColumnId(newColumnBoardId);
        task.setStatus(newBoardColumn.getTitle());

        taskRepository.save(task);
    }

    public void assignTaskToUser(String taskId, String userId) {

        if (taskId == null || userId == null)
            return;

        Task task = taskRepository.findById(taskId).get();

        User user = userService.getUserById(userId);

        task.setAssignedUser(user);

        taskRepository.save(task);
    }

    public void unassignTask(String taskId) {
        if (taskId == null)
            return;

        Task task = taskRepository.findById(taskId).get();

        task.setAssignedUser(null);

        taskRepository.save(task);
    }

}
