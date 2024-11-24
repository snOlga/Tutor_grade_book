package course_project.back.enums;

public enum UserRoles {
    ADMIN(1, "ROLE_ADMIN"),
    TUTOR(2, "ROLE_TUTOR"),
    STUDENT(3, "ROLE_STUDENT");

    private final int id;
    private final String roleName;

    UserRoles(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public static UserRoles fromId(int id) {
        for (UserRoles role : UserRoles.values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role ID: " + id);
    }
}
