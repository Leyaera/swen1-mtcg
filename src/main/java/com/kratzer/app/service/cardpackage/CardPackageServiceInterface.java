package com.kratzer.app.service.cardpackage;

import com.kratzer.app.model.cardpackage.CardPackageInterface;
import com.kratzer.app.model.user.UserInterface;

import java.util.List;

public interface CardPackageServiceInterface {
    CardPackageInterface getCardPackageById (int id);

    CardPackageInterface getRandomCardPackage();

    List<CardPackageInterface> getCardPackages();

    CardPackageInterface addCardPackage(CardPackageInterface cardPackage);

    boolean deleteCardPackageById(int id);

    boolean assignCardPackageToUser(CardPackageInterface cardPackage, UserInterface user);
}
