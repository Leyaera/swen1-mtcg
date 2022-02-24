package com.kratzer.app.model.deck;

import com.kratzer.app.model.card.*;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.database.DatabaseService;
import com.kratzer.app.model.user.UserInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeckService implements DeckServiceInterface {
    private static DeckService getDeckService;

    private CardService cardService;

    private DeckService() {
        cardService = CardService.getCardService();
    }

    public static DeckService getDeckService() {
        if (DeckService.getDeckService == null) {
            DeckService.getDeckService = new DeckService();
        }
        return DeckService.getDeckService;
    }

    @Override
    public List<CardInterface> getDeckForUser(UserInterface user) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT id, cardname, damage, cardtype, elementtype FROM cards WHERE userid = ? AND indeck;");
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
                cards.add(card);
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
    public boolean addCardsWithIdsToDeck(String[] cardIds, UserInterface user) {
        List<CardInterface> userCards = cardService.getCardsForUser(user);
        List<CardInterface> newDeck = new ArrayList<>();

        // Only 4 cards in deck allowed
        if (cardIds.length == 4) {
            for (String cardId : cardIds) {
                // Check if the card belongs to the user
                List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId().equals(cardId)).collect(Collectors.toList());
                if (filteredCards.size() == 1) {
                    CardInterface card = filteredCards.get(0);
                    newDeck.add(card);
                }
            }
            // Cards belong to User
            if (newDeck.size() == 4) {
                // Clear deck
                this.clearDeck(user);

                // Attach new cards to the deck
                for (CardInterface card : newDeck) {
                    this.addCardToDeck(card, user);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCardToDeck(CardInterface card, UserInterface user) {
        if (card.isInDeck()) {
            return false;
        }

        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(id) FROM  cards WHERE userid = ? AND indeck;");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);

                // Deck maximum = 5, count from 0!
                if (count >= 4) {
                    return false;
                }

                preparedStatement = conn.prepareStatement("UPDATE cards SET indeck = TRUE WHERE id = ?;");
                preparedStatement.setString(1, card.getId());

                int affectedRows = preparedStatement.executeUpdate();

                resultSet.close();
                preparedStatement.close();
                conn.close();

                return affectedRows != 0;
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean clearDeck(UserInterface user) {
        try {
            Connection conn = DatabaseService.getDatabaseService().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET indeck = FALSE WHERE userid = ?;");
            ps.setInt(1, user.getId());

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}