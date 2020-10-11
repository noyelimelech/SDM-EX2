package uiComponents.FinalDetailsOnItemsFromStore;

import SDM.OrderItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FinalDetailsOnItemsFromStoreController {



    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label typeLabel;
    @FXML private Label amountLabel;
    @FXML private Label priceLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label regularOrSalePriceLabel;

    @FXML private GridPane gridPaneItem;


    //TODO
    public void updateLabels(OrderItem orderItem) {
        idLabel.setText(String.format("%d",orderItem.getItemInOrder().getItem().getId()));
        nameLabel.setText(orderItem.getItemInOrder().getItem().getName());
        typeLabel.setText(orderItem.getItemInOrder().getItem().getType().toString());
        amountLabel.setText(String.format("%.2f",orderItem.getAmount()));
        priceLabel.setText(String.format("%d",orderItem.getPricePaid()));
        totalPriceLabel.setText(String.format("%.2f",orderItem.getTotalPrice()));

        if (orderItem.isBoughtInDiscount())
        {

            regularOrSalePriceLabel.setText("sale price");
        }
        else
            regularOrSalePriceLabel.setText("regular price");

    }
}
