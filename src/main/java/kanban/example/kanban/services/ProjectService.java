package kanban.example.kanban.services;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.Board;
import kanban.example.kanban.collections.Project;
import kanban.example.kanban.collections.User;
import kanban.example.kanban.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {

        if (project == null)
            return null;
        project.setCreatedAt(getDate());
        return projectRepository.save(project);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        if (id == null)
            return null;
        return projectRepository.findById(id).get();
    }

    public List<Project> getProjectsByUser(String userId) {

        return projectRepository.findByCreatedByUserId(new ObjectId(userId));

    }

    public List<Project> getProjectsWhereUserIsMember(String userId) {
        return projectRepository.findBymembers(new ObjectId(userId));
    }

    public Project updateProject(Project project) {
        if (project.getId() == null)
            return null;
        project.setUpdatedAt(getDate());
        return projectRepository.save(project);
    }

    public Project addMemberToProject(String projectId, User user) {

        if (projectId == null || user == null)
            return null;

        Project project = projectRepository.findById(projectId).get();

        if (project == null)
            return null;

        project.getMembers().add(user);

        return projectRepository.save(project);
    }

    public Project addMembersToProject(String projectId, List<User> users) {

        if (projectId == null || users == null)
            return null;

        Project project = projectRepository.findById(projectId).get();

        if (project == null)
            return null;

        for (User user : users) {
            if (!project.getMembers().contains(user))
                project.getMembers().add(user);
        }

        return projectRepository.save(project);
    }

    public Project removeMemberFromProject(String projectId, User user) {

        if (projectId == null || user == null)
            return null;

        Project project = projectRepository.findById(projectId).get();

        if (project == null)
            return null;

        project.getMembers().remove(user);

        return projectRepository.save(project);
    }

    public Project addBoardToProject(String projectId, Board board) {

        if (projectId == null || board == null)
            return null;

        Project project = projectRepository.findById(projectId).get();

        if (project == null)
            return null;

        project.getBoards().add(board);

        return projectRepository.save(project);
    }

    public Project removeBoardFromProject(String projectId, Board board) {

        if (projectId == null || board == null)
            return null;

        Project project = projectRepository.findById(projectId).get();

        if (project == null)
            return null;

        project.getBoards().remove(board);

        return projectRepository.save(project);
    }

    public void deleteProject(String id) {

        if (id == null)
            return;

        projectRepository.deleteById(id);
    }

    private String getDate() {
        return new Date(System.currentTimeMillis()).toString();
    }

}
