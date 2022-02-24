package com.kratzer.app.model.battle;

import com.kratzer.app.model.user.UserInterface;

import java.util.List;

public interface BattleInterface {
    int getId();

    boolean isGameOver();

    UserInterface getPlayerA();

    UserInterface getPlayerB();

    UserInterface getWinner();

    void startBattle();

    List<BattleRoundInterface> getBattleRounds();
}