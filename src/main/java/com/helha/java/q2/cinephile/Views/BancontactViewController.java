package com.helha.java.q2.cinephile.Views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Classe BancontactViewController qui gère l'interface utilisateur pour le paiement par Bancontact.
 */
public class BancontactViewController {

    @FXML
    private Label montantLabel;

    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    private Listener listener;
    private Blistener buttonListener;

    @FXML
    private TextField promoCodeTextField;

    private Double price;

    /**
     * Initialise les actions des boutons et du champ de texte.
     */
    @FXML
    private void initialize() {
        promoCodeTextField.setOnKeyReleased(event -> {
            price = Double.valueOf(montantLabel.getText().substring(0, montantLabel.getText().length() - 1));
            OnCodeEnter(promoCodeTextField.getText());
        });

        acceptButton.setOnAction(event -> {
            price = Double.valueOf(montantLabel.getText().substring(0, montantLabel.getText().length() - 1));
            OnAccepted(price);
        });

        rejectButton.setOnAction(event -> {
            price = Double.valueOf(montantLabel.getText().substring(0, montantLabel.getText().length() - 1));
            OnRejected(price);
        });
    }

    /**
     * Définit le montant à afficher.
     *
     * @param amount Le montant à afficher.
     */
    public void setAmount(double amount) {
        Platform.runLater(() -> {
            montantLabel.setText(String.valueOf(amount));
        });
    }

    /**
     * Réinitialise le montant affiché.
     */
    public void reSetAmount() {
        Platform.runLater(() -> {
            montantLabel.setText(null);
        });
    }

    /**
     * Gère l'action lors de l'acceptation du paiement.
     *
     * @param price Le prix à payer.
     */
    public void OnAccepted(Double price) {
        if (buttonListener != null) {
            buttonListener.OnAccepted(price);
        }
    }

    /**
     * Gère l'action lors du rejet du paiement.
     *
     * @param price Le prix à payer.
     */
    public void OnRejected(Double price) {
        if (buttonListener != null) {
            buttonListener.OnRejected(price);
        }
    }

    /**
     * Gère l'action lors de l'entrée d'un code promo.
     *
     * @param code Le code promo entré.
     */
    private void OnCodeEnter(String code) {
        if (listener != null) {
            listener.OnCodeEnter(code);
        }
    }

    /**
     * Définit le listener pour le code promo.
     *
     * @param listener Le listener à définir.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Définit le listener pour les boutons.
     *
     * @param buttonListener Le listener à définir.
     */
    public void setButtonListener(Blistener buttonListener) {
        this.buttonListener = buttonListener;
    }

    /**
     * Interface pour le listener des boutons.
     */
    public interface Blistener{
        void OnAccepted( Double price);
        void OnRejected( Double price);
    }

    /**
     * Interface pour le listener du code promo.
     */
    public interface Listener {
        void OnCodeEnter(String code);
    }
}
