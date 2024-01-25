package kanban.example.kanban.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import kanban.example.kanban.collections.User;

@Repository
public interface UserRepository  extends MongoRepository<User, String>{

    Optional<User> findByEmail(String email);

}
