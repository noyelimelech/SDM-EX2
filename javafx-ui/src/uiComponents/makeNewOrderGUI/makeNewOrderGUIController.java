package uiComponents.makeNewOrderGUI;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class makeNewOrderGUIController {

    @FXML
    private ComboBox<String> chooseCustomerComboBox;

    @FXML
    private ComboBox<?> chooseTypeOfOrderButton;

    @FXML
    void chooseCustomerComboBoxAction() {
        }

    @FXML
    void chooseTypeOfOrderButtonAction() {

    }

    public void setCustomerComboBoxes() {

        chooseCustomerComboBox.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );

    }
}










