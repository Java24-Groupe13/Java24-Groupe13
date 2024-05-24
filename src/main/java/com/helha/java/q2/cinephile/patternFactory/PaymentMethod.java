package com.helha.java.q2.cinephile.patternFactory;

/**
 * Interface PaymentMethod qui définit une méthode de paiement.
 */
public interface PaymentMethod {

    /**
     * Méthode pour effectuer un paiement.
     *
     * @param amount Le montant à payer.
     */
    void pay(double amount);
}



