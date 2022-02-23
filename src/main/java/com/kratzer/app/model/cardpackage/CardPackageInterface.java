package com.kratzer.app.model.cardpackage;

import com.kratzer.app.model.card.CardInterface;

import java.util.List;

public interface CardPackageInterface {
    int getId();

    List<CardInterface> getCards();

    int getCost();

    String getName();
}
