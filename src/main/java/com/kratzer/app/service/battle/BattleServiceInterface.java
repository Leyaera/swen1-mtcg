package com.kratzer.app.service.battle;

import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.user.UserInterface;
import com.kratzer.app.model.battle.BattleInterface;

public interface BattleServiceInterface {

    boolean battle(BattleInterface battle);
}