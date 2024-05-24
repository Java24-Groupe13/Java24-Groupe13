package com.helha.java.q2.cinephile.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class TicketsDbTest {

    private TicketsDb ticketsDb;

    @BeforeEach
    void setUp() {
        ticketsDb = new TicketsDb();
    }

    @Test
    void testGetAllTickets() {
        List<Tickets> tickets = ticketsDb.getAllTickets();

        // Vérifiez que la liste des tickets n'est pas vide
        assertFalse(tickets.isEmpty(), "The list of tickets is empty");
    }
}