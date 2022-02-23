package com.kratzer.app.controller;

import com.google.common.hash.Hashing;
import com.kratzer.app.model.card.Card;
import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.cardpackage.CardPackage;
import com.kratzer.app.model.cardpackage.CardPackageInterface;
import com.kratzer.app.model.user.User;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.cardpackage.CardPackageService;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.server.Request;
import com.kratzer.server.Response;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CardPackageController extends Controller {
    CardPackageService cardPackageService;
    CardService cardService;
    UserService userService;

    public CardPackageController(CardPackageService cardPackageService, CardService cardService, UserService userService) {
        super();
        this.cardPackageService = cardPackageService;
        this.cardService = cardService;
        this.userService = userService;
    }

    public Response createPackage(Request request) {
        try {
            String token = request.getAuthorization();
            if (token != null && !token.equals("admin-mtcgToken")) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Unauthorized"
                );
            }
            // request.getBody() => "{ \"id\": 4, \"username\": \"Username\", ... }
            Card[] cards = this.getObjectMapper().readValue(request.getBody(), Card[].class);
            CardPackage cardPackage = new CardPackage();
            cardPackage.setCards(Arrays.asList(cards));

            cardPackage = (CardPackage) cardPackageService.addCardPackage(cardPackage);

            for (CardInterface card : cards) {
                cardService.addCard(card);
                cardService.assignCardToCardPackage(card, cardPackage);
            }

            if (cardPackage!= null) {
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ message: \"Package created successfully!\" }"
                );
            } else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "{ message: \"Create Package failed\" }"
                );
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ContentType.JSON,
                "{ \"message\" : \"Internal Server Error\" }"
        );
    }

    public Response acquirePackage(Request request) {

        String token = request.getAuthorization();
        User user = (User) userService.getUserByToken(token);
        if (user == null) {
            return new Response(
                    HttpStatus.UNAUTHORIZED,
                    ContentType.JSON,
                    "Unauthorized"
            );
        }

        CardPackageInterface cardPackage = cardPackageService.getRandomCardPackage();
        cardPackageService.assignCardPackageToUser(cardPackage, user);
        cardPackageService.deleteCardPackageById(cardPackage.getId());

        if (cardPackage!= null) {
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    "{ message: \"Package successfully assigned to User\" }"
            );
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "{ message: \"Assigning package to user failed\" }"
            );
        }
    }
}
