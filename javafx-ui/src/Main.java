import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uiComponents.mainComponent.MainComponentController;


import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //CSSFX.start();

        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource("/uiComponents/mainComponent/mainComponentFXML.fxml");
        loader.setLocation(mainFXML);
        ScrollPane root = loader.load();

        // wire up controller
        MainComponentController histogramController = loader.getController();
        SDMEgine businessLogic = new SDMEgine(histogramController);
        histogramController.setPrimaryStage(primaryStage);
        histogramController.setBusinessLogic(businessLogic);

        // set stage
        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(root, 1050, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
