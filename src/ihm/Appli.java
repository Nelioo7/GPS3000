package ihm;

import java.io.FileInputStream;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Interface graphique de l'app
 * @author Erwann Besegher
 * @version 1.3.3
 */
public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // ==============================< Racine >==============================
        
        BorderPane root = new BorderPane(); {

            // ==============================< Panneau de gauche >==============================

            VBox leftPanel = new VBox(); {

                leftPanel.setId("left-panel"); // on ajoute un id afin de cibler le panneau précisément dans le css

                leftPanel.setAlignment(Pos.CENTER);

                leftPanel.setPadding(new Insets(15)); // on ajoute un padding pour rendre l'interface plus agéable

                // champ de recherche de la ville de départ
                TextField startSearch = new TextField("Ville de départ..."); {
                    startSearch.setPadding(new Insets(7));
                }
                
                // bouton qui permet d'inverser la ville de départ et celle de destination
                Button switchArrows = new Button(); {
                    switchArrows.setPrefSize(26, 26);
                }
                
                // on ajoute une icone
                ImageView switchIcon = new ImageView(
                    new Image(
                        new FileInputStream("src/ihm/img/switch.png")
                    )
                ); {
                    switchIcon.setFitHeight(26);
                    switchIcon.setPreserveRatio(true);
                    switchArrows.setGraphic(switchIcon);
                    switchArrows.setRotate(90);
                }
                

                // champ de recherche de la ville d'arrivée
                TextField destinationSearch = new TextField("Ville de destination..."); {
                    startSearch.setPadding(new Insets(7));
                }

                // on ajoute les items dans le panneau
                leftPanel.getChildren().addAll(
                    startSearch,
                    switchArrows,
                    destinationSearch
                );

                VBox.setMargin(switchArrows, new Insets(10, 0, 10, 0));
            }


            // ==============================< Panneau de Droite >==============================

            TabPane rightPanel = new TabPane(); {

                rightPanel.setId("right-panel"); // ajout de l'id pour cibler dans le css

                // ------------------------------< Tab itinéraires >------------------------------

                // création de la tab itinéraires
                Tab itineraires = new Tab(); {
                    itineraires.setContent(null);
                    itineraires.setClosable(false);
                }
                
                // ajout de l'icone sur la tab itinéraires
                ImageView pathsIcon = new ImageView(
                    new Image(
                        new FileInputStream("src/ihm/img/route.png")
                    )
                ); {
                    pathsIcon.setFitHeight(30);
                    pathsIcon.setPreserveRatio(true);
                    itineraires.setGraphic(pathsIcon);
                }


                // ------------------------------< Tab favoris >------------------------------

                // création de la tab favoris
                Tab favoris = new Tab(); {
                    favoris.setContent(null);
                    favoris.setClosable(false);
                }

                // ajout de l'icone sur la tab favoris
                ImageView favoritesIcon = new ImageView(
                    new Image(
                        new FileInputStream("src/ihm/img/favorite.png")
                    )
                ); {
                    favoritesIcon.setFitHeight(30);
                    favoritesIcon.setPreserveRatio(true);
                    favoris.setGraphic(favoritesIcon);
                }


                // ------------------------------< Tab historique >------------------------------

                // création de la tab historique
                Tab historique = new Tab(); {
                    historique.setContent(null);
                    historique.setClosable(false);
                }
                
                // ajout de l'icone sur la tab historique
                ImageView historyIcon = new ImageView(
                    new Image(
                        new FileInputStream("src/ihm/img/history.png")
                    )
                ); {
                    historyIcon.setFitHeight(30);
                    historyIcon.setPreserveRatio(true);
                    historique.setGraphic(historyIcon);
                }


                // ajout des tabs dans le panel
                rightPanel.getTabs().addAll(
                    itineraires,
                    favoris,
                    historique
                );

                // on met l'orientation des onglets
                rightPanel.setSide(Side.LEFT);

                rightPanel.setTabMaxHeight(Double.MAX_VALUE);
                rightPanel.setMinHeight(30);
            }

            // on ajoute les deux panneaux dans la racine
            root.setLeft(leftPanel);
            root.setCenter(rightPanel);
        }


        // ==============================< Gestion de la stage >==============================

        Scene home = new Scene(root);
        home.getStylesheets().add("/ihm/style.css"); // ajout du fichier css

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
