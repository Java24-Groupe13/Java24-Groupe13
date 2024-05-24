package com.helha.java.q2.cinephile.patternFactory;

/**
 * Classe CreditCardPaymentMethod qui implémente l'interface PaymentMethod.
 * Cette classe représente une méthode de paiement par carte de crédit.
 */
public class CreditCardPaymentMethod implements PaymentMethod {

    /**
     * Méthode pour effectuer un paiement.
     *
     * @param amount Le montant à payer.
     */
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}


