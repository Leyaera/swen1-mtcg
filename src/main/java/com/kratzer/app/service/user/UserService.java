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

    public UserInterface getUserByUsername(String username) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, username, password, token, elo, coins, status FROM users WHERE username=?");
            preparedStatement.setString(1, username);
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
            if (affectedRows == 0) {
                return null;
            }

            /*try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return this.getUserById(generatedKeys.getInt(1));
                }
                generatedKeys.close();
            } catch (SQLException ignored) {

            }*/

            preparedStatement.close();
            conn.close();
            return user;
        } catch (SQLException ignored) {

        }
        return null;
    }





    /*private List<User> userData;

    public UserService() {


        userData = new ArrayList<>();
        userData.add(new User(1,"Carmen", "abc123", 10));
        userData.add(new User(2,"Steffi", "abc123",10));
        userData.add(new User(3,"Obaid", "abc123", 10));
    }

    // POST /users
    public void addUser(User user) {
        userData.add(user);
    }

    // GET /users
    public List<User> getUsers() {
        return userData;
    }*/
}
