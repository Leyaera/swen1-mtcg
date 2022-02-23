package com.kratzer.app.service.card;



import com.kratzer.app.model.card.*;
import com.kratzer.app.model.cardpackage.CardPackageInterface;
import com.kratzer.app.model.user.UserInterface;
import com.kratzer.app.service.database.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CardService implements CardServiceInterface{
    private static CardService cardService;

    private CardService() {

    }

    public static CardService getCardService() {
        if (CardService.cardService == null) {
            CardService.cardService = new CardService();
        }
        return CardService.cardService;
    }

    @Override
    public CardInterface getCardById(String id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, cardname, damage, cardtype, elementtype, userid, packageid FROM cards WHERE id = ?;");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CardInterface card;
                if (CardType.valueOf(resultSet.getString(4)).equals(CardType.MONSTER)) {
                    card = new MonsterCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                } else {// (CardType.valueOf(resultSet.getString(4)).equals(CardType.SPELL))
                    card = new SpellCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                }
                resultSet.close();
                preparedStatement.close();
                conn.close();
                return card;
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardInterface> getCards() {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, damage, cardtype, elementtype FROM cards;");

            List<CardInterface> cards = new ArrayList<>();
            while (resultSet.next()) {
                CardInterface card;
                if (CardType.valueOf(resultSet.getString(4)).equals(CardType.MONSTER)) {
                    card = new MonsterCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                } else {// (CardType.valueOf(resultSet.getString(4)).equals(CardType.SPELL))
                    card = new SpellCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardInterface addCard(CardInterface card) {
        try {
            if (this.getCardById(card.getId()) == null) {
                return null;
            }
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO cards(id, cardname, damage, cardtype, elementtype, userid, packageid) VALUES(?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, card.getId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setFloat(3, card.getDamage());
            preparedStatement.setString(4, card.getElementType().toString());
            preparedStatement.setString(5, card.getCardType().toString());
            preparedStatement.setNull(6, java.sql.Types.NULL);
            preparedStatement.setNull(7, java.sql.Types.NULL);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            preparedStatement.close();
            conn.close();
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardInterface> getCardsForCardPackage(CardPackageInterface cardPackage) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, cardname, damage, cardtype, elementtype FROM cards WHERE packageid = ?;");
            preparedStatement.setInt(1, cardPackage.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CardInterface> cards = new ArrayList<>();
            while (resultSet.next()) {
                CardInterface card;
                if (CardType.valueOf(resultSet.getString(4)).equals(CardType.MONSTER)) {
                    card = new MonsterCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                } else {// (CardType.valueOf(resultSet.getString(4)).equals(CardType.SPELL))
                    card = new SpellCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                }
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardInterface> getCardsForUser(UserInterface user) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, name, damage, cardtype, elementtype FROM cards WHERE userid = ?;");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CardInterface> cards = new ArrayList<>();
            while (resultSet.next()) {
                CardInterface card;
                if (CardType.valueOf(resultSet.getString(4)).equals(CardType.MONSTER)) {
                    card = new MonsterCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                } else {// (CardType.valueOf(resultSet.getString(4)).equals(CardType.SPELL))
                    card = new SpellCard(
                            resultSet.getString(1),                         // id
                            resultSet.getString(2),                         // cardname
                            resultSet.getFloat(3),                          // damage
                            ElementType.valueOf(resultSet.getString(5)));   // elementtype
                }
            }

            resultSet.close();
            preparedStatement.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardInterface assignCardToCardPackage(CardInterface card, CardPackageInterface cardPackage) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE cards SET packageid = ? WHERE id = ?;");
            preparedStatement.setInt(1, cardPackage.getId());
            preparedStatement.setString(2, card.getId());

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return this.getCardById(card.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CardInterface assignCardToUser(CardInterface card, UserInterface user) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE cards SET packageid = NULL, userid = ? WHERE id = ?;");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, card.getId());

            int affectedRows = preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

            if (affectedRows == 0) {
                return null;
            }
            return this.getCardById(card.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteCardById(String id) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM cards WHERE id = ?;");
            preparedStatement.setString(1, id);

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
}
