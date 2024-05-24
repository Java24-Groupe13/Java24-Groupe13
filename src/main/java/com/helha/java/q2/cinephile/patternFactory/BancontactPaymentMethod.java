package com.helha.java.q2.cinephile.patternFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe BancontactPaymentMethod qui implémente l'interface PaymentMethod.
 * Cette classe représente une méthode de paiement par Bancontact.
 */
public class BancontactPaymentMethod implements PaymentMethod {

    /**
     * Méthode pour effectuer un paiement.
     *
     * @param amount Le montant à payer.
     */
    @Override
    public void pay(double amount) {

        String message = "Paid " + amount + "€ using Bancontact";
        writeToFile(message);
    }

    /**
     * Méthode privée pour écrire un message dans un fichier.
     *
     * @param message Le message à écrire.
     */
    private void writeToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
