package course_project.back.repository;

import course_project.back.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest loads a minimal set of Spring components needed for JPA tests.
// Replace.NONE is used here to potentially test against your actual database (e.g., PostgreSQL),
// but for true unit tests, you might use Replace.ANY for an in-memory database like H2.
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository; // Placeholder: Assumes you have a UserRepository

    @Test
    void whenFindByUsername_thenReturnUser() {
        // Arrange: Create and save a User entity to the database
        UserEntity newUser = new UserEntity();
        newUser.setEmail("testuser");
        UserEntity savedUser = userRepository.save(newUser);

        // Act: Retrieve the user by their username
        UserEntity foundUser = userRepository.findByEmail(savedUser.getEmail());

        // Assert: Verify the user was found and data matches
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("testuser");
    }

    @Test
    void whenFindByUsername_thenNotFound() {
        // Act: Search for a username that does not exist
        UserEntity foundUser = userRepository.findByEmail("nonexistent_user");

        // Assert: Verify no user was found (result is null)
        assertThat(foundUser).isNull();
    }
}