package com.kratzer.app;


import com.kratzer.app.controller.UserController;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.http.Method;
import com.kratzer.server.Request;
import com.kratzer.server.Response;
import com.kratzer.server.ServerApp;

public class App implements ServerApp {
    private final UserController userController;

    public App() {
        this.userController = new UserController(new UserService());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getPathname().equals("/users") && request.getMethod() == Method.POST) {
            return this.userController.addUser(request);
        } else if (request.getPathname().equals("/sessions") && request.getMethod() == Method.POST) {
            return this.userController.loginUser(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
