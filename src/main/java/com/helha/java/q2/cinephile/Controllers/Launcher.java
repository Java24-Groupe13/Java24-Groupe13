package com.helha.java.q2.cinephile.Controllers;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        try {
            // Lancer CentralServer
            System.out.println("Lancement de CentralServer...");
            Process centralServerProcess = new ProcessBuilder("java", "-cp", "path/to/CentralServer.jar", "com.example.CentralServer").start();
            Thread.sleep(2000); // Attendre 2 secondes

            // Lancer MainTerminal
            System.out.println("Lancement de MainTerminal...");
            Process mainTerminalProcess = new ProcessBuilder("java", "-cp", "path/to/MainTerminal.jar", "com.example.MainTerminal").start();
            Thread.sleep(2000); // Attendre 2 secondes

            // Lancer MainCinephileController
            System.out.println("Lancement de MainCinephileController...");
            Process mainCinephileProcess = new ProcessBuilder("java", "-cp", "path/to/MainCinephileController.jar", "com.example.MainCinephileController").start();

            // Attendre que tous les processus se terminent
            int exitCode1 = centralServerProcess.waitFor();
            int exitCode2 = mainTerminalProcess.waitFor();
            int exitCode3 = mainCinephileProcess.waitFor();

            System.out.println("Tous les processus ont terminé avec les codes de sortie :");
            System.out.println("CentralServer : " + exitCode1);
            System.out.println("MainTerminal : " + exitCode2);
            System.out.println("MainCinephileController : " + exitCode3);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


