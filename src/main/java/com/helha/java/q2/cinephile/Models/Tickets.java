package com.helha.java.q2.cinephile.Models;

import java.io.Serializable;

public class Tickets implements Serializable {
    private int filmId;
    private int id;
    private int numberOfTickets;
    private int room;
    private String hour;
    private String price;
    private int numberOfChildrenTickets;
    private int numberOfSeniorTickets;
    private int numberOfAdultTickets;
    private String filmName;

    // Getters and setters
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {return filmName;}

    public void setFilmName(String NomFilm) {this.filmName = NomFilm;}

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumberOfChildrenTickets() {
        return numberOfChildrenTickets;
    }

    public void setNumberOfChildrenTickets(int numberOfChildrenTickets) {
        this.numberOfChildrenTickets = numberOfChildrenTickets;
    }

    public int getNumberOfSeniorTickets() {
        return numberOfSeniorTickets;
    }

    public void setNumberOfSeniorTickets(int numberOfSeniorTickets) {
        this.numberOfSeniorTickets = numberOfSeniorTickets;
    }

    public int getNumberOfAdultTickets() {
        return numberOfAdultTickets;
    }

    public void setNumberOfAdultTickets(int numberOfAdultTickets) {
        this.numberOfAdultTickets = numberOfAdultTickets;
    }
}
