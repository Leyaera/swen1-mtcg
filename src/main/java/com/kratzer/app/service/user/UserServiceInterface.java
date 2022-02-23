package com.kratzer.app.service.user;

import java.util.List;
import com.kratzer.app.model.user.UserInterface;

public interface UserServiceInterface {
    UserInterface getUserById(int id);

    UserInterface getUserByIdWithoutPassword(int id);

    UserInterface getUserByUsername(String username);

    UserInterface getUserByUsernameWithoutPassword(String username);

    List<UserInterface> getUsers();

    UserInterface addUser(UserInterface user);

    UserInterface updateUser(int id, UserInterface user);

    boolean deleteUser(int id);
}
