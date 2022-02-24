package com.kratzer.app.model.battle;

import com.kratzer.app.model.user.UserInterface;
import lombok.Getter;

import java.util.List;


public class Battle implements BattleInterface {
    @Getter
    int id;

    @Getter
    boolean gameOver;

    @Getter
    UserInterface playerA;

    @Getter
    UserInterface playerB;

    @Getter
    UserInterface winner;

    @Getter
    List<BattleRoundInterface> battleRounds;

    @Override
    public void startBattle() {

    }
}