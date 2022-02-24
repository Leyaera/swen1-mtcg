package com.kratzer.app.model.battle;

import com.kratzer.app.model.card.CardInterface;

public interface BattleRoundInterface {
    int getId();

    CardInterface getCardPlayerA();

    CardInterface getCardPlayerB();

    CardInterface getWinnerCard();
}
