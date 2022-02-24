package com.kratzer.app.model.user;

public interface UserInterface {
    int getId();

    void setId(int id);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getToken();

    void setToken(String token);

    int getElo();

    int getCoins();

    void setCoins(int coins);

    String getStatus();

    boolean authorized(String password);
}
