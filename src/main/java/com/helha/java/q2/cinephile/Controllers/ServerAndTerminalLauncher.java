package com.helha.java.q2.cinephile.Controllers;

public class ServerAndTerminalLauncher {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            CentralServer.main(args);
        }).start();
        Thread.sleep(2000);

        new Thread(() -> {
            MainTerminalController.main(args);
        }).start();
        Thread.sleep(2000);
    }
}
