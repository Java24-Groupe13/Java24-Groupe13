package com.helha.java.q2.cinephile.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketsDb {
    private Connection conn;

    /**
     * Constructeur de la classe TiquetDAO. Initialise la connexion à la base de données.
     */
    public TicketsDb() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/data.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les tiquets à partir de la base de données.
     *
     * @return Une liste contenant tous les tiquets récupérés de la base de données.
     */
    public List<Tickets> getAllTiquets() {
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

    public void insertTiquet(Tickets tiquet) {
        String query = "INSERT INTO Tiquets (FilmId, NombreDeTiquet, Salle, Heure, Prix, NombreDeTiquetEnfant, NombreDeTiquetSenior, NombreDeTiquetAdulte, NomFilm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tiquet.getFilmId());
            pstmt.setInt(2, tiquet.getNumberOfTickets());
            pstmt.setInt(3, tiquet.getRoom());
            pstmt.setString(4, tiquet.getHour());
            pstmt.setString(5, tiquet.getPrice());
            pstmt.setInt(6, tiquet.getNumberOfChildrenTickets());
            pstmt.setInt(7, tiquet.getNumberOfSeniorTickets());
            pstmt.setInt(8, tiquet.getNumberOfAdultTickets());
            pstmt.setString(9, tiquet.getFilmName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
