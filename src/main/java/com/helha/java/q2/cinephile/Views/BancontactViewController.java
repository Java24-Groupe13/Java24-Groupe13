package com.helha.java.q2.cinephile.Views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    public void setAmount(double amount) {
        Platform.runLater(() -> {
            montantLabel.setText(String.valueOf(amount));
        });
    }
    public void reSetAmount() {
        Platform.runLater(() -> {
            montantLabel.setText(null);
        });
    }


    public void OnAccepted(Double price) {
        if (buttonListener != null) {
            buttonListener.OnAccepted(price);
        }
    }
    public void OnRejected(Double price) {
        if (buttonListener != null) {
            buttonListener.OnRejected(price);
        }
    }


    private void OnCodeEnter(String code) {
        if (listener != null) {
            listener.OnCodeEnter(code);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public void setButtonListener(Blistener buttonListener) {
        this.buttonListener = buttonListener;
    }
    public interface Blistener{
        void OnAccepted( Double price);
        void OnRejected( Double price);
    }

    public interface Listener {
        void OnCodeEnter(String code);
    }
}
