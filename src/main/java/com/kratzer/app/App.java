package com.kratzer.app;


import com.kratzer.app.controller.CardController;
import com.kratzer.app.controller.CardPackageController;
import com.kratzer.app.controller.DeckController;
import com.kratzer.app.controller.UserController;
import com.kratzer.app.model.deck.DeckService;
import com.kratzer.app.service.card.CardService;
import com.kratzer.app.service.cardpackage.CardPackageService;
import com.kratzer.app.service.user.UserService;
import com.kratzer.http.ContentType;
import com.kratzer.http.HttpStatus;
import com.kratzer.http.Method;
import com.kratzer.server.Request;
import com.kratzer.server.Response;
import com.kratzer.server.ServerApp;

public class App implements ServerApp {
    private final UserController userController;
    private final CardPackageController cardPackageController;
    private final CardController cardController;
    private final DeckController deckController;

    public App() {
        this.userController = new UserController(new UserService());
        this.cardPackageController = new CardPackageController(CardPackageService.getCardPackageService(), CardService.getCardService(), UserService.getUserService());
        this.cardController = new CardController(CardService.getCardService(), UserService.getUserService());
        this.deckController = new DeckController(CardService.getCardService(), UserService.getUserService(), DeckService.getDeckService());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getPathname().equals("/users") && request.getMethod() == Method.POST) {
            return this.userController.addUser(request);
        }

        if (request.getPathname().equals("/sessions") && request.getMethod() == Method.POST) {
            return this.userController.loginUser(request);
        }

        if (request.getPathname().equals("/packages") && request.getMethod() == Method.POST) {
            return this.cardPackageController.createPackage(request);
        }

        if (request.getPathname().equals("/transactions/packages") && request.getMethod() == Method.POST) {
            return this.cardPackageController.acquirePackage(request);
        }

        if (request.getPathname().equals("/cards") && request.getMethod() == Method.GET) {
            return this.cardController.getCardsForUser(request);
        }

        if (request.getPathname().equals("/deck") && request.getMethod() == Method.GET) {
            return this.deckController.getDeckForUser(request);
        }

        if (request.getPathname().equals("/deck") && request.getMethod() == Method.PUT) {
            return this.deckController.addCardsWithIdsToDeck(request);
        }

        if (request.getPathname().equals("/deck?format=plain") && request.getMethod() == Method.GET) {
            return this.deckController.getDeckForUserPlain(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
