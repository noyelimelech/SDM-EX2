package uiComponents.staticOrderGUI;

import SDM.Customer;
import SDM.SDMEngine;
import SDM.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.List;

public class staticOrderGUIController {

    @FXML private ComboBox<String> chooseStoreComboBox;
    @FXML private Label ppkLabel;
    @FXML private Button continueButton;

    SDMEngine sdmEngine;
    FlowPane dynamicAreaFlowPane;

    @FXML
    void continueButtonAction() {
        //move to DANIEL GUI
    }



    public void setStoreComboBox() {

        List<Store> allStores= sdmEngine.getAllStores();
        for (Store st: allStores) {
            chooseStoreComboBox.getItems().add(
                    String.format("Id: %d      Name: %s      location: x=%d, y=%d", st.getId(),st.getName(),st.getLocation().getXLocation(),st.getLocation().getYLocation()));
        }




    }


    public void setSDMEngine(SDMEngine sdmEngine) {
        this.sdmEngine=sdmEngine;
    }

    public void setDynamicAreaFlowPane(FlowPane dynamicAreaFlowPane) {
        this.dynamicAreaFlowPane=dynamicAreaFlowPane;
    }
}
