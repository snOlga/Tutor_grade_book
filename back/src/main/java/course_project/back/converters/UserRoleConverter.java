package course_project.back.converters;

import course_project.back.enums.UserRoles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRoles, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRoles role) {
        if (role == null) {
            return null;
        }
        return role.getId();
    }

    @Override
    public UserRoles convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        UserRoles currentUser = null;
        for (UserRoles role : UserRoles.values()) {
            if (role.getId() == id) {
                currentUser = role;
            }
        }
        return currentUser;
    }
}
