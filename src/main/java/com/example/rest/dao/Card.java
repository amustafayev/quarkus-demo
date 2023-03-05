package com.example.rest.dao;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.*;

@Entity
@Table(schema = "cards")
public class Card extends PanacheEntity {
    @Column
    private String maskedPan;

    public Long getId() {
        return id;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public String getHolderName() {
        return holderName;
    }

    @Column
    private String holderName;
}
