package com.kratzer;

import com.kratzer.app.App;
import com.kratzer.server.Server;

import java.io.IOException;

public class Main {
    public static void main (String[] args) {
        App app = new App();
        Server server = new Server(10001, app);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
