package com.kratzer.app.controller;

import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.cardpackage.CardPackageService;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.server.Request;
import com.kratzer.server.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardPackageController extends Controller {
    CardPackageService cardPackageService;

    public CardPackageController(CardPackageService cardPackageService) {
        super();
        this.cardPackageService = cardPackageService;
    }
}
