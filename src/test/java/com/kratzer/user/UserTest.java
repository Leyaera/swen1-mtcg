package com.kratzer.user;

import com.google.common.hash.Hashing;
import com.kratzer.app.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach()
    void beforeEach() {
        user = new User();
        user.setUsername("username");
        user.setPassword(Hashing.sha256().hashString("password", StandardCharsets.UTF_8).toString());   // password
    }

    @Test
    @DisplayName ("Authorize User with wrong password")
    void testAuthorizedWrong() {
        boolean authorized = user.authorized("wrong password");
        assertFalse(authorized);
    }

    @Test
    @DisplayName ("Authorize User with correct password")
    void testAuthorizedCorrect() {
        boolean authorized = user.authorized(Hashing.sha256().hashString("password", StandardCharsets.UTF_8).toString());
        assertTrue(authorized);
    }
}