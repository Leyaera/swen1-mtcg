package com.kratzer.app.model.card;

import lombok.Getter;

public class MonsterCard extends Card {
    @Getter
    CardType cardType = CardType.MONSTER;

    public MonsterCard(int id, String name, float damage, ElementType elementType) {
        super(id, name, damage, elementType);
        this.cardType = CardType.MONSTER;
    }
}
