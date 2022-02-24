package com.kratzer.app.service.battle;

import com.kratzer.app.model.battle.BattleInterface;
import com.kratzer.app.model.card.Card;
import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.deck.DeckService;
import com.kratzer.app.model.user.User;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.user.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleService implements BattleServiceInterface {
    private static BattleService battleService;
    private final UserService userService;
    private final DeckService deckService;
    private final CardService cardService;

    private BattleService() {
        userService = UserService.getUserService();
        deckService = DeckService.getDeckService();
        cardService = CardService.getCardService();
    }

    public static BattleService getBattleService() {
        if (BattleService.getBattleService() == null) {
            BattleService.battleService = new BattleService();
        }
        return BattleService.battleService;
    }

    @Override
    public boolean battle(BattleInterface battle) {
        User playerA = (User) battle.getPlayerA();
        User playerB = (User) battle.getPlayerB();
        User winner = null;

        ArrayList<CardInterface> deckA = (ArrayList<CardInterface>) deckService.getDeckForUser(playerA);
        ArrayList<CardInterface> deckB = (ArrayList<CardInterface>) deckService.getDeckForUser(playerB);

        // Check if decks are complete
        if (deckA.size() != 4 || deckB.size() != 4) {
            return false;
        }

        CardInterface cardA;
        CardInterface cardB;
        CardInterface winnerCard;

        System.out.println("MTCG - Battle start!");
        System.out.println(playerA.getUsername() + " fights against " + playerB.getUsername());

        for (int i = 0; i < 100; i++) {
            // Check for winners
            if (deckA.size() == 0) {
                // No Cards in A Deck, B wins
                winner = playerB;
                // Update elo in DB
                System.out.println("Player B won.");
                break;
            } else if (deckB.size() == 0) {
                // Deck B is empty
                System.out.println("Player B won.");
                break;
            }

            cardA = deckA.get(new Random().nextInt(deckA.size()));
            cardB = deckB.get(new Random().nextInt(deckB.size()));
            winnerCard = null;

            // new Battle Round
        }

        // Transfer cards from current decks to users
        for (CardInterface card : deckA) {
            //cardService.addCardToUser(card, playerA);
        }
        for (CardInterface card : deckB) {
            //cardService.addCardToUser(card, playerB);
        }

        // Clear deck
        deckService.clearDeck(playerA);
        deckService.clearDeck(playerB);

        return false;
    }

}
