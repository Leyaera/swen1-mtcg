package com.kratzer.app.model.card;

public interface CardInterface {
    int getId();
    String getName();
    float getDamage();
    CardType getCardType();
    ElementType getElementType();

    float damageAgainst(CardInterface opponentCard);
    boolean instantWinsAgainst(CardInterface opponentCard);
}
