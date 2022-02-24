package com.kratzer.app.model.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

public class User implements UserInterface{
    @Getter
    @Setter
    @JsonAlias({"id"})
    int id = 0;

    @Getter
    @Setter
    @JsonAlias({"Username"})
    String username;

    @Getter
    @Setter
    @JsonAlias({"Password"})
    String password;

    @Getter
    @Setter
    @JsonAlias({"token"})
    String token;

    @Getter
    @Setter
    @JsonAlias({"elo"})
    int elo = 100;


    @Getter
    @Setter
    @JsonAlias({"coins"})
    int coins = 20;

    @Getter
    @Setter
    @JsonAlias({"status"})
    String status;

    public User () {};

    public User (int id, String username, String password, String token, int elo, int coins, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.elo = elo;
        this.coins = coins;
        this.status = status;
    }

    @Override
    public boolean authorized(String password) {
        return password.equals(getPassword());
    }
}
