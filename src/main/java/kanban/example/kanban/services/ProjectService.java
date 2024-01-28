package kanban.example.kanban.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kanban.example.kanban.collections.Project;
import kanban.example.kanban.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {

        if (project.getId() != null)
            return null;

        return projectRepository.save(project);
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
        return projectRepository.save(project);
    }

    public void deleteProject(String id) {

        if (id == null)
            return;

        projectRepository.deleteById(id);
    }

}
