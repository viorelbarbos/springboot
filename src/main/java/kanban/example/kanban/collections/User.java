package kanban.example.kanban.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String userName;

    @Indexed(unique = true)
    private String email;
    private String password;
    private String createdAt;
    private String updatedAt;
}
