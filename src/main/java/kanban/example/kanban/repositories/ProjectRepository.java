package kanban.example.kanban.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByCreatedByUserId(ObjectId userId);

    List<Project> findBymembers(ObjectId userId);

}
