package kanban.example.kanban.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int statusCode;
    private T data;
    private boolean success;

    public static <T> ApiResponse<T> success(String message, int statusCode, T data) {
        return new ApiResponse<>(message, statusCode, data, true);
    }

    public static <T> ApiResponse<T> error(String message, int statusCode) {
        return new ApiResponse<>(message, statusCode, null, false);
    }
}