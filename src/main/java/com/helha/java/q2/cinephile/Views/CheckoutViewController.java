package com.helha.java.q2.cinephile.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Classe CheckoutViewController qui gère l'interface utilisateur pour le processus de paiement.
 */
public class CheckoutViewController {

    @FXML
    private TextField adultTextField;

    @FXML
    private TextField childTextField;

    @FXML
    private TextField seniorTextField;


    @FXML
    private Button checkoutbtn;


    @FXML
    private Label ticketPriceLabel;
    private NavListener listener;


    private double adultPrice = 8.50;
    private double childPrice = 5.00;
    private double seniorPrice = 7.50;


    @FXML
    private ComboBox<String> paymentMethodComboBox;


    /**
     * Initialise le contrôleur.
     */
    @FXML
    private void initialize() {

        paymentMethodComboBox.getItems().addAll("Credit Card", "Bancontact");
        paymentMethodComboBox.setValue("Credit Card"); // Set a default value

        // Ajout d'écouteurs aux TextField pour mettre à jour le prix total
        adultTextField.textProperty().addListener((observable, oldValue, newValue) -> updateTotalPrice());
        childTextField.textProperty().addListener((observable, oldValue, newValue) -> updateTotalPrice());
        seniorTextField.textProperty().addListener((observable, oldValue, newValue) -> updateTotalPrice());


        // Mettre à jour le prix total au démarrage
        updateTotalPrice();

        checkoutbtn.setOnAction(event -> {
            // Récupérer le prix à nouveau au moment du clic
            String labelText = ticketPriceLabel.getText();
            double ticketPrice = Double.parseDouble(labelText);
            System.out.println("Prix du ticket : " + ticketPrice);
            openBancontactPage(ticketPrice);
        });

    }

    /**
     * Ouvre la page de paiement Bancontact.
     *
     * @param prix Le prix à payer.
     */
    private void openBancontactPage(Double prix) {
        if (listener != null) {
            listener.sendToTerminal(prix);
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
        void sendToTerminal(Double prix);
    }

    /**
     * Gère l'action du bouton de réinitialisation.
     *
     * @param event L'événement d'action.
     */
    @FXML
    private void handleResetButton(ActionEvent event) {
        adultTextField.clear();
        childTextField.clear();
        seniorTextField.clear();
    }

    /**
     * Décrémente le nombre de billets pour adultes.
     */
    @FXML
    private void decrementAdult() {
        int count = parseTextFieldValue(adultTextField.getText());
        if (count > 0) {
            adultTextField.setText(String.valueOf(count - 1));
            updateTotalPrice();
        }
    }

    /**
     * Incrémente le nombre de billets pour adultes.
     */
    @FXML
    private void incrementAdult() {
        int count = parseTextFieldValue(adultTextField.getText());
        adultTextField.setText(String.valueOf(count + 1));
        updateTotalPrice();
    }

    /**
     * Décrémente le nombre de billets pour enfants.
     */
    @FXML
    private void decrementChild() {
        int count = parseTextFieldValue(childTextField.getText());
        if (count > 0) {
            childTextField.setText(String.valueOf(count - 1));
            updateTotalPrice();
        }
    }

    /**
     * Incrémente le nombre de billets pour enfants.
     */
    @FXML
    private void incrementChild() {
        int count = parseTextFieldValue(childTextField.getText());
        childTextField.setText(String.valueOf(count + 1));
        updateTotalPrice();
    }

    /**
     * Décrémente le nombre de billets pour seniors.
     */
    @FXML
    private void decrementSenior() {
        int count = parseTextFieldValue(seniorTextField.getText());
        if (count > 0) {
            seniorTextField.setText(String.valueOf(count - 1));
            updateTotalPrice();
        }
    }

    /**
     * Incrémente le nombre de billets pour seniors.
     */
    @FXML
    private void incrementSenior() {
        int count = parseTextFieldValue(seniorTextField.getText());
        seniorTextField.setText(String.valueOf(count + 1));
        updateTotalPrice();
    }

    /**
     * Récupère le nombre total de billets choisis.
     *
     * @return Le nombre total de billets choisis.
     */
    @FXML
    public int getTotalTicketsChosen() {
        int adultCount = parseTextFieldValue(adultTextField.getText());
        int childCount = parseTextFieldValue(childTextField.getText());
        int seniorCount = parseTextFieldValue(seniorTextField.getText());


        return adultCount + childCount + seniorCount ;
    }
    /**
     * Récupère le nombre de billets pour enfants.
     *
     * @return Le nombre de billets pour enfants.
     */
    public int getChildTicket(){
        return parseTextFieldValue(childTextField.getText());
    }
    /**
     * Récupère le nombre de billets pour seniors.
     *
     * @return Le nombre de billets pour seniors.
     */
    public int getSeniorTicket(){
        return parseTextFieldValue(seniorTextField.getText());
    }
    /**
     * Récupère le nombre de billets pour adultes.
     *
     * @return Le nombre de billets pour adultes.
     */
    public int getAdultTicket(){
        return parseTextFieldValue(adultTextField.getText());
    }

    /**
     * Met à jour le prix total.
     */
    private void updateTotalPrice() {
        int adultCount = parseTextFieldValue(adultTextField.getText());
        int childCount = parseTextFieldValue(childTextField.getText());
        int seniorCount = parseTextFieldValue(seniorTextField.getText());


        double totalPrice = (adultCount * adultPrice) + (childCount * childPrice) + (seniorCount * seniorPrice);
        ticketPriceLabel.setText(String.valueOf(totalPrice));
    }

    /**
     * Met à jour le prix total.
     *
     * @param prix Le nouveau prix.
     */
    public void updateTotalPrice(Double prix) {
        ticketPriceLabel.setText(String.valueOf(prix));
    }

    /**
     * Analyse la valeur du champ de texte.
     *
     * @param text Le texte à analyser.
     * @return La valeur analysée.
     */
    private int parseTextFieldValue(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}



