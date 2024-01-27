package kanban.example.kanban.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.Task;
import kanban.example.kanban.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByUser(String id) {
        return taskRepository.findByCreatedByUserId(id);
    }

    public List<Task> getTasksByBoardId(String boardId) {
        return taskRepository.findByBoardId(boardId);
    }

    public Task getTaskById(String id) {
        return taskRepository.findById(id).get();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {

        Task taskToUpdate = taskRepository.findById(task.getId()).get();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setAssignedUserId(task.getAssignedUserId());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setUpdatedAt(task.getUpdatedAt());
        taskToUpdate.setFinishedAt(task.getFinishedAt());
        taskToUpdate.setStatus(task.getStatus());
        taskToUpdate.setBoardId(task.getBoardId());
        taskToUpdate.setCreatedByUserId(task.getCreatedByUserId());
        return taskRepository.save(taskToUpdate);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public void updateTaskStatus(String id, String status) {

        Task task = taskRepository.findById(id).get();
        task.setStatus(status);
        taskRepository.save(task);
    }

}
