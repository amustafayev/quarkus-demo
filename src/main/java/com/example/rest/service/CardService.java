package com.example.rest.service;

import com.example.rest.dao.Card;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardService {

    public Uni<Card> getCardDetails(Long id) {
        return Card
                .find("id", id)
                .firstResult();
    }

    public Multi<Card> getAllCardDetails() {
        return Card.streamAll();
    }

    public Uni<Long> createCard(Card card) {
        return Panache.withTransaction(() -> card
                .persist()
                .onItem()
                .transform(c -> ((Card) c).getId()));
    }
}
