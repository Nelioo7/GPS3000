package ihm;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Interface graphique de l'app
 * @author Erwann Besegher
 * @version 1.4.1
 */
public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // ==============================< Racine >==============================

        BorderPane root = new BorderPane(); {

            // ==============================< Panneau de gauche >==============================
            VBox leftPanel = new VBox(); {

                leftPanel.setId("left-panel"); // nom pour le css

                leftPanel.setAlignment(Pos.TOP_CENTER);
                leftPanel.setPadding(new Insets(30, 15, 15, 15)); // on ajoute un padding pour rendre l'interface plus agéable
                leftPanel.setSpacing(15); 

                HBox co2Banner = new HBox(); {
                    co2Banner.getStyleClass().add("co2-banner");

                    co2Banner.setAlignment(Pos.CENTER);
                    co2Banner.setSpacing(10);

                    Text lampIcon = new Text("💡");
                    lampIcon.setId("lamp-icon"); 

                    Label co2Text = new Label("Pensez à privilégier des moyens de transport minimisant l'émission de CO2");
                    co2Text.getStyleClass().add("co2-text");
                    co2Text.setWrapText(true);
                    co2Text.setPrefWidth(220);

                    co2Banner.getChildren().addAll(lampIcon, co2Text);
                }

                // champ de recherche de la ville de départ
                TextField startSearch = new TextField(); {
                    startSearch.setPromptText("Départ: ex. Lambersart"); 
                    startSearch.getStyleClass().add("search-bar"); 
                }
                
                // champ de recherche de la ville d'arrivée
                TextField destinationSearch = new TextField(); {
                    destinationSearch.setPromptText("Arrivée: ex. Croix");
                    destinationSearch.getStyleClass().add("search-bar"); 
                }

                // bouton qui permet d'inverser la ville de départ et celle de destination
                Button switchArrows = new Button(); {

                    switchArrows.getStyleClass().add("switch-btn"); 

                    ImageView switchIcon = new ImageView(
                        new Image(
                            new FileInputStream("src/ihm/img/switch.png")
                        )
                    ); {
                        switchIcon.setFitHeight(20); 
                        switchIcon.setPreserveRatio(true);
                        switchArrows.setGraphic(switchIcon);
                        switchArrows.setRotate(90);
                    }

                    switchArrows.setOnAction(event -> {
                        String temp = startSearch.getText();
                        startSearch.setText(destinationSearch.getText());
                        destinationSearch.setText(temp);
                    });
                }

                // on ajoute les items dans le panneau
                leftPanel.getChildren().addAll(
                    co2Banner,
                    startSearch,
                    switchArrows,
                    destinationSearch
                );
                
                VBox.setMargin(co2Banner, new Insets(0, 0, 30, 0));
            }

            // ==============================< Panneau de Droite >==============================
            TabPane rightPanel = new TabPane(); {
                rightPanel.setId("right-panel"); 

                // ------------------------------< Tab itinéraires >------------------------------
                Tab itineraires = new Tab(); {

                    itineraires.setClosable(false);
                    itineraires.setContent(null);

                    ImageView pathsIcon = new ImageView(
                        new Image(
                            new FileInputStream("src/ihm/img/route.png"), 30, 30, true, true
                        )
                    );

                    itineraires.setGraphic(pathsIcon);
                }

                // ------------------------------< Tab favoris >------------------------------
                Tab favoris = new Tab(); {

                    favoris.setClosable(false);
                    itineraires.setContent(null);

                    ImageView favoritesIcon = new ImageView(
                        new Image(
                            new FileInputStream("src/ihm/img/favorite.png"), 30, 30, true, true
                        )
                    );

                    favoris.setGraphic(favoritesIcon);
                }

                // ------------------------------< Tab historique >------------------------------
                Tab historique = new Tab(); {

                    historique.setClosable(false);
                    itineraires.setContent(null);

                    ImageView historyIcon = new ImageView(
                        new Image(
                            new FileInputStream("src/ihm/img/history.png"), 30, 30, true, true
                        )
                    );

                    historique.setGraphic(historyIcon);
                }

                rightPanel.getTabs().addAll(itineraires, favoris, historique);
                rightPanel.setSide(Side.LEFT);

                // forcer chaque onglet à prendre exactement 1/3 de la hauteur totale.
                // -10 pour compenser les marges et éviter les débordements.
                rightPanel.tabMinWidthProperty().bind(rightPanel.heightProperty().divide(3).subtract(35));
                rightPanel.tabMaxWidthProperty().bind(rightPanel.heightProperty().divide(3).subtract(35));
            }

            root.setLeft(leftPanel);
            root.setCenter(rightPanel);
        }

        // ==============================< Gestion de la stage >==============================

        Scene home = new Scene(root);
        home.getStylesheets().add("/ihm/style.css"); 

        stage.setScene(home);
        stage.setTitle("GPS 3000");
        stage.setFullScreen(true);
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
}