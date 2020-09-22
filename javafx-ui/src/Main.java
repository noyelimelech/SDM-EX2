import SDM.SDMEngine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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
        Parent root = loader.load();

        // wire up controller
        MainComponentController mainComponentController = loader.getController();
        SDMEngine sdmEngine = new SDMEngine(/*MainComponentController -- Model doesnt know controller*/);
        mainComponentController.setPrimaryStage(primaryStage);
        mainComponentController.setSdmEngine(sdmEngine);

        // set stage
        primaryStage.setTitle("Super Duper Market");
        Scene scene = new Scene(root, 1060, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
