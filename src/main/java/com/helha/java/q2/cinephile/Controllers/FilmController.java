package com.helha.java.q2.cinephile.Controllers;

import com.helha.java.q2.cinephile.Models.Film;
import com.helha.java.q2.cinephile.Models.Tickets;
import com.helha.java.q2.cinephile.Views.FilmViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class FilmController {
    /**
     * Instance de FilmViewController.
     */
    private static FilmViewController filmView;

    /**
     * Socket pour la communication avec le serveur.
     */
    private static Socket socket;

    /**
     * Flux de sortie pour envoyer des objets au serveur.
     */
    private static ObjectOutputStream out;

    /**
     * Flux d'entrée pour recevoir des objets du serveur.
     */
    private static ObjectInputStream in;

    /**
     * Constructeur de FilmController.
     */

    public FilmController() {
        this.filmView = filmView;

    }

    /**
     * Méthode pour démarrer l'application.
     *
     * @param primaryStage Le stage principal.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     * @throws URISyntaxException Si une erreur de syntaxe d'URI se produit.
     */
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/helha/java/q2/cinephile/FilmView.fxml"));
        Parent root = loader.load();
        filmView = loader.getController();
        filmView.setListener(new FilmViewController.goToScheduleListener() {
            @Override
            public void openSchedulePage(Film film) throws IOException, URISyntaxException {
                ScheduleController scheduleController = new ScheduleController();
                scheduleController.start(primaryStage, film);
            }
        });
        filmView.setFilmController(this); // Passer une instance de FilmController au FilmViewController

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Cinephile");
        primaryStage.setWidth(1000); // Largeur en pixels
        primaryStage.setHeight(700); // Hauteur en pixels
        primaryStage.show();
        connectToServer();
        loadFilms();
        loadTickets();
    }

    /**
     * Méthode pour se connecter au serveur.
     */
    static void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour charger les films depuis le serveur.
     */
    static void loadFilms() {
        try {
            out.writeObject("GET_FILMS");
            out.flush();
            List<Film> films = (List<Film>) in.readObject();
            filmView.displayFilms(films);
            System.out.println(films.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode pour charger les tickets depuis le serveur.
     */
    public static void loadTickets() {
        try {
            out.writeObject("GET_TIQUETS");
            out.flush();
            List<Tickets> tiquets = null;
            tiquets = (List<Tickets>) in.readObject();
            filmView.displayTickets(tiquets);
            System.out.println(tiquets.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void reloadData(String command) {
        try {
            out.writeObject("GET_DATA");
            out.flush();
            String[] parts = command.split(" ");
            List<Tickets> tiquets = null;

            tiquets = (List<Tickets>) in.readObject();
            filmView.displayTickets(tiquets);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}