package com.helha.java.q2.cinephile.patternFactory;

/**
 * Classe abstraite PaymentMethodFactory qui crée des instances de PaymentMethod.
 */
public abstract class PaymentMethodFactory {

    /**
     * Crée une instance de PaymentMethod en fonction du type spécifié.
     *
     * @param type Le type de méthode de paiement. Peut être "Bancontact" ou "CreditCard".
     * @return Une instance de PaymentMethod correspondant au type spécifié.
     * @throws IllegalArgumentException Si le type spécifié n'est pas "Bancontact" ou "CreditCard".
     */
    public static PaymentMethod createPaymentMethod(String type) {
        if (type.equals("Bancontact")) {
            return new BancontactPaymentMethod();
        }  else if (type.equals("CreditCard")) {
            return new CreditCardPaymentMethod();
        }
        throw new IllegalArgumentException("Invalid payment method type");
    }
}


