package uiComponents.FinalDetailsOnStoreAndItemsFromStore;

import SDM.OneStoreOrder;
import SDM.OrderItem;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.FinalDetailsOnItemsFromStore.FinalDetailsOnItemsFromStoreController;

import java.net.URL;
import java.util.Map;

public class FinalDetailsOnStoreAndItemsFromStoreController {

    @FXML private VBox dynamicVBoxItemsInStore;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label ppkLabel;
    @FXML private Label distanceLabel;
    @FXML private Label costOfDeliveryLabel;


    public void setStoreLabelsAndSetItems(OneStoreOrder oneStoreOrder)
    {
        setStoreLabels(oneStoreOrder);
        setItemsInStoreOrder(oneStoreOrder);
    }



    private void setStoreLabels(OneStoreOrder oneStoreOrder) {
        idLabel.setText(String.format("%d",oneStoreOrder.getStoreOrderMadeFrom().getId()));
        nameLabel.setText(oneStoreOrder.getStoreOrderMadeFrom().getName());
        ppkLabel.setText((String.format("%d",oneStoreOrder.getStoreOrderMadeFrom().getDeliveryPPK())));
        distanceLabel.setText((String.format("%.2f",oneStoreOrder.distanceBetweenCostumerAndStore())));
        costOfDeliveryLabel.setText((String.format("%.2f",oneStoreOrder.getDeliveryPrice())));
    }




    private void setItemsInStoreOrder(OneStoreOrder oneStoreOrder) {
        Map<Integer, OrderItem> itemsInStoreOrder= oneStoreOrder.getOrderItemCart();

        for (OrderItem orderItem:itemsInStoreOrder.values())
        {

            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/FinalDetailsOnItemsFromStore/FinalDetailsOnItemsFromStoreFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node finalDetailsOnItemsFromStore = loader.load();
            FinalDetailsOnItemsFromStoreController finalDetailsOnItemsFromStoreController = loader.getController();

            finalDetailsOnItemsFromStoreController.updateLabels(orderItem);
            dynamicVBoxItemsInStore.getChildren().add(finalDetailsOnItemsFromStore);

        }


        //לעבור איטם איטם ולהכניס לדינמיק ויבוקס
    }













}
