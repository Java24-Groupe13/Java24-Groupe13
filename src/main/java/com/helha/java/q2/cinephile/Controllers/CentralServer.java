package com.helha.java.q2.cinephile.Controllers;

import com.helha.java.q2.cinephile.Models.Film;
import com.helha.java.q2.cinephile.Models.FilmDb;
import com.helha.java.q2.cinephile.Models.Tickets;
import com.helha.java.q2.cinephile.Models.TicketsDb;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CentralServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    static int numberOfTickets;
    static int numberOfChildTickets;
    static int numberOfAdultTickets;
    static int numberOfSeniorTickets;
    static int FilmId;
    static int room;
    static String hour;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Central server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.in = new ObjectInputStream(clientSocket.getInputStream());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object message = in.readObject();
                    if (message instanceof String command) {
                        if (command.equals("GET_FILMS")) {
                            sendFilms();
                            System.out.println("Films sent to the client");
                        } else if (command.startsWith("SEND_PAYMENT")) {
                            sendPaymentToTerminal(command);
                            System.out.println("reponse envoyer au terminal");
                        } else if (command.startsWith("RESEND_PAYMENTRESPONSE")) {
                            sendPaymentToMain(command);
                            System.out.println("reponse envoyer au main");

                        } else if (command.equals("GET_TIQUETS")) {
                            sendTickets();
                            System.out.println("Films sent to the client");
                        }
                    }
                }
            } catch (EOFException e) {
                System.out.println("Client disconnected.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clients.remove(this);
            }
        }

        private void sendFilms() {
            FilmDb filmDb = new FilmDb();
            List<Film> films = filmDb.getAllFilms();
            try {
                out.writeObject(films);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void sendTickets() {
            TicketsDb ticketsDb = new TicketsDb();
            List<Tickets> tiquets = ticketsDb.getAllTiquets();
            try {
                out.writeObject(tiquets);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendPaymentToTerminal(String command) {
            String[] parts = command.split(" ");
            double amount = Double.parseDouble(parts[1]);
            numberOfTickets = Integer.parseInt(parts[2]);
            FilmId = Integer.parseInt(parts[3]);
            numberOfChildTickets = Integer.parseInt(parts[4]);
            numberOfAdultTickets = Integer.parseInt(parts[5]);
            numberOfSeniorTickets = Integer.parseInt(parts[6]);
            room = Integer.parseInt(parts[7]);
            hour = parts[8];


            try {

                // Send to MainTerminal
                for (ClientHandler client : clients) {
                    if (client != this) {
                        client.out.writeObject("SEND_PAYMENT " + amount);
                        client.out.flush();
                        System.out.println("Payment sent to MainTerminal: " + amount);
                        return; // Assuming only one MainTerminal client
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendPaymentToMain(String command) throws IOException, SQLException {

            System.out.println("send to main: " + command);
            String[] parts = command.split(" ");
            if (parts.length < 4) {
                System.out.println("Invalid response format: " + command);
                return;
            }
            String response = parts[1];
            System.out.println(response);
            double finalAmount = Double.parseDouble(parts[2]);  // Le montant final
            String code = parts[3];
            if(code!=null){
               if(comparePromoCode(code)){
                   finalAmount = finalAmount * 0.9;
               }
            }
            System.out.println("Nombre tiquet: " + numberOfTickets);
            System.out.println("FilmId: " + FilmId);
            if(response.equals("PaymentAccepted")){
                FilmDb filmDb = new FilmDb();
                Film film=filmDb.getFilmById(FilmId);
                createNewTiquet(FilmId, numberOfTickets,room,hour, finalAmount, numberOfChildTickets, numberOfSeniorTickets, numberOfAdultTickets, film.getTitle());
                updateRemainingTickets(FilmId,room, numberOfTickets);
            }

            // Envoyer la réponse à tous les clients sauf à celui-ci
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.out.writeObject(response + " " + finalAmount);
                    client.out.flush();
                    System.out.println("Payment response sent to a client: " + response + " " + finalAmount);
                }
            }
        }


        public static String readPromoCode() throws IOException {
            String filePath = "src/main/resources/com/helha/java/q2/cinephile/promotionCode.txt";
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        }



        public static boolean comparePromoCode(String codeEntered) {
            try {
                String fileContent = readPromoCode();
                String[] lines = fileContent.split("\n");

                for (String line : lines) {
                    if (line.trim().equals(codeEntered)) {
                        System.out.println("Promo code " + codeEntered + " found.");
                        return true;
                    }
                }
                return false;
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            return false;
        }



        private static void createNewTiquet(int filmId, int nombreDeTiquet, int salle, String heure, double prix, int nombreDeTiquetEnfant, int nombreDeTiquetSenior, int nombreDeTiquetAdulte, String nomFilm) {
            TicketsDb ticketsDb = new TicketsDb();
            Tickets newTicket = new Tickets();
            newTicket.setFilmId(filmId);
            newTicket.setNumberOfTickets(nombreDeTiquet);
            newTicket.setRoom(salle);
            newTicket.setHour(heure);
            newTicket.setPrice(String.valueOf(prix));
            newTicket.setNumberOfChildrenTickets(nombreDeTiquetEnfant);
            newTicket.setNumberOfSeniorTickets(nombreDeTiquetSenior);
            newTicket.setNumberOfAdultTickets(nombreDeTiquetAdulte);
            newTicket.setFilmName(nomFilm);

            ticketsDb.insertTiquet(newTicket);
            System.out.println("Nouveau tiquet créé avec succès.");
        }



        private static void updateRemainingTickets(int filmId, int salle, int numberOfBuyTickets) {
            FilmDb filmDb = new FilmDb();
            try {
                // Récupérer les informations actuelles sur les tiquets restants pour la salle choisie
                Film film = filmDb.getFilmById(filmId);

                // Mettre à jour les tiquets restants dans la salle choisie
                switch (salle) {
                    case 1:
                        System.out.println("salle: 1"+film.getTitle()+numberOfBuyTickets);
                        film.setRemainingticketsRoom1(film.getRemainingticketsRoom1() - numberOfBuyTickets);
                        break;
                    case 2:
                        System.out.println("salle: 2"+film.getTitle()+numberOfBuyTickets);
                        film.setRemainingticketsRoom2(film.getRemainingticketsRoom2() - numberOfBuyTickets);
                        break;
                    case 3:
                        System.out.println("salle: 3"+film.getTitle()+numberOfBuyTickets);
                        film.setRemainingticketsRoom3(film.getRemainingticketsRoom3() - numberOfBuyTickets);
                        break;
                    default:
                        System.out.println("Salle inconnue.");
                        return;
                }

                // Mettre à jour les informations dans la base de données
                filmDb.updateFilm(film);
                System.out.println("Mise à jour des tiquets restants pour la salle " + salle + " effectuée avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
