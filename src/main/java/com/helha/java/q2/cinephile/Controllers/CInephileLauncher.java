package com.helha.java.q2.cinephile.Controllers;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Classe MainCinephileController qui lance l'application.
 */

public class CInephileLauncher extends Application {
    /**
     * Méthode principale qui lance l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Méthode pour démarrer l'application.
     *
     * @param primaryStage Le stage principal.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     * @throws URISyntaxException Si une erreur de syntaxe d'URI se produit.
     */
    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException {
        FilmController filmController = new FilmController();
        filmController.start(primaryStage);
    }
}



