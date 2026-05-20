package ihm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Interface graphique de l'app
 * @author Erwann Besegher
 * @version 1.1.0
 */
public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane(); // le conteneur qui englobe tout

        VBox leftPanel = new VBox(); // le panneau de gauche

        leftPanel.setBackground(
            new Background(
                new BackgroundFill(
                    Color.LIGHTGRAY, // gris clair pour le fond
                    new CornerRadii(0), // on met les rayons des coins à 0 pour qu'ils restent carrés
                    Insets.EMPTY // on ne met pas d'espace
                )
            )
        );

        leftPanel.setPadding(new Insets(15));

        TextField startSearch = new TextField("Ville de départ...");
        startSearch.setPadding(new Insets(7));

        Button switchArrows = new Button();
        switchArrows.setGraphic(
            new ImageView(
                new Image("") // TODO ajouter le chemin
            )
        );

        TextField destinationSearch = new TextField("Ville de destination...");
        startSearch.setPadding(new Insets(7));

        leftPanel.getChildren().addAll(
            startSearch,
            destinationSearch
        );

        TabPane rightPanel = new TabPane(); // la partie de droite qui contient les onglets

        root.setLeft(leftPanel);
        root.setRight(rightPanel);

        Scene home = new Scene(root);

        stage.setScene(home);
        stage.setTitle("GPS 3000");
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("[échap] pour quitter le mode plein écran");
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
}
