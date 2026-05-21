package ihm;

import java.io.FileInputStream;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
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
 * @version 1.2.0
 */
public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane(); // le conteneur qui englobe tout

        VBox leftPanel = new VBox(); // le panneau de gauche
        leftPanel.setAlignment(Pos.CENTER);

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
        switchArrows.setPrefSize(26, 26);
        ImageView switchIcon = new ImageView(new Image(new FileInputStream("src/ihm/img/switch.png")));
        switchIcon.setFitHeight(26);
        switchIcon.setPreserveRatio(true);
        switchArrows.setGraphic(switchIcon);
        switchArrows.setRotate(90);

        TextField destinationSearch = new TextField("Ville de destination...");
        startSearch.setPadding(new Insets(7));

        leftPanel.getChildren().addAll(
            startSearch,
            switchArrows,
            destinationSearch
        );

        VBox.setMargin(switchArrows, new Insets(10, 0, 10, 0));

        TabPane rightPanel = new TabPane(); // la partie de droite qui contient les onglets

        Tab itineraires = new Tab();
        itineraires.setContent(null);
        itineraires.setClosable(false);

        ImageView pathsIcon = new ImageView(new Image(new FileInputStream("src/ihm/img/route.png")));
        pathsIcon.setFitHeight(30);
        pathsIcon.setPreserveRatio(true);
        itineraires.setGraphic(pathsIcon);

        Tab favoris = new Tab();
        favoris.setContent(null);
        favoris.setClosable(false);

        ImageView favoritesIcon = new ImageView(new Image(new FileInputStream("src/ihm/img/favorite.png")));
        favoritesIcon.setFitHeight(30);
        favoritesIcon.setPreserveRatio(true);
        favoris.setGraphic(favoritesIcon);

        Tab historique = new Tab();
        historique.setContent(null);
        historique.setClosable(false);

        ImageView historyIcon = new ImageView(new Image(new FileInputStream("src/ihm/img/history.png")));
        historyIcon.setFitHeight(30);
        historyIcon.setPreserveRatio(true);
        historique.setGraphic(historyIcon);

        rightPanel.getTabs().addAll(
            itineraires,
            favoris,
            historique
        );

        rightPanel.setSide(Side.LEFT);

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
