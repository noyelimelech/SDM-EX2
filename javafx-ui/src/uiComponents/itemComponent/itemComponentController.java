package uiComponents.itemComponent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class itemComponentController {

    @FXML
    private Label itemIdLabel;

    public void setItemIdLabel(String itemId) {
        itemIdLabel.setText(itemId);
    }



}