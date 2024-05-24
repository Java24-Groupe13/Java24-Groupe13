package com.helha.java.q2.cinephile.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe TicketsDb qui gère la base de données des tickets.
 */
public class TicketsDb {
    private Connection conn;

    /**
     * Constructeur de la classe TiquetDb. Initialise la connexion à la base de données.
     */
    public TicketsDb() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/data.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les tickets à partir de la base de données.
     *
     * @return Une liste contenant tous les tickets récupérés de la base de données.
     */
    public List<Tickets> getAllTickets() {
        List<Tickets> tickets = new ArrayList<>();
        String query = "SELECT * FROM Tiquets";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Tickets ticket = new Tickets();
                ticket.setFilmId(rs.getInt("FilmId"));
                ticket.setId(rs.getInt("Id"));
                ticket.setNumberOfTickets(rs.getInt("NombreDeTiquet"));
                ticket.setRoom(rs.getInt("Salle"));
                ticket.setHour(rs.getString("Heure"));
                ticket.setPrice(rs.getString("Prix"));
                ticket.setNumberOfChildrenTickets(rs.getInt("NombreDeTiquetEnfant"));
                ticket.setNumberOfSeniorTickets(rs.getInt("NombreDeTiquetSenior"));
                ticket.setNumberOfAdultTickets(rs.getInt("NombreDeTiquetAdulte"));
                ticket.setFilmName(rs.getString("NomFilm"));


                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    /**
     * Insère un nouveau ticket dans la base de données.
     *
     * @param tickets Le ticket à insérer.
     */
    public void insertTickets(Tickets tickets) {
        String query = "INSERT INTO Tiquets (FilmId, NombreDeTiquet, Salle, Heure, Prix, NombreDeTiquetEnfant, NombreDeTiquetSenior, NombreDeTiquetAdulte, NomFilm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tickets.getFilmId());
            pstmt.setInt(2, tickets.getNumberOfTickets());
            pstmt.setInt(3, tickets.getRoom());
            pstmt.setString(4, tickets.getHour());
            pstmt.setString(5, tickets.getPrice());
            pstmt.setInt(6, tickets.getNumberOfChildrenTickets());
            pstmt.setInt(7, tickets.getNumberOfSeniorTickets());
            pstmt.setInt(8, tickets.getNumberOfAdultTickets());
            pstmt.setString(9, tickets.getFilmName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
