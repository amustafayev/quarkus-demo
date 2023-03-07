package com.example.messagingdemo.models;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Column;
import java.math.BigInteger;

public class PaymentStatement extends PanacheEntity {
    @Column
    private String sourceAccount;

    @Column
    private String destinationAccount;

    @Column
    private BigInteger amount;

    public PaymentStatement(String sourceAccount,
                            String destinationAccount,
                            BigInteger amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }
}
