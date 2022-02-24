package com.kratzer.app.service.user;

import com.kratzer.app.model.user.User;
import com.kratzer.app.model.user.UserInterface;
import com.kratzer.app.service.database.DatabaseService;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserServiceInterface {
    private static UserService userService;

    public UserService() {
    }

    public static UserService getUserService() {
        if (UserService.userService == null) {
            UserService.userService = new UserService();
        }
        return UserService.userService;
    }

    @Override
    public UserInterface getUserById(int id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, username, password, token, elo, coins, status FROM users WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),        // id
                        resultSet.getString(2),     // username
                        resultSet.getString(3),     // password
                        resultSet.getString(4),     // token
                        resultSet.getInt(5),        // elo
                        resultSet.getInt(6),        // coins
                        resultSet.getString(7));    // status
                resultSet.close();
                preparedStatement.close();
                conn.close();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserInterface getUserByIdWithoutPassword(int id) {
        if (id == 0) {
            return null;
        }
        User user = (User) this.getUserById(id);
        user.setPassword(null);
        user.setToken(null);
        return user;
    }

    @Override
    public UserInterface getUserByUsername(String username) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, username, password, token, elo, coins, status FROM users WHERE username=?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = (User) this.getUserById(resultSet.getInt(1));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserInterface getUserByToken(String token) {
        try {
            if (token.equals("")) {
                return null;
            }
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, username, password, token, elo, coins, status FROM users WHERE token = ?;");
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                User user = (User) this.getUserById(resultSet.getInt(1));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserInterface getUserByUsernameWithoutPassword(String username) {
        if (username == null) {
            return null;
        }
        User user = (User) this.getUserByUsername(username);
        user.setPassword(null);
        user.setToken(null);
        return user;
    }

    @Override
    public UserInterface addUser(UserInterface user) {
        try {
            if (this.getUserByUsername(user.getUsername()) != null) {
                return null;
            }

            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users(username, password, token, elo, coins, status) VALUES(?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getToken());
            preparedStatement.setInt(4, user.getElo());
            preparedStatement.setInt(5, user.getCoins());
            preparedStatement.setString(6, user.getStatus());

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return user;
        } catch (SQLException ignored) {

        }
        return null;
    }

    @Override
    public UserInterface updateUser(int id, UserInterface user) {
        User userBeforeUpdate = (User) this.getUserById(id);
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET username = ?, password = ?, token = ?, elo = ?, coins = ?, status = ? WHERE id = ?;");

            preparedStatement.setString(1, user.getUsername() != null ? user.getUsername() : userBeforeUpdate.getUsername());
            preparedStatement.setString(2, user.getPassword() != null ? user.getPassword() : userBeforeUpdate.getPassword());
            preparedStatement.setString(3, user.getToken() != null ? user.getToken() : userBeforeUpdate.getToken());
            preparedStatement.setInt(4, user.getElo());
            preparedStatement.setInt(5, user.getCoins());
            preparedStatement.setString(6, user.getStatus() != null ? user.getStatus() : userBeforeUpdate.getStatus());
            preparedStatement.setInt(7, id);

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return this.getUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM users WHERE id = ?;");

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<UserInterface> getUsers() {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, username, password, token, elo, coins, status FROM users;");

            List<UserInterface> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new User(
                        resultSet.getInt(1),        // id
                        resultSet.getString(2),     // username
                        resultSet.getString(3),     // password
                        resultSet.getString(4),     // token
                        resultSet.getInt(5),        // elo
                        resultSet.getInt(6),        // coins
                        resultSet.getString(7)));   // status
            }

            statement.close();
            resultSet.close();
            conn.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
