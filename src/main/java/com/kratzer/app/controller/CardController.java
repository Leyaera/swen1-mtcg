package com.kratzer.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.kratzer.app.model.card.CardInterface;
import com.kratzer.app.model.user.User;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.server.Request;
import com.kratzer.server.Response;

import java.util.*;
import com.google.gson.*;

public class CardController extends Controller {
    CardService cardService;
    UserService userService;

    public CardController (CardService cardService, UserService userService) {
        super();
        this.cardService = cardService;
        this.userService = userService;
    }

    public Response getCardsForUser(Request request) {
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
            List<CardInterface> cards = cardService.getCardsForUser(user);

            if (cards != null) {
                String json = getObjectMapper().writeValueAsString(cards);
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
                        "{ message: \"Get Cards failed.\" }"
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
