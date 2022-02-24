package com.kratzer.app.controller;

import java.util.*;
import com.google.gson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kratzer.app.model.card.Card;
import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.cardpackage.CardPackage;
import com.kratzer.app.model.deck.DeckService;
import com.kratzer.app.model.user.User;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.server.Request;
import com.kratzer.server.Response;

public class DeckController extends Controller {
    CardService cardService;
    UserService userService;
    DeckService deckService;

    public DeckController (CardService cardService, UserService userService, DeckService deckService) {
        super();
        this.cardService = cardService;
        this.userService = userService;
        this.deckService = deckService;
    }

    public Response getDeckForUser(Request request) {
        try {
            String token = request.getAuthorization();
            if (token == null) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Unauthorized (No Token)"
                );
            }

            User user = (User) userService.getUserByToken(token);
            List<CardInterface> deck = deckService.getDeckForUser(user);

            if (deck != null) {
                String json = getObjectMapper().writeValueAsString(deck);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement j = JsonParser.parseString(json);
                String prettyJsonString = gson.toJson(j);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ " + prettyJsonString + " }"
                );
            } else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "{ message: \"Get Deck failed.\" }"
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

    public Response getDeckForUserPlain(Request request) {
        try {
            String token = request.getAuthorization();
            if (token == null) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Unauthorized (No Token)"
                );
            }

            User user = (User) userService.getUserByToken(token);
            List<CardInterface> deck = deckService.getDeckForUser(user);

            if (deck != null) {
                String json = getObjectMapper().writeValueAsString(deck);
                return new Response(
                        HttpStatus.OK,
                        ContentType.JSON,
                        "{ " + json + " }"
                );
            } else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "{ message: \"Get Deck (plain) failed.\" }"
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

    public Response addCardsWithIdsToDeck(Request request) {
        try {
            String token = request.getAuthorization();
            if (token == null) {
                return new Response(
                        HttpStatus.UNAUTHORIZED,
                        ContentType.JSON,
                        "Unauthorized (No Token)"
                );
            }

            User user = (User) userService.getUserByToken(token);
            String[] cardIds = this.getObjectMapper().readValue(request.getBody(), String[].class);
            if (deckService.addCardsWithIdsToDeck(cardIds, user)) {
                return new Response(
                        HttpStatus.CREATED,
                        ContentType.JSON,
                        "{ message: \"Deck created successfully\" }"
                );
            } else {
                return new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "{ message: \"Deck creation failed\" }"
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
}
