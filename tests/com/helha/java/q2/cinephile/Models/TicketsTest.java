package com.helha.java.q2.cinephile.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicketsTest {

    private Tickets tickets;

    @BeforeEach
    public void setUp() {
        tickets = new Tickets();
    }

    @Test
    public void testFilmId() {
        tickets.setFilmId(1);
        assertEquals(1, tickets.getFilmId());
    }

    @Test
    public void testFilmName() {
        tickets.setFilmName("Inception");
        assertEquals("Inception", tickets.getFilmName());
    }

    @Test
    public void testId() {
        tickets.setId(1);
        assertEquals(1, tickets.getId());
    }

    @Test
    public void testNumberOfTickets() {
        tickets.setNumberOfTickets(5);
        assertEquals(5, tickets.getNumberOfTickets());
    }

    @Test
    public void testRoom() {
        tickets.setRoom(2);
        assertEquals(2, tickets.getRoom());
    }

    @Test
    public void testHour() {
        tickets.setHour("20:00");
        assertEquals("20:00", tickets.getHour());
    }

    @Test
    public void testPrice() {
        tickets.setPrice("10.00");
        assertEquals("10.00", tickets.getPrice());
    }

    @Test
    public void testNumberOfChildrenTickets() {
        tickets.setNumberOfChildrenTickets(2);
        assertEquals(2, tickets.getNumberOfChildrenTickets());
    }

    @Test
    public void testNumberOfSeniorTickets() {
        tickets.setNumberOfSeniorTickets(3);
        assertEquals(3, tickets.getNumberOfSeniorTickets());
    }

    @Test
    public void testNumberOfAdultTickets() {
        tickets.setNumberOfAdultTickets(4);
        assertEquals(4, tickets.getNumberOfAdultTickets());
    }
}
