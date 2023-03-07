package com.example.messagingdemo;

import com.example.messagingdemo.models.PaymentStatement;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.time.Duration;

@ApplicationScoped
public class InMemoryApp {


    @Outgoing("statement-out")
    public Multi<PaymentStatement> getStatements() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(1))
                .map(t -> fetchStatements());
    }


    private PaymentStatement fetchStatements() {
        return new PaymentStatement("source1", "dest1", BigInteger.valueOf(12));
    }


    @Incoming("statement-out")
    @Outgoing("in-memory-statement")
    @Broadcast
    public PaymentStatement processStatements(PaymentStatement paymentStatement) {

        //Do business logic here;
        //We separate fetching with business logic like that
        return paymentStatement;
    }
}
