package kanban.example.kanban.utils;

import java.util.Date;

import org.springframework.stereotype.Component;


@Component
public class Utils {
    public String getDate() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
