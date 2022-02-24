package com.kratzer.app.model.deck;

import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.user.UserInterface;

import java.util.List;

public interface DeckServiceInterface {
    List<CardInterface> getDeckForUser(UserInterface user);

    boolean addCardsWithIdsToDeck(String[] cardIds, UserInterface user);

    boolean addCardToDeck(CardInterface card, UserInterface user);

    boolean clearDeck(UserInterface user);
}