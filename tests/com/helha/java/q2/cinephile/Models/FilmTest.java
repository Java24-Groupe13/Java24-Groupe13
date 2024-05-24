package com.helha.java.q2.cinephile.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {

    private Film film;

    @BeforeEach
    public void setUp() {
        film = new Film(
                "Inception", "A mind-bending thriller", "2h28m", "trailer_url", "picture_url",
                "2010-07-16", "Friday", "20:00", "2023-01-01", "2023-01-31",
                100, 1, 150, 200
        );
    }

    @Test
    public void testGetTitle() {
        assertEquals("Inception", film.getTitle());
    }

    @Test
    public void testGetText() {
        assertEquals("A mind-bending thriller", film.getText());
    }

    @Test
    public void testGetDuration() {
        assertEquals("2h28m", film.getDuration());
    }

    @Test
    public void testGetTrailer() {
        assertEquals("trailer_url", film.getTrailer());
    }

    @Test
    public void testGetPicture() {
        assertEquals("picture_url", film.getPicture());
    }

    @Test
    public void testGetReleasedate() {
        assertEquals("2010-07-16", film.getReleasedate());
    }

    @Test
    public void testGetDayAvailable() {
        assertEquals("Friday", film.getDayAvailable());
    }

    @Test
    public void testGetHourAvailable() {
        assertEquals("20:00", film.getHourAvailable());
    }

    @Test
    public void testGetStart() {
        assertEquals("2023-01-01", film.getStart());
    }

    @Test
    public void testGetEnd() {
        assertEquals("2023-01-31", film.getEnd());
    }

    @Test
    public void testGetRemainingticketsRoom1() {
        assertEquals(100, film.getRemainingticketsRoom1());
    }

    @Test
    public void testGetId() {
        assertEquals(1, film.getId());
    }

    @Test
    public void testGetRemainingticketsRoom2() {
        assertEquals(150, film.getRemainingticketsRoom2());
    }

    @Test
    public void testGetRemainingticketsRoom3() {
        assertEquals(200, film.getRemainingticketsRoom3());
    }

    @Test
    public void testSetRemainingticketsRoom1() {
        film.setRemainingticketsRoom1(90);
        assertEquals(90, film.getRemainingticketsRoom1());
    }

    @Test
    public void testSetRemainingticketsRoom2() {
        film.setRemainingticketsRoom2(140);
        assertEquals(140, film.getRemainingticketsRoom2());
    }

    @Test
    public void testSetRemainingticketsRoom3() {
        film.setRemainingticketsRoom3(180);
        assertEquals(180, film.getRemainingticketsRoom3());
    }
}
