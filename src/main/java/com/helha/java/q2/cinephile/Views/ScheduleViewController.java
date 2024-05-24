package com.helha.java.q2.cinephile.Views;

import com.helha.java.q2.cinephile.Models.Film;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

/**
 * Classe ScheduleViewController qui gère l'affichage des horaires des films.
 */
public class ScheduleViewController {

    @FXML
    private ToggleButton room1Button;

    @FXML
    private ToggleButton room2Button;

    @FXML
    private ToggleButton room3Button;

    @FXML
    private Label moviesynopsis;

    @FXML
    private Label movieduration;

    @FXML
    private Label movietitle;

    private NavListener listener;

    private String selectedRoom;

    private String selectedHour;

    @FXML
    private Button buyticketbtn;

    @FXML
    private Label ticketsRoom1;

    @FXML
    private Label ticketsRoom2;

    @FXML
    private Label ticketsRoom3;

    /**
     * Initialise le contrôleur.
     */
    @FXML
    private void initialize() {
        buyticketbtn.setOnAction(event -> openCheckoutPage());
        buyticketbtn.setDisable(true);  // Disable the button initially
        room1Button.setOnAction(this::handleRoomButtonAction);
        room2Button.setOnAction(this::handleRoomButtonAction);
        room3Button.setOnAction(this::handleRoomButtonAction);
    }

    /**
     * Gère l'action du bouton de la salle.
     *
     * @param event L'événement d'action.
     */
    private void handleRoomButtonAction(ActionEvent event) {
        ToggleButton selectedButton = (ToggleButton) event.getSource();

        if (selectedButton.isSelected()) {
            // Mettre à jour la salle sélectionnée
            if (selectedButton == room1Button) {
                selectedRoom = "1";
                selectedHour = "13H";
                room2Button.setSelected(false);
                room3Button.setSelected(false);
            } else if (selectedButton == room2Button) {
                selectedRoom = "2";
                selectedHour = "17H";
                room1Button.setSelected(false);
                room3Button.setSelected(false);
            } else if (selectedButton == room3Button) {
                selectedRoom = "3";
                selectedHour = "19H";
                room1Button.setSelected(false);
                room2Button.setSelected(false);
            }
            // Mettre à jour le style des boutons
            room1Button.setStyle(selectedButton == room1Button ? "-fx-background-color: green; -fx-background-radius: 20; -fx-text-fill: white;" : "-fx-background-color: #aab0ad; -fx-background-radius: 20;");
            room2Button.setStyle(selectedButton == room2Button ? "-fx-background-color: green; -fx-background-radius: 20; -fx-text-fill: white;" : "-fx-background-color: #aab0ad; -fx-background-radius: 20;");
            room3Button.setStyle(selectedButton == room3Button ? "-fx-background-color: green; -fx-background-radius: 20; -fx-text-fill: white;" : "-fx-background-color: #aab0ad; -fx-background-radius: 20;");

            // Enable the buy ticket button if a room is selected
            buyticketbtn.setDisable(false);
        } else {
            // Disable the buy ticket button if no room is selected
            buyticketbtn.setDisable(true);
        }
    }

    /**
     * Définit le film à afficher sur l'interface utilisateur.
     *
     * @param film Le film à afficher.
     */
    public void setFilm(Film film) {
        moviesynopsis.setText(film.getText());
        movietitle.setText(film.getTitle());
        movieduration.setText(film.getDuration());
        afficherTicketsRestants(
                film.getRemainingticketsRoom1(),
                film.getRemainingticketsRoom2(),
                film.getRemainingticketsRoom3()
        );
    }

    /**
     * Ouvre la page de paiement.
     */
    private void openCheckoutPage() {
        if (listener != null) {
            listener.openCheckoutPage(selectedRoom, selectedHour);
        }
    }

    /**
     * Définit le listener pour la navigation.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(NavListener listener) {
        this.listener = listener;
    }

    /**
     * Interface pour le listener de navigation.
     */
    public interface NavListener {
        void openCheckoutPage(String selectedRoom, String selectedHour);
    }

    /**
     * Affiche le nombre de billets restants pour chaque salle.
     *
     * @param ticketsRoom1Count Le nombre de billets restants pour la salle 1.
     * @param ticketsRoom2Count Le nombre de billets restants pour la salle 2.
     * @param ticketsRoom3Count Le nombre de billets restants pour la salle 3.
     */
    private void afficherTicketsRestants(int ticketsRoom1Count, int ticketsRoom2Count, int ticketsRoom3Count) {
        ticketsRoom1.setText("Tickets restants : " + ticketsRoom1Count);
        ticketsRoom2.setText("Tickets restants : " + ticketsRoom2Count);
        ticketsRoom3.setText("Tickets restants : " + ticketsRoom3Count);
    }
}



