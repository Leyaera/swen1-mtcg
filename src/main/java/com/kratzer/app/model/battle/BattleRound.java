package com.kratzer.app.model.battle;

import com.kratzer.app.model.card.CardInterface;
import lombok.Getter;

public class BattleRound implements BattleRoundInterface {
    @Getter
    int id;

    @Getter
    CardInterface cardPlayerA;

    @Getter
    CardInterface cardPlayerB;

    @Getter
    CardInterface winnerCard;
}
