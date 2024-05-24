package com.helha.java.q2.cinephile.Models;

import java.io.Serializable;

public class Film implements Serializable {

    private String title;
    private String text;
    private String duration;
    private String trailer;
    private String picture;
    private String releasedate;
    private String dayAvailable;
    private String hourAvailable;
    private String start;
    private String end;
    private int RemainingticketsRoom1;
    private int id;
    private int RemainingticketsRoom2;
    private int RemainingticketsRoom3;

    // Constructeur
    public Film(String title, String text, String duration, String trailer, String picture, String releasedate,
                String jourDisponible, String heureDisponible, String debut, String fin, int RemainingticketsRoom1, int id,
                int RemainingticketsRoom2, int RemainingticketsRoom3) {
        this.title = title;
        this.text = text;
        this.duration = duration;
        this.trailer = trailer;
        this.picture = picture;
        this.releasedate = releasedate;
        this.dayAvailable = jourDisponible;
        this.hourAvailable = heureDisponible;
        this.start = debut;
        this.end = fin;
        this.RemainingticketsRoom1 = RemainingticketsRoom1;
        this.id = id;
        this.RemainingticketsRoom2 = RemainingticketsRoom2;
        this.RemainingticketsRoom3 = RemainingticketsRoom3;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDuration() {
        return duration;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getPicture() {
        return picture;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public String getDayAvailable() {
        return dayAvailable;
    }

    public String getHourAvailable() {
        return hourAvailable;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getRemainingticketsRoom1() {
        return RemainingticketsRoom1;
    }

    public int getId() {
        return id;
    }

    public int getRemainingticketsRoom2() {
        return RemainingticketsRoom2;
    }

    public int getRemainingticketsRoom3() {
        return RemainingticketsRoom3;
    }



    public void setRemainingticketsRoom1(int remainingticketsRoom1) {
        this.RemainingticketsRoom1 = remainingticketsRoom1;
    }

    public void setRemainingticketsRoom2(int remainingticketsRoom2) {
        this.RemainingticketsRoom2 = remainingticketsRoom2;
    }

    public void setRemainingticketsRoom3(int remainingticketsRoom3) {
        this.RemainingticketsRoom3 = remainingticketsRoom3;
    }
}
