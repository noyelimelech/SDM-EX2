package uiComponents.discountInOrderGUI;

import SDM.Discount;
import SDM.Exception.NegativeAmountOfItemInException;
import SDM.Offer;
import SDM.SDMEngine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uiComponents.FXMLLoaderProxy;
import uiComponents.offerGui.offerGuiController;

import java.net.URL;

public class DiscountInOrderController {


    @FXML private GridPane discountHolder;
    @FXML private FlowPane offerPlaceHolder;

    @FXML private Button confirmButton;
    @FXML private Label discountLabelName;
    @FXML private Label discountTypeLabel;
    @FXML private ComboBox<Offer> oneOfComboBox;

    private Discount discount;
    private SDMEngine sdmEngine;
    private VBox discountsPlaceHolder;
    private Node nodeOfDiscount;

    public void setDiscountsPlaceHolder(VBox discountsPlaceHolder) {
        this.discountsPlaceHolder = discountsPlaceHolder;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
        fillGUIControlsWithData();
    }

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    private void fillGUIControlsWithData() {
        discountLabelName.setText(discount.getName());
        discountTypeLabel.setText(discount.getThenGet().getOperator());
        fillPaneWithOffers();
        if(discount.getThenGet().getOperator().equals("ONE-OF")) {
            confirmButton.disableProperty().set(true);
            oneOfComboBox.visibleProperty().set(true);
            oneOfComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null) {
                    confirmButton.disableProperty().set(false);
                }
            });
            fillComboBoxWithOffers();
        }
    }

    private void fillComboBoxWithOffers() {
        oneOfComboBox.getItems().addAll(discount.getThenGet().getOffers());
    }

    private void fillPaneWithOffers() {
        for(Offer offer : discount.getThenGet().getOffers()) {
            FXMLLoaderProxy loader = new FXMLLoaderProxy();
            URL fxmlLocation = getClass().getResource("/uiComponents/offerGui/offerGuiFXML.fxml");
            loader.setLocation(fxmlLocation);

            Node offerUI = loader.load();

            offerGuiController discountGuiController=loader.getController();
            discountGuiController.setOffer(offer);

            offerPlaceHolder.getChildren().add(offerUI);
        }
    }

    @FXML
    void confirmButtonAction() throws NegativeAmountOfItemInException {
        if(discount.getThenGet().getOperator().equals("ONE-OF")) {
            sdmEngine.useDiscountOfCurrentOrder(discount, oneOfComboBox.getSelectionModel().getSelectedItem());
        }
        else {
            sdmEngine.useDiscountOfCurrentOrder(discount);
        }

        makeThisDisapear();
        Alert discountAddedAlert = new Alert(Alert.AlertType.INFORMATION);
        discountAddedAlert.setTitle("Discount added!");
        discountAddedAlert.setHeaderText("Discount " + discount.getName() + " had been added to your cart!");
        discountAddedAlert.show();
    }

    private void makeThisDisapear() {
        if(discountsPlaceHolder != null && nodeOfDiscount != null) {
            discountsPlaceHolder.getChildren().remove(nodeOfDiscount);
        }
        else {
            discountHolder.setVisible(false);
        }
    }

    public void setNodeOfDiscount(Node nodeOfDiscount) {
        this.nodeOfDiscount = nodeOfDiscount;
    }

    public Node getNodeOfDiscount() {
        return nodeOfDiscount;
    }
}
