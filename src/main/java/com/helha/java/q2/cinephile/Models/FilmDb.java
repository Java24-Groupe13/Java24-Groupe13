/**
 * Cette classe représente un accès à une base de données de films.
 * Elle fournit des méthodes pour récupérer des informations sur les films à partir de la base de données.
 */
package com.helha.java.q2.cinephile.Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDb {
    private Connection conn;

    public FilmDb(Connection conn) {
        this.conn = conn;
    }
    /**
     * Constructeur de la classe FilmDb. Initialise la connexion à la base de données.
     */
    public FilmDb() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources/data.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les films à partir de la base de données.
     *
     * @return Une liste contenant tous les films récupérés de la base de données.
     */
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Films");

            while (resultSet.next()) {
                String title = resultSet.getString("Titre");
                String synopsis = resultSet.getString("Synopsis");
                String duration = resultSet.getString("Duree");
                String trailer = resultSet.getString("BandeAnnonce");
                String picture = resultSet.getString("Image");
                String releaseDate = resultSet.getString("DateSortie");
                String dayAvailable = resultSet.getString("JourDisponible");
                String hourAvailable = resultSet.getString("HeureDisponible");
                String beginning = resultSet.getString("Debut");
                String end = resultSet.getString("Fin");
                int remainingRoom1Tickets = resultSet.getInt("TiquetsRestantsSalle1");
                int id = resultSet.getInt("id");
                int remainingRoom2Tickets = resultSet.getInt("TiquetsRestantsSalle2");
                int remainingRoom3Tickets = resultSet.getInt("TiquetsRestantsSalle3");

                films.add(new Film(title, synopsis, duration, trailer, picture, releaseDate, dayAvailable,
                        hourAvailable, beginning, end, remainingRoom1Tickets, id, remainingRoom2Tickets, remainingRoom3Tickets));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }


    /**
     * Récupère un film à partir de la base de données en fonction de son identifiant.
     *
     * @param id L'identifiant du film à récupérer.
     * @return Le film correspondant à l'identifiant spécifié.
     * @throws SQLException Si une erreur SQL survient lors de la récupération du film.
     */
    public Film getFilmById(int id) throws SQLException {
        Film film = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Films WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                film = extractFilmFromResultSet(resultSet);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return film;
    }

    /**
     * Extrait un film à partir du résultat d'une requête ResultSet.
     *
     * @param resultSet Le résultat de la requête ResultSet.
     * @return Le film extrait à partir du ResultSet.
     * @throws SQLException Si une erreur SQL survient lors de l'extraction du film.
     */
    private Film extractFilmFromResultSet(ResultSet resultSet) throws SQLException {
        Film film = new Film(
                resultSet.getString("Titre"),
                resultSet.getString("Synopsis"),
                resultSet.getString("Duree"),
                resultSet.getString("BandeAnnonce"),
                resultSet.getString("Image"),
                resultSet.getString("DateSortie"),
                resultSet.getString("JourDisponible"),
                resultSet.getString("HeureDisponible"),
                resultSet.getString("Debut"),
                resultSet.getString("Fin"),
                resultSet.getInt("TiquetsRestantsSalle1"),
                resultSet.getInt("id"),
                resultSet.getInt("TiquetsRestantsSalle2"),
                resultSet.getInt("TiquetsRestantsSalle3")
        );
        return film;
    }

    /**
     * Met à jour les informations d'un film dans la base de données.
     *
     * @param film Le film à mettre à jour.
     * @throws SQLException Si une erreur SQL survient lors de la mise à jour du film.
     */
    public void updateFilm(Film film) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            String query = "UPDATE Films SET Titre = ?, Synopsis = ?, Duree = ?, BandeAnnonce = ?, Image = ?, DateSortie = ?, JourDisponible = ?, HeureDisponible = ?, Debut = ?, Fin = ?, TiquetsRestantsSalle1 = ?, TiquetsRestantsSalle2 = ?, TiquetsRestantsSalle3 = ? WHERE id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setString(2, film.getText());
            preparedStatement.setString(3, film.getDuration());
            preparedStatement.setString(4, film.getTrailer());
            preparedStatement.setString(5, film.getPicture());
            preparedStatement.setString(6, film.getReleasedate());
            preparedStatement.setString(7, film.getDayAvailable());
            preparedStatement.setString(8, film.getHourAvailable());
            preparedStatement.setString(9, film.getStart());
            preparedStatement.setString(10, film.getEnd());
            preparedStatement.setInt(11, film.getRemainingticketsRoom1());
            preparedStatement.setInt(12, film.getRemainingticketsRoom2());
            preparedStatement.setInt(13, film.getRemainingticketsRoom3());
            preparedStatement.setInt(14, film.getId());

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

}
