package kanban.example.kanban.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.User;

@Repository
public interface UserRepository  extends MongoRepository<User, String>{

    User findByEmail(String email);

}
