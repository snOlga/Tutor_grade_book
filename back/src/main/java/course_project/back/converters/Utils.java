package course_project.back.converters;

import java.util.UUID;

public class Utils {
    public static UUID fromDTO(String id) {
        return id == null || id.isEmpty() || id.isBlank() ? null : UUID.fromString(id);
    }
}
