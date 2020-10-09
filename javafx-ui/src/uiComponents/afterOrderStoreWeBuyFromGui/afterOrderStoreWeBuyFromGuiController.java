package uiComponents.afterOrderStoreWeBuyFromGui;

import SDM.Location;
import SDM.OneStoreOrder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class afterOrderStoreWeBuyFromGuiController {

    @FXML private Label storeIdLabel;
    @FXML private Label nameLabel;
    @FXML private Label locationLabel;
    @FXML private Label distanceLabel;
    @FXML private Label ppkLabel;
    @FXML private Label costDeliveryLabel;
    @FXML private Label numOfAllItemsLabel;
    @FXML private Label costOfAllItemsLabel;


    public void updateLabels(OneStoreOrder oneStoreOrder) {
        storeIdLabel.setText(String.format("%d",oneStoreOrder.getStoreOrderMadeFrom().getId()));
        nameLabel.setText(oneStoreOrder.getStoreOrderMadeFrom().getName());
        locationLabel.setText(String.format("x=%.2f y=%.2f",oneStoreOrder.getStoreOrderMadeFrom().getLocation().getXLocation(), oneStoreOrder.getStoreOrderMadeFrom().getLocation().getYLocation()));
        distanceLabel.setText(String.format("%.2f", Location.distanceBetweenLocations(oneStoreOrder.getStoreOrderMadeFrom().getLocation(),oneStoreOrder.getCustomer().getLocation())));
        ppkLabel.setText(String.format("%.2f",oneStoreOrder.getStoreOrderMadeFrom().getDeliveryPPK()));
        costDeliveryLabel.setText(String.format("%.2f",oneStoreOrder.getDeliveryPrice()));
        numOfAllItemsLabel.setText(String.format("%d",oneStoreOrder.getTotalItemsInOrder()));
        costOfAllItemsLabel.setText(String.format("%.2f",oneStoreOrder.getPriceOfAllItems()));
    }

}
