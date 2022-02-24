package com.kratzer.user;

import com.kratzer.app.model.user.User;
import com.kratzer.app.service.database.DatabaseService;
import com.kratzer.app.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static UserService userService;

    @BeforeAll
    static void beforeAll() {
        userService = UserService.getUserService();
    }

    @BeforeEach
    void beforeEach() {
        // Delete user with id -1 before every test
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM users WHERE id = -1;");
            sm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with existing ID")
    void testGetUserByIdExistingId() {
        try {
            // arrange
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'username', 'password')");

            // act
            User user = (User) userService.getUserById(-1);

            sm.executeUpdate("DELETE FROM users WHERE id = -1");
            sm.close();
            conn.close();

            // assert
            assertEquals("username", user.getUsername());
            assertEquals(-1, user.getId());
            assertEquals("password", user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with non existing ID")
    void testGetUserByIdNonExistingId() {
        // act
        User user = (User) userService.getUserById(-1);

        // assert
        assertNull(user);
    }

    @Test
    @DisplayName("Get a user with existing username")
    void testGetUserByIdExistingUsername() {
        try {
            // arrange
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1, 'username', 'password')");

            // act
            User user = (User) userService.getUserByUsername("username");

            sm.executeUpdate("DELETE FROM users WHERE id = -1");
            sm.close();
            conn.close();

            // assert
            assertEquals("username", user.getUsername());
            assertEquals(-1, user.getId());
            assertEquals("password", user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a user with non existing Username")
    void testGetUserByIdNonExistingUsername() {
        // act
        User user = (User) userService.getUserByUsername("no_username");

        // assert
        assertNull(user);
    }

    @Test
    @DisplayName("Add user")
    void testAddUser() {
        try {
            // arrange
            String username = "test-username";
            String password = "test-password";

            // act
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user = (User) userService.addUser(user);

            // assert
            assertNotNull(user.getUsername());
            assertNotNull(user.getPassword());
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, username, password FROM users WHERE username = '" + user.getUsername() + "';");
            assertTrue(resultSet.next());
            assertTrue(resultSet.getInt(1) >= 0);   // id
            assertEquals(username, resultSet.getString(2)); // username
            assertEquals(password, resultSet.getString(3)); // password

            // delete user after testing
            statement.executeUpdate("DELETE FROM users WHERE username = '" + username + "';");
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Update user")
    void testUpdateUser() {
        try {
            // arrange
            String username = "test-username";
            String username2 = "test-username2";
            String password = "test-password";
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1,'" + username + "', '" + password + "');");

            // act
            User user = new User();
            user.setUsername(username2);
            user.setPassword(password);
            user = (User) userService.updateUser(-1, user);

            // assert
            assertNotNull(user);
            assertEquals(user.getPassword(), password);
            assertEquals(user.getUsername(), username2);

            statement.executeUpdate("DELETE FROM users WHERE id = -1;");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete user")
    void testDeleteUser() {
        try {
            // arrange
            String username = "test-username";
            String password = "test-password";
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO users(id, username, password) VALUES(-1,'" + username + "', '" + password + "');");

            // act
            boolean result = userService.deleteUser(-1);

            // assert
            assertTrue(result);
            ResultSet resultSet = statement.executeQuery("SELECT id FROM users WHERE id = -1;");
            assertFalse(resultSet.next());

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}