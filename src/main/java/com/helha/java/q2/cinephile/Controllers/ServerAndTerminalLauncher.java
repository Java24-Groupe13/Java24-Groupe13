package com.helha.java.q2.cinephile.Controllers;

/**
 * Classe ServerAndTerminalLauncher qui lance le serveur central et le terminal principal.
 */
public class ServerAndTerminalLauncher {

    /**
     * Méthode principale qui lance le serveur central et le terminal principal.
     *
     * @param args Les arguments de la ligne de commande.
     * @throws InterruptedException Si le thread est interrompu pendant son sommeil.
     */
    public static void main(String[] args) throws InterruptedException {
        // Lance le serveur central dans un nouveau thread
        new Thread(() -> {
            CentralServer.main(args);
        }).start();
        // Fait une pause pour s'assurer que le serveur est lancé avant le terminal
        Thread.sleep(2000);

        // Lance le terminal principal dans un nouveau thread
        new Thread(() -> {
            MainTerminalController.main(args);
        }).start();
        // Fait une pause pour s'assurer que le terminal est lancé après le serveur
        Thread.sleep(2000);
    }
}
