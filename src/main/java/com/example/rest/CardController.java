package com.example.rest;

import com.example.rest.dao.Card;
import com.example.rest.service.CardService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import org.hibernate.annotations.Polymorphism;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/cards")
public class CardController {
    @Inject
    Vertx vertx;

    @Inject
    RecommendationService recommendationService;

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GET
    @Path("/{id}")
    public Uni<Card> getCardDetails(@PathParam("id") Long id) {
        return cardService.getCardDetails(id);
    }

    @GET
    public Multi<Card> getAllCardDetails() {
        return cardService.getAllCardDetails();
    }

    @POST
    public Uni<String> createCard(Card card) {
        return cardService.createCard(card)
                .onItem().transform(id -> "new card created with id" + id)
                .onFailure().recoverWithItem(failure -> "Failure: " + failure.getMessage());
    }

    @GET
    @Path("/404")
    public Uni<Response> get404() {
        return vertx.fileSystem()
                .readFile("oups.txt")
                .onItem()
                .transform(buffer -> buffer.toString("UTF-8"))
                .onItem()
                .transform(content -> Response.ok(content).build())
                .onFailure()
                .recoverWithItem(Response.status(Response.Status.NOT_FOUND).build());
    }

    //Server sent events
    @GET
    @Path("/recommendations")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> getRecommendedProductName() {
        return recommendationService.getProductRecommendations();
    }
}

@ApplicationScoped
class RecommendationService {

    public Multi<String> getProductRecommendations() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(2))
                .onItem()
                .transform(x -> "product_" + x);
    }
}