package com.kratzer.app.model.card;

import lombok.Getter;

public class SpellCard extends Card {
    @Getter
    CardType cardType = CardType.MONSTER;

    public SpellCard(String id, String name, float damage, ElementType elementType) {
        super(id, name, damage, elementType);
        this.cardType = CardType.SPELL;
    }
}
