package com.kratzer.app.model.card;

public interface CardInterface {
    String getId();

    void setId(String id);

    String getName();

    float getDamage();

    CardType getCardType();

    ElementType getElementType();

    float damageAgainst(CardInterface opponentCard);

    boolean instantWinsAgainst(CardInterface opponentCard);

    boolean isImmune();
}
