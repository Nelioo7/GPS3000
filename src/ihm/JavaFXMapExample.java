package ihm;

import io.github.makbn.jlmap.fx.JLMapView;
import io.github.makbn.jlmap.map.JLMapProvider;
import io.github.makbn.jlmap.JLProperties;
import io.github.makbn.jlmap.model.JLLatLng;
import io.github.makbn.jlmap.model.JLMapOption;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class JavaFXMapExample extends Application {

    @Override
    public void start(Stage stage) {
        // Create a map view
        JLMapView map = JLMapView.builder()
                .jlMapProvider(JLMapProvider.MAP_TILER.parameter(new JLMapOption.Parameter("key", MAP_API_KEY)).build())
                .startCoordinate(JLLatLng.builder()
                        .lat(51.044)
                        .lng(114.07)
                        .build())
                .showZoomController(true)
                .build();

        // Create the scene
        AnchorPane root = new AnchorPane(map);
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Java Leaflet Map (JavaFX)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
