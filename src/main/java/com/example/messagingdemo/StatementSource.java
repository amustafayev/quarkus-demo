package com.example.messagingdemo;

import com.example.messagingdemo.models.PaymentStatement;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/statements")
public class StatementSource {

    @Channel("in-memory-statement")
    Publisher<PaymentStatement> statements;


    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<PaymentStatement> getInMemoryStatements() {
        return statements;
    }
}
