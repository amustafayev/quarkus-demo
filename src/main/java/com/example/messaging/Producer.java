package com.example.messaging;

import com.example.messaging.model.Order;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Producer {

    @Channel("my-channel")
    MutinyEmitter<Order> orderEmitter;


    //Emit messages
    public Uni<Void> send(Order p) {
        return orderEmitter.send(p); //   if success? returns null; else failure reason
    }

    //Emit order with acknowledge
    public void sendMessage(Order o) {
        orderEmitter.sendMessageAndForget(
                Message.of(o,
                        () -> {
                            // Acknowledgment callback
                            return CompletableFuture.completedFuture(null);
                        },
                        failure -> {
                            // Negative-acknowledgment callback
                            return CompletableFuture.completedFuture(null);
                        })
        );
    }

    /**
     * Methods annotated with @Outgoing can’t be called from your code,
     * but you can call from it
     */
    @Outgoing("my-stream")
    Multi<Order> orderAStreamOfPersons() {
        return getTodaysOrders();
    }

    private Multi<Order> getTodaysOrders() {
        return Multi.createFrom().items(
                new Order("Order1"),
                new Order("Order2"),
                new Order("Order3")
        );
    }


    @Channel("my-channel-upcoming")
    Multi<Message<Order>> streamOfOrders;

    // ...
    void init() {
        streamOfOrders.subscribe().with(
                message -> {
                    Order person = message.getPayload();
                    try {
                        // do something
                        // acknowledge
                        message.ack();
                    } catch (Exception e) {
                        message.nack(e);
                    }
                },
                failure -> { /* ... */ });
    }

    @Incoming("my-stream")
    CompletionStage<Void> consume(Message<Order> order) {
        // ...
        return order.ack();
    }


    @Incoming("from")
    @Outgoing("to")
    Multi<Order> processStream(Multi<String> inputStream) {
        return inputStream .onItem().transform(Order::new);
    }


}
