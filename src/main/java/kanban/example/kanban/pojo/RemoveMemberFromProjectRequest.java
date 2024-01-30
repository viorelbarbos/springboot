package kanban.example.kanban.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveMemberFromProjectRequest {
     private String projectId;
     private String memberId;    
}
