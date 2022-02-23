package com.kratzer.app.service.card;

import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.cardpackage.CardPackageInterface;
import com.kratzer.app.model.user.UserInterface;

import java.util.List;

public interface CardServiceInterface {
    CardInterface getCardById(String id);

    List<CardInterface> getCards();

    List<CardInterface> getCardsForUser(UserInterface user);

    List<CardInterface> getCardsForCardPackage(CardPackageInterface cardPackage);

    CardInterface addCard(CardInterface card);

    CardInterface assignCardToCardPackage(CardInterface card, CardPackageInterface cardPackage);

    CardInterface assignCardToUser(CardInterface card, UserInterface user);

    boolean deleteCardById(String id);
}
