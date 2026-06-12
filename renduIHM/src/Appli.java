import java.io.File;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Interface graphique de l'app
 * @author Erwann Besegher
 * @author Jérôme Pardoue
 * @author Alaeddine Oudriss
 * @author Amayas Mehmel
 * @version 1.4.1
 */
public class Appli extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // ==============================< Racine >==============================
                VBox favorisList = new VBox(); {
                favorisList.setSpacing(10);
                favorisList.setPadding(new Insets(15));
                }

                VBox historiqueList = new VBox(); {
                historiqueList.setSpacing(10);
                historiqueList.setPadding(new Insets(15));
                }
                
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
                            new FileInputStream("renduIHM/img/switch.png")
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


// ==========================< Bouton sauvegarde >==============================

                Button saveBtn = new Button();
                saveBtn.getStyleClass().add("save-btn"); 

                Image imgEmpty = new Image("file:renduIHM/img/signet.png", 28, 28, true, true);
                Image imgFilled = new Image("file:renduIHM/img/signet1.png", 28, 28, true, true);

                //on met l'image non selectionné par défaut
                ImageView saveIcon = new ImageView(imgEmpty);
                saveBtn.setGraphic(saveIcon);

                //on vérifie quelle image est affichée et on inverse quand on click dessus
                saveBtn.setOnAction(event -> {
                    if (startSearch.getText().isEmpty() || destinationSearch.getText().isEmpty()) {
                        return;
                    }

                    if (saveIcon.getImage() == imgEmpty) {

                        String texteTrajet = startSearch.getText() + "  →  " + destinationSearch.getText();

                        // on vérifie si le trajet existe déjà avant de l'ajouter
                        boolean dejaPresent = false;
                        for (javafx.scene.Node n : favorisList.getChildren()) {
                            HBox ligne = (HBox) n;
                            Label l = (Label) ligne.getChildren().get(0);
                            if (l.getText().equals(texteTrajet)) {
                                dejaPresent = true;
                            }
                        }

                        if (!dejaPresent) {

                            Label trajet = new Label(texteTrajet);
                            trajet.getStyleClass().add("favori-item");
                            Button deleteBtn = new Button(); {
                                deleteBtn.getStyleClass().add("delete-btn");
                                
                                Image imgDelete = new Image("file:renduIHM/img/signet1.png", 20, 20, true, true);
                                ImageView deleteIcon = new ImageView(imgDelete);
                                deleteBtn.setGraphic(deleteIcon);
                            }

                            HBox ligne = new HBox(trajet, deleteBtn);
                            ligne.setSpacing(10);
                            ligne.setAlignment(Pos.CENTER_LEFT);

                            deleteBtn.setOnAction(e -> {
                                favorisList.getChildren().remove(ligne);
                            });

                            favorisList.getChildren().add(ligne);
                        }
                        saveIcon.setImage(imgFilled);
                    } else {
                        saveIcon.setImage(imgEmpty);
                    }
                });

                startSearch.textProperty().addListener((obs, oldVal, newVal) -> {
                    saveIcon.setImage(imgEmpty);
                });

                destinationSearch.textProperty().addListener((obs, oldVal, newVal) -> {
                    saveIcon.setImage(imgEmpty); 
                });

                // ==========================< Bouton recherche >==============================

                Button rechercheBtn = new Button();
                rechercheBtn.getStyleClass().add("recherche-btn");

                Image imgRecherche = new Image("file:renduIHM/img/search.png", 28, 28, true, true);
                ImageView rechercheIcon = new ImageView(imgRecherche);
                rechercheBtn.setGraphic(rechercheIcon);

                rechercheBtn.setOnAction(event -> {
                    if (startSearch.getText().isEmpty() || destinationSearch.getText().isEmpty()) {
                        return;
                    }

                    String texteTrajet = startSearch.getText() + "  →  " + destinationSearch.getText();

                    // on cherche si le trajet existe déjà dans l'historique
                    HBox ligneExistante = null;
                    for (javafx.scene.Node n : historiqueList.getChildren()) {
                        HBox ligne = (HBox) n;
                        Label l = (Label) ligne.getChildren().get(0);
                        if (l.getText().equals(texteTrajet)) {
                            ligneExistante = ligne;
                        }
                    }

                    // si présent, on l'enlève pour le remettre en haut après
                    if (ligneExistante != null) {
                        historiqueList.getChildren().remove(ligneExistante);
                    } else {
                        Label trajet = new Label(texteTrajet);
                        trajet.getStyleClass().add("historique-item");

                        Button deleteBtn = new Button();
                        deleteBtn.getStyleClass().add("delete-btn");

                        Image imgDelete = new Image("file:renduIHM/img/supprimer.png", 20, 20, true, true);
                        ImageView deleteIcon = new ImageView(imgDelete);
                        deleteBtn.setGraphic(deleteIcon);

                        HBox ligne = new HBox(trajet, deleteBtn);
                        ligne.setSpacing(10);
                        ligne.setAlignment(Pos.CENTER_LEFT);

                        deleteBtn.setOnAction(e -> {
                            historiqueList.getChildren().remove(ligne);
                        });

                        ligneExistante = ligne;
                    }
                    // on remet la dernière recherche en haut
                    historiqueList.getChildren().add(0, ligneExistante);
                });

                

                javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
                HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

                HBox saveBtnContainer = new HBox(saveBtn, spacer, rechercheBtn);
                saveBtnContainer.setAlignment(Pos.CENTER);
                
                
                // Menu pour choisir les modalités de transport à afficher
                HBox filterBar = new HBox(); {
                    filterBar.getStyleClass().add("filter-bar");

                    filterBar.setAlignment(Pos.CENTER);
                    filterBar.setSpacing(30);
                    filterBar.setPadding(new Insets(10, 0, 10, 0));

                    Button boutonTrain;
                    Button boutonBus;
                    Button boutonAvion;

                    boolean[] transportSelected = new boolean[]{false, false, false};

                    boutonTrain = new Button(); {
                        boutonTrain.getStyleClass().add("filter-btn");
                        ImageView trainIcon = new ImageView(
                            new Image(
                                new FileInputStream("renduIHM/img/train.png")
                            )
                        );{
                            trainIcon.setFitHeight(20); 
                            trainIcon.setPreserveRatio(true);
                            boutonTrain.setGraphic(trainIcon);
                        }
                    }
                    boutonBus = new Button(); {
                        boutonBus.getStyleClass().add("filter-btn");
                        ImageView busIcon = new ImageView(
                            new Image(
                                new FileInputStream("renduIHM/img/autobus.png")
                            )
                        );{
                            busIcon.setFitHeight(20); 
                            busIcon.setPreserveRatio(true);
                            boutonBus.setGraphic(busIcon);
                        }
                    }
                    boutonAvion = new Button(); {
                        boutonAvion.getStyleClass().add("filter-btn");
                        ImageView avionIcon = new ImageView(
                            new Image(
                                new FileInputStream("renduIHM/img/avion.png")
                            )
                        );{
                            avionIcon.setFitHeight(20); 
                            avionIcon.setPreserveRatio(true);
                            boutonAvion.setGraphic(avionIcon);
                        }
                    }

                    boutonTrain.setOnAction(event -> {
                        if (boutonTrain.getStyleClass().contains("filter-btn-selected")) {
                            boutonTrain.getStyleClass().remove("filter-btn-selected");
                            transportSelected[0] = false;
                        } else {
                            boutonTrain.getStyleClass().add("filter-btn-selected");
                            boutonBus.getStyleClass().remove("filter-btn-selected");
                            boutonAvion.getStyleClass().remove("filter-btn-selected");
                            transportSelected[0] = true;
                            transportSelected[1] = false;
                            transportSelected[2] = false;
                        }
                    });

                    boutonBus.setOnAction(event -> {
                        if (boutonBus.getStyleClass().contains("filter-btn-selected")) {
                            boutonBus.getStyleClass().remove("filter-btn-selected");
                            transportSelected[1] = false;
                        } else {
                            boutonBus.getStyleClass().add("filter-btn-selected");
                            boutonTrain.getStyleClass().remove("filter-btn-selected");
                            boutonAvion.getStyleClass().remove("filter-btn-selected");
                            transportSelected[0] = false;
                            transportSelected[1] = true;
                            transportSelected[2] = false;
                        }
                    });

                    boutonAvion.setOnAction(event -> {
                        if (boutonAvion.getStyleClass().contains("filter-btn-selected")) {
                            boutonAvion.getStyleClass().remove("filter-btn-selected");
                            transportSelected[2] = false;
                        } else {
                            boutonAvion.getStyleClass().add("filter-btn-selected");
                            boutonTrain.getStyleClass().remove("filter-btn-selected");
                            boutonBus.getStyleClass().remove("filter-btn-selected");
                            transportSelected[0] = false;
                            transportSelected[1] = false;
                            transportSelected[2] = true;
                        }
                    });

                    filterBar.getChildren().addAll(
                        boutonTrain, 
                        boutonBus, 
                        boutonAvion
                    );
                }

                // on ajoute les items dans le panneau
                leftPanel.getChildren().addAll(
                    co2Banner,
                    startSearch,
                    switchArrows,
                    destinationSearch,
                    saveBtnContainer,
                    filterBar
                );
            }

            // ==============================< Panneau de Droite >==============================
            TabPane rightPanel = new TabPane(); {
                rightPanel.setId("right-panel"); 

                // ------------------------------< Tab itinéraires >------------------------------
                Tab itineraires = new Tab(); {

                    itineraires.setClosable(false);

                    // ---< Carte >---
                    WebView map = new WebView(); {
                        WebEngine moteur = map.getEngine(); // instanciation du moteur de la webview
                        moteur.load(new File("renduIHM/src/map.html").toURI().toString()); // chargement de la page web
                        
                        map.prefHeightProperty().bind(rightPanel.heightProperty());
                    }

                    // on ajoute la map dans la tab
                    itineraires.setContent(map);

                    ImageView pathsIcon = new ImageView(
                        new Image(
                            new FileInputStream("renduIHM/img/route.png"), 30, 30, true, true
                        )
                    );

                    itineraires.setGraphic(pathsIcon);
                }

                // ------------------------------< Tab favoris >------------------------------
                Tab favoris = new Tab(); {

                    favoris.setClosable(false);
                    favoris.setContent(favorisList);

                    ImageView favoritesIcon = new ImageView(
                        new Image(
                            new FileInputStream("renduIHM/img/favorite.png"), 30, 30, true, true
                        )
                    );

                    favoris.setGraphic(favoritesIcon);
                }

                // ------------------------------< Tab historique >------------------------------
                Tab historique = new Tab(); {

                    historique.setClosable(false);
                    historique.setContent(historiqueList);

                    ImageView historyIcon = new ImageView(
                        new Image(
                            new FileInputStream("renduIHM/img/history.png"), 30, 30, true, true
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

        Scene home = new Scene(root, 1000, 600);
        home.getStylesheets().add(new File("renduIHM/src/style.css").toURI().toString()); 

        stage.setScene(home);
        stage.setTitle("GPS 3000");
        stage.setFullScreen(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch();
    }
}