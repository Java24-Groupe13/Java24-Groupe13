package com.helha.java.q2.cinephile.Controllers;

import com.helha.java.q2.cinephile.Models.Film;
import com.helha.java.q2.cinephile.Views.CheckoutViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * Classe CheckoutController qui gère le processus de paiement.
 */
public class CheckoutController {
    /**
     * Instance de CheckoutViewController.
     */
    static CheckoutViewController checkoutViewController;

    /**
     * Méthode pour ouvrir la page de paiement.
     *
     * @param film Le film sélectionné.
     * @param room La salle sélectionnée.
     * @param hour L'heure sélectionnée.
     * @param shedulePageStage Le stage de la page de programmation.
     */
    public static void openCheckout(Film film, String room, String hour,Stage shedulePageStage) {
        try {
            FXMLLoader loader = new FXMLLoader(CheckoutController.class.getResource("/com/helha/java/q2/cinephile/checkout.fxml"));
            Parent root = loader.load();
            // Obtient la scène actuelle
            Scene newScene = new Scene(root);
            // Créez un nouveau stage pour la nouvelle scène
            Stage checkoutPageStage = new Stage();
            checkoutPageStage.setScene(newScene);
            checkoutPageStage.setWidth(875);
            checkoutPageStage.setHeight(800);
            checkoutPageStage.show();
            checkoutViewController = loader.getController();
            checkoutViewController.setListener(prix -> {
                System.out.println("room: "+room + " hour: "+hour);
                startClient(prix, film, room, hour, checkoutPageStage, shedulePageStage);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour démarrer le client et envoyer le montant à payer au serveur.
     *
     * @param prix Le prix total à payer.
     * @param film Le film sélectionné.
     * @param room La salle sélectionnée.
     * @param hour L'heure sélectionnée.
     * @param checkoutPageStage Le stage de la page de paiement.
     * @param shedulePageStage Le stage de la page de programmation.
     */
    private static void startClient(Double prix, Film film, String room, String hour, Stage checkoutPageStage, Stage shedulePageStage) {
        String serverAddress = "127.0.0.1"; // Adresse IP du serveur (localhost)
        int serverPort = 12345; // Port utilisé par le serveur
        try (
                Socket socket = new Socket(serverAddress, serverPort);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            System.out.println("Client démarré, connecté au serveur " + serverAddress + ":" + serverPort);

            // Envoi du montant au serveur
            int numberOfTickets = checkoutViewController.getTotalTicketsChosen();
            int numberOfChildTickets= checkoutViewController.getChildTicket();
            int numberOfAdultTickets= checkoutViewController.getAdultTicket();
            int numberOfSeniorTickets= checkoutViewController.getSeniorTicket();
            out.writeObject("SEND_PAYMENT " + prix + " "+ numberOfTickets+" "+film.getId()+" "+numberOfChildTickets+" "+numberOfAdultTickets+" "+numberOfSeniorTickets+" "+room+" "+hour);
            out.flush();
            System.out.println("Montant " + prix + " envoyé au serveur.");

            // Lecture de la réponse du serveur
            boolean responseReceived = false;
            while (!responseReceived) {
                Object response = in.readObject();
                if (response instanceof String) {
                    String command = (String) response;
                    if (command.startsWith("PaymentAccepted")) {
                        System.out.println("Réponse du serveur: " + response);
                        System.out.println("le client a accepté la commande");
                        double finalAmount = Double.parseDouble(command.split(" ")[1]); // Récupère le montant final
                        System.out.println("le client a accepté la commande " + finalAmount);
                        checkoutViewController.updateTotalPrice(finalAmount);
                        System.out.println("Montant final restant: " + finalAmount);
                        System.out.println("Nombre de tiquet: " + numberOfTickets);
                        FilmController.connectToServer();
                        FilmController.loadTickets();
                        FilmController.connectToServer();
                        FilmController.loadFilms();
                        System.out.println("rechargement historique");
                        // Fermer la communication après avoir reçu la réponse attendue
                        responseReceived = true;
                        checkoutPageStage.close();
                        shedulePageStage.close();
                    } else if (command.startsWith("PaymentRejected")) {
                        System.out.println("le client a refusé la commande");
                        responseReceived = true;
                    }

                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

