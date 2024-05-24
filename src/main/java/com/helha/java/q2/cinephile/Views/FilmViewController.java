package com.helha.java.q2.cinephile.Views;

import com.helha.java.q2.cinephile.Controllers.FilmController;
import com.helha.java.q2.cinephile.Models.Film;
import com.helha.java.q2.cinephile.Models.Tickets;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilmViewController implements Initializable {
    @FXML
    public FlowPane flowPane;
    public Menu menu;

    private FilmController filmController;

    private goToScheduleListener listener;

    public FilmViewController() {
    }
    public void setFilmController(FilmController filmController) {
        this.filmController = filmController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void displayFilms(List<Film> films) throws MalformedURLException, URISyntaxException {
        flowPane.getChildren().clear();
        for (Film film : films) {
            ImageView FrontImage = new ImageView(new Image(film.getPicture()));
            FrontImage.setFitWidth(200);
            FrontImage.setFitHeight(300);
            Rectangle clip = new Rectangle(200, 300);
            Rectangle clip2 = new Rectangle(200, 300);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            clip2.setArcWidth(20);
            clip2.setArcHeight(20);
            String imageURL = "https://imgs.search.brave.com/ihZ8YVmYBtj0IjiPcGafKKQDZvUEg6MeG3ZGoArDP0g/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9pbWcu/ZnJlZXBpay5jb20v/cGhvdG9zLXByZW1p/dW0vYWJzdHJhaXQt/ZGVncmFkZS1ncmlz/Xzk2MzUtMTUxOC5q/cGc_c2l6ZT02MjYm/ZXh0PWpwZw";
            Image image = new Image(imageURL);
            ImageView backImage = new ImageView(image);
            backImage.setFitWidth(200);
            backImage.setFitHeight(300);
            backImage.setOpacity(0);
            FrontImage.setClip(clip);
            backImage.setClip(clip2);

            Text text = new Text(film.getTitle());
            text.setStyle(" -fx-font-weight: bold;");
            text.setOpacity(0);
            text.setFont(Font.font(text.getFont().getName(), 10));
            splitTextIfNeeded(text, film.getTitle());

            Button button = new Button("Reserver");
            button.setOpacity(0);
            button.setStyle("-fx-background-color: #4CAF50; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 10px 20px; " +
                    "-fx-background-radius: 5px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);"); /* Effet d'ombre */
            button.setOnMouseClicked(event -> {
                try {
                    openSchedulePage(film);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });




            String actualText = text.getText();
            String finalText = actualText + "\n" + "\n" + "Durée : " + film.getDuration() ;
            text.setText(finalText);


            StackPane stackPane = new StackPane(FrontImage, backImage, text, button);
            StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
            stackPane.setOnMouseEntered(event -> flipImage(FrontImage, backImage, text, button)); // Utilisation de la nouvelle fonction flipImage
            stackPane.setOnMouseExited(event -> flipBackImage(backImage, FrontImage, text, button)); // Utilisation de la nouvelle fonction flipImageBack
            flowPane.getChildren().add(stackPane);
        }

    }

    private void openSchedulePage(Film film) throws IOException, URISyntaxException {
        if (listener != null){
            listener.openSchedulePage(film);
        }

    }

    public void setListener (goToScheduleListener listener){
        this.listener = listener;
    }

    public interface goToScheduleListener{
        void openSchedulePage(Film film) throws IOException, URISyntaxException;
    }



    private void flipImage(ImageView fromImageView, ImageView toPane, Text text, Button button) {
        // Transition de mise à l'échelle pour l'image sortante
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.25), fromImageView);
        scaleOut.setToX(0);
        scaleOut.setInterpolator(Interpolator.EASE_BOTH);

        // Transition de mise à l'échelle pour l'image entrante
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.25), toPane);
        scaleIn.setToX(1);
        scaleIn.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleTextIn = new ScaleTransition(Duration.seconds(0.25), text);
        scaleTextIn.setToX(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleTextIn.setToY(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleTextIn.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleButtonIn = new ScaleTransition(Duration.seconds(0.25), button);
        scaleButtonIn.setToX(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleButtonIn.setToY(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleButtonIn.setInterpolator(Interpolator.EASE_BOTH);

        scaleOut.setOnFinished(event -> {
            fromImageView.setOpacity(0);
            toPane.setOpacity(1);
            text.setOpacity(1);
            button.setOpacity(1);
            scaleIn.play();
            scaleTextIn.play();
            scaleButtonIn.play();
        });

        scaleOut.play();
    }

    /**
     * Réalise une transition pour faire pivoter l'image lorsque la souris quitte la zone du film.
     *
     * @param fromImageView L'image actuelle.
     * @param toPane        L'image à afficher.
     * @param texte         Le texte à afficher.
     * @param button        Le bouton à afficher.
     */
    private void flipBackImage(ImageView fromImageView, ImageView toPane, Text texte, Button button) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        ScaleTransition scaleOut = new ScaleTransition(Duration.seconds(0.25), fromImageView);
        scaleOut.setToX(0);
        scaleOut.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.25), toPane);
        scaleIn.setToX(1);
        scaleIn.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleTextIn = new ScaleTransition(Duration.seconds(0.25), texte);
        scaleTextIn.setToX(1);
        scaleTextIn.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scaleButtonIn = new ScaleTransition(Duration.seconds(0.25), button);
        scaleButtonIn.setToX(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleButtonIn.setToY(1.5); // Ajustez la valeur pour modifier l'effet d'agrandissement
        scaleButtonIn.setInterpolator(Interpolator.EASE_BOTH);

        pause.setOnFinished(event1 -> {
            texte.setOpacity(0);
            button.setOpacity(0);
            scaleOut.setOnFinished(event -> {
                fromImageView.setOpacity(0);
                toPane.setOpacity(1);
                scaleIn.play();
                scaleTextIn.play();
            });
            scaleOut.play();
        });
        pause.play();
    }

    /**
     * Divise le texte du titre si celui-ci dépasse une certaine longueur.
     *
     * @param texte Le texte à traiter.
     * @param titre Le titre du film.
     */
    private void splitTextIfNeeded(Text texte, String titre) {
        if (titre.length() > 20) {
            int splitIndex = titre.lastIndexOf(" ", 20);
            if (splitIndex != -1) {
                String firstPart = titre.substring(0, splitIndex);
                String secondPart = titre.substring(splitIndex + 1); // Ignorer l'espace
                texte.setText(firstPart + "\n" + secondPart);
            }
        }
    }

    public void displayTiquets(List<Tickets> tiquets) {
        menu.getItems().clear();
        for (Tickets tiquet : tiquets) {

                // Construction du texte du menu avec le titre du film
                String menuText = tiquet.getNumberOfTickets() + "x Tiquet vendu pour le film " + tiquet.getFilmName() + " à la salle " + tiquet.getRoom() + " à " + tiquet.getHour() + " pour un prix de " + tiquet.getPrice() + "€";
                MenuItem menuItem = new MenuItem(menuText);
                menu.getItems().add(menuItem);

        }
    }


}




