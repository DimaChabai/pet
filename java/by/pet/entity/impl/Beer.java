package by.pet.entity.impl;

import by.pet.entity.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class Beer extends Entity {
    @Id
    @GeneratedValue
    private long id;
    private String beerType;

    public Beer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }
}
