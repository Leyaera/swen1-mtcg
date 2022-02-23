package com.kratzer.app.controller;

import com.kratzer.app.model.user.User;
// import com.kratzer.app.model.user.UserInterface;
import com.kratzer.app.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.server.Request;
import com.kratzer.server.Response;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Date;
// import java.util.Properties;
// import java.util.List;

public class UserController extends Controller {
    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    // POST /users
    public Response addUser(Request request) {
        User user;
        try {
            // request.getBody() => "{ \"id\": 4, \"username\": \"Username\", ... }
            user = this.getObjectMapper().readValue(request.getBody(), User.class);

            if (user != null && user.getUsername() != null && user.getPassword() != null) {
                user.setCoins(20);
                user.setElo(100);
                user.setStatus(("online"));

                // token for curl file
                if (user.getUsername().equals("kienboec")) {
                    user.setToken("kienboec-e7e0d40d4b4e11797b84327d70d4989690900833a8f6c0fe109d6f469e8ab983");
                } else if (user.getUsername().equals("altenhof")) {
                    user.setToken("altenhof-3e1a1cb4cd6c0cc38b10f1b5a4bab0083bcc963892babc623bf58b6f19973f2c");
                } else if (user.getUsername().equals("admin")) {
                    user.setToken("admin-193b8b736f5225f9ba10a293cc339a5a05f8cc4cd55551553077a4d59bf9b0fa");
                } else {
                    // random token
                    user.setToken(user.getUsername() + "-" + Hashing.sha256().hashString(user.getUsername() + new Date().getTime() + Math.random(), StandardCharsets.UTF_8).toString());
                }
                // random token in real use
                // user.setToken(user.getUsername() + "-" + Hashing.sha256().hashString(user.getUsername() + new Date().getTime() + Math.random(), StandardCharsets.UTF_8).toString());

                // hash password
                user.setPassword(Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString());

                user = (User) this.userService.addUser(user);
                if (user != null) {
                    return new Response(
                            HttpStatus.CREATED,
                            ContentType.JSON,
                            "{ message: \"User added successfully!\" }"
                    );
                } else {
                    return new Response(
                            HttpStatus.BAD_REQUEST,
                            ContentType.JSON,
                            "{ message: \"User already exists!\" }"
                    );
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }

    // POST /sessions
    public Response loginUser(Request request) {
        User user;
        try {
            // request.getBody() => "{ \"id\": 4, \"username\": \"Username\", ... }
            user = this.getObjectMapper().readValue(request.getBody(), User.class);

            if (user != null && user.getUsername() != null && user.getPassword() != null) {
                String username = user.getUsername();

                // hash password input
                String password = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();

                user = (User) this.userService.getUserByUsername(username);
                if (user == null) {
                    return new Response(
                            HttpStatus.NOT_FOUND,
                            ContentType.JSON,
                            "{ message: \"Username does not exist!\" }"
                    );
                } else if (user.authorized(password)) {
                    return new Response(
                            HttpStatus.OK,
                            ContentType.JSON,
                            "{ message: \"Login successful!\" }"
                    );
                } else {
                    return new Response(
                            HttpStatus.BAD_REQUEST,
                            ContentType.JSON,
                            "{ message: \"Login failed. Wrong username or password\" }"
                    );
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }


    // GET /users
    /*public Response getUsers() {
        List<User> userList = this.userService.getUsers();

        try {
            // request.getBody() => "{ \"id\": 4, \"username\": \"Username\", ... }
            String userListString = this.getObjectMapper().writeValueAsString(userList);

            return new Response(
                    HttpStatus.CREATED,
                    ContentType.JSON,
                    userListString
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }*/
}

