package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

class CustomerServiceTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void contactsConstraints() {
        DBConnectionProvider connectionProvider = new DBConnectionProvider(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        try (Connection conn = connectionProvider.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("""
                    CREATE TABLE contacts (
                        ID SERIAL PRIMARY KEY,
                        phone VARCHAR(50) CHECK (phone ~ '^[0-9]*$'),
                        email VARCHAR(50) CHECK (email ~ '^[0-z]+@([A-z]+\\.)+[A-z]{2,4}$')
                     );""");
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}