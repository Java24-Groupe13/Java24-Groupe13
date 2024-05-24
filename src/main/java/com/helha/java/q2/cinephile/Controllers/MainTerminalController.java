package com.helha.java.q2.cinephile.Controllers;

import com.helha.java.q2.cinephile.Views.BancontactViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe MainTerminalController qui gère l'application du terminal principal.
 */
public class MainTerminalController extends Application {

    /**
     * Instance de BancontactViewController.
     */
    private static BancontactViewController bancontactViewController;

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
     * Code pour le paiement.
     */
    private String code;

    /**
     * Stage principal de l'application.
     */
    private static Stage stage;

    /**
     * Méthode principale qui lance l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        // Connect to the central server
        new Thread(MainTerminalController::connectToServer).start();

        // Launch the JavaFX application
        launch(args);
    }

    /**
     * Méthode pour démarrer l'application.
     *
     * @param primaryStage Le stage principal.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/helha/java/q2/cinephile/bancontact.fxml"));
        Parent root = loader.load();
        stage=primaryStage;
        bancontactViewController = loader.getController();
        bancontactViewController.setListener((codeEntered) -> {
            code = codeEntered;
        });
        bancontactViewController.setButtonListener(new BancontactViewController.Blistener() {
            @Override
            public void OnAccepted(Double price) {
                System.out.println("Accepted");
                sendPaymentResponse("PaymentAccepted", price, code);
            }

            @Override
            public void OnRejected(Double price) {
                sendPaymentResponse("PaymentRejected", price, code);
            }
        });

        primaryStage.setTitle("Bancontact Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Méthode pour se connecter au serveur.
     */
    private static void connectToServer() {
        String serverAddress = "127.0.0.1"; // Server IP address (localhost)
        int serverPort = 12345; // Server port

        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to the central server at " + serverAddress + ":" + serverPort);

            // Keep reading responses from the server
            while (true) {
                Object response = in.readObject();
                if (response instanceof String) {
                    String command = (String) response;
                    if (command.startsWith("SEND_PAYMENT")) {
                        double finalAmount = Double.parseDouble(command.split(" ")[1]);
                        System.out.println("Payment accepted. Final amount: " + finalAmount);
                        Platform.runLater(() -> {
                            notifyPrimaryStage();

                            bancontactViewController.setAmount(finalAmount);
                        });
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour notifier le stage principal.
     */
    private static void notifyPrimaryStage() {
        // Bring primary stage to front and request focus
        stage.setAlwaysOnTop(true);  // Force it to the front
        stage.toFront();
        stage.requestFocus();
        stage.setAlwaysOnTop(false);  // Reset the always-on-top status
    }

    /**
     * Méthode pour envoyer le stage principal en arrière-plan.
     */
    private static void sendPrimaryStageToBack() {
        // Send primary stage to back
        stage.setAlwaysOnTop(false); // Ensure it is not always on top
        stage.toBack(); // Send it to the back
    }

    /**
     * Méthode pour envoyer la réponse de paiement.
     *
     * @param response La réponse à envoyer.
     * @param finalAmount Le montant final à payer.
     * @param code Le code pour le paiement.
     */
    private static void sendPaymentResponse(String response, Double finalAmount, String code) {
        sendPrimaryStageToBack();
        try {
            System.out.println("Sending payment response: " + response );
            out.writeObject("RESEND_PAYMENTRESPONSE " +response + " " + finalAmount + " "+ code);
            out.flush();
            bancontactViewController.reSetAmount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
