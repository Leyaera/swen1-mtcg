package com.kratzer.app.service.cardpackage;

import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.cardpackage.CardPackage;
import com.kratzer.app.model.cardpackage.CardPackageInterface;
import com.kratzer.app.model.user.User;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.database.DatabaseService;
import com.kratzer.app.model.user.UserInterface;
import com.kratzer.app.service.user.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardPackageService implements CardPackageServiceInterface{
    private static CardPackageService cardPackageService;
    private UserService userService;
    private CardService cardService;

    private CardPackageService() {
        userService = UserService.getUserService();
        cardService = CardService.getCardService();
    }

    public static CardPackageService getCardPackageService() {
        if (CardPackageService.cardPackageService == null) {
            CardPackageService.cardPackageService = new CardPackageService();
        }
        return CardPackageService.cardPackageService;
    }

    @Override
    public CardPackageInterface getCardPackageById (int id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, packagename, cost FROM packages WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CardPackage cardPackage = new CardPackage(
                        resultSet.getInt(1),        // id
                        resultSet.getString(2),     // packagename
                        resultSet.getInt(3)         // cost
                );
                resultSet.close();
                preparedStatement.close();
                conn.close();

                return cardPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardPackageInterface getRandomCardPackage () {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, packagename, cost FROM packages ORER BY RANDOM() LIMIT 1;");

            if (resultSet.next()) {
                CardPackage cardPackage = new CardPackage(
                        resultSet.getInt(1),        // id
                        resultSet.getString(2),     // packagename
                        resultSet.getInt(3)         // cost
                );
                resultSet.close();
                statement.close();
                conn.close();
                return cardPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardPackageInterface addCardPackage(CardPackageInterface cardPackage) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO packages(packagename, cost) VALUES (?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cardPackage.getName());
            preparedStatement.setInt(2, cardPackage.getCost());

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return cardPackage;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardPackageInterface> getCardPackages() {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, packagename, cost FROM packages;");

            List<CardPackageInterface> packages = new ArrayList<>();
            while(resultSet.next()) {
                packages.add(new CardPackage(
                        resultSet.getInt(1),        // id
                        resultSet.getString(2),     // packagename
                        resultSet.getInt(3)));        // cost
            }

            statement.close();
            resultSet.close();
            conn.close();
            return packages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteCardPackageById(int id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM packages WHERE id = ?;");
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean assignCardPackageToUser(CardPackageInterface cardPackage, UserInterface user) {
        if (user.getCoins() < cardPackage.getCost()) return false;

        user.setCoins(user.getCoins() - cardPackage.getCost());
        userService.updateUser(user.getId(), user);

        for (CardInterface card : cardService.getCardsForCardPackage(cardPackage)) {
            cardService.assignCardToUser(card, user);
        }
        this.deleteCardPackageById(cardPackage.getId());

        return true;
    }
}
