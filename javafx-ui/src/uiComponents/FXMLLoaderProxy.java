package uiComponents;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;

import java.io.IOException;

public class FXMLLoaderProxy extends FXMLLoader{

    public <T> T load() {
        T node = null;

        try {
            node = super.load();
        } catch (IOException e) {
            Alert loadingAlert = new Alert(Alert.AlertType.ERROR);
            loadingAlert.setTitle("FXML Loading ERROR");
            loadingAlert.setHeaderText("Seems like we got a problem on our error loading... Nothing you can do...");
            loadingAlert.show();
        }

        return node;
    }

}
