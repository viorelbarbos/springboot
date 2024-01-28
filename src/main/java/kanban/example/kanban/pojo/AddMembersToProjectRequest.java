package kanban.example.kanban.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddMembersToProjectRequest {
    private String projectId;
    private List<String> newMembersIds;
}
