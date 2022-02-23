package com.kratzer.app.model.cardpackage;

import com.kratzer.app.model.card.CardInterface;

import com.kratzer.app.model.card.CardType;
import com.kratzer.app.model.card.ElementType;
import lombok.Getter;
import java.util.List;

public class CardPackage implements CardPackageInterface {
    @Getter
    int id;

    @Getter
    transient List<CardInterface> cards;

    @Getter
    int cost = 5;

    @Getter
    String name = "standard package";

    public CardPackage () {

    }

    public CardPackage (int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }


}
