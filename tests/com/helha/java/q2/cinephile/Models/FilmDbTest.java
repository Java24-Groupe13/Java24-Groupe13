package com.helha.java.q2.cinephile.Models;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FilmDbTest {

    private FilmDb filmDb;
    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        // Setup in-memory database for each test
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");
        filmDb = new FilmDb(conn);  // Assuming you have a constructor that accepts a Connection

        // Create table and insert test data
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE Films (" +
                    "id INTEGER PRIMARY KEY, " +
                    "Titre TEXT, " +
                    "Synopsis TEXT, " +
                    "Duree TEXT, " +
                    "BandeAnnonce TEXT, " +
                    "Image TEXT, " +
                    "DateSortie TEXT, " +
                    "JourDisponible TEXT, " +
                    "HeureDisponible TEXT, " +
                    "Debut TEXT, " +
                    "Fin TEXT, " +
                    "TiquetsRestantsSalle1 INTEGER, " +
                    "TiquetsRestantsSalle2 INTEGER, " +
                    "TiquetsRestantsSalle3 INTEGER)");

            stmt.execute("INSERT INTO Films (id, Titre, Synopsis, Duree, BandeAnnonce, Image, DateSortie, JourDisponible, HeureDisponible, Debut, Fin, TiquetsRestantsSalle1, TiquetsRestantsSalle2, TiquetsRestantsSalle3) " +
                    "VALUES (1, 'Inception', 'A mind-bending thriller', '2h28m', 'trailer_url', 'picture_url', '2010-07-16', 'Friday', '20:00', '2023-01-01', '2023-01-31', 100, 150, 200)");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Close the connection
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void testGetAllFilms() throws SQLException {
        List<Film> films = filmDb.getAllFilms();
        assertEquals(1, films.size());

        Film film = films.get(0);
        assertEquals("Inception", film.getTitle());
        assertEquals("A mind-bending thriller", film.getText());
        assertEquals("2h28m", film.getDuration());
        assertEquals("trailer_url", film.getTrailer());
        assertEquals("picture_url", film.getPicture());
        assertEquals("2010-07-16", film.getReleasedate());
        assertEquals("Friday", film.getDayAvailable());
        assertEquals("20:00", film.getHourAvailable());
        assertEquals("2023-01-01", film.getStart());
        assertEquals("2023-01-31", film.getEnd());
        assertEquals(100, film.getRemainingticketsRoom1());
        assertEquals(150, film.getRemainingticketsRoom2());
        assertEquals(200, film.getRemainingticketsRoom3());
        assertEquals(1, film.getId());
    }

    @Test
    public void testGetFilmById() throws SQLException {
        Film film = filmDb.getFilmById(1);
        assertNotNull(film);
        assertEquals("Inception", film.getTitle());
        assertEquals("A mind-bending thriller", film.getText());
        assertEquals("2h28m", film.getDuration());
        assertEquals("trailer_url", film.getTrailer());
        assertEquals("picture_url", film.getPicture());
        assertEquals("2010-07-16", film.getReleasedate());
        assertEquals("Friday", film.getDayAvailable());
        assertEquals("20:00", film.getHourAvailable());
        assertEquals("2023-01-01", film.getStart());
        assertEquals("2023-01-31", film.getEnd());
        assertEquals(100, film.getRemainingticketsRoom1());
        assertEquals(150, film.getRemainingticketsRoom2());
        assertEquals(200, film.getRemainingticketsRoom3());
        assertEquals(1, film.getId());
    }

    @Test
    public void testUpdateFilm() throws SQLException {
        Film film = filmDb.getFilmById(1);
        assertNotNull(film);

        film.setRemainingticketsRoom1(90);
        film.setRemainingticketsRoom2(140);
        film.setRemainingticketsRoom3(180);
        filmDb.updateFilm(film);

        Film updatedFilm = filmDb.getFilmById(1);
        assertNotNull(updatedFilm);
        assertEquals(90, updatedFilm.getRemainingticketsRoom1());
        assertEquals(140, updatedFilm.getRemainingticketsRoom2());
        assertEquals(180, updatedFilm.getRemainingticketsRoom3());
    }

}
