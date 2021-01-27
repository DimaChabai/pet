package by.pet.entity.impl;

import by.pet.entity.Entity;
import by.pet.entity.PlaceType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class Place extends Entity {
    @Id
    @GeneratedValue
    @Column(name = "idPlace")
    private long idPlace;
    private int seats;
    private PlaceType type;

    public Place() {
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(long idPlace) {
        this.idPlace = idPlace;
    }

    @Override
    public String toString() {
        return "Территория №" + idPlace + " Места = " + seats +
                ", тип = " + type.toString();
    }
}
