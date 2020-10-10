
package uiComponents.discountGui;

import SDM.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.offerGui.offerGuiController;

import java.net.URL;
import java.util.List;

public class discountGuiController {

    @FXML private Label discountNameLabel;
    @FXML private Label itemNameLabel;
    @FXML private Label itemIdLabel;
    @FXML private Label amountLabel;
    @FXML private Label itemPriceLabel;
    @FXML private VBox discountPane;

    //noy 1/10
    private Discount discount;
    private Store storeOfDiscount;

    public void setDiscount(Discount discount) {
        this.discount = discount;
        setDiscountGuiLabels(discount);

    }

    public Store getStoreOfDiscount() {
        return storeOfDiscount;
    }

    public void setStoreOfDiscount(Store storeOfDiscount) {
        this.storeOfDiscount = storeOfDiscount;
    }

    /*
    public void setDiscountNameLabel(String discountName) {
        this.discountNameLabel.setText(discountName);
    }

    public void setItemNameLabel(String itemName) {
        this.itemNameLabel.setText(itemName);
    }

    public void setItemIdLabel(String itemId) {
        this.itemIdLabel.setText(itemId);
    }

    public void setAmountLabel(String amount) {
        this.amountLabel.setText(amount);
    }

    public void setItemPriceLabel(String itemPrice) {
        this.itemPriceLabel.setText(itemPrice);
    }

 */

    void setDiscountGuiLabels(Discount discount)
    {
        discountNameLabel.setText(discount.getName());
        StoreItem storeItem=discount.getIfBuy().getStoreItem();
        itemNameLabel.setText(storeItem.getItem().getName());
        itemIdLabel.setText(String.format("%d",storeItem.getItem().getId()));
        amountLabel.setText((String.format("%.2f",discount.getIfBuy().getQuantity())));
        itemPriceLabel.setText((String.format("%d",storeItem.getPrice())));
        showOffers(discount);
    }



    public void showOffers(Discount discount)
    {
        List<Offer> offerList=discount.getThenGet().getOffers();
        discountPane.getChildren().clear();

        for (Offer offer:offerList)
        {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/offerGui/offerGuiFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node offerUI = loader.load();

            offerGuiController offerController =loader.getController();
            offerController.setStoreOfOffer(storeOfDiscount);
            offerController.setOffer(offer);


            discountPane.getChildren().add(offerUI);

        }
    }


}
