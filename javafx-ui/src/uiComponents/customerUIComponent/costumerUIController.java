package uiComponents.customerUIComponent;

import SDM.Customer;
import SDM.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class costumerUIController {

    @FXML private Label costumerIdLabel;
    @FXML private Label costumerNameLabel;
    @FXML private Label costumerLocationLabel;
    @FXML private Label numOfOrdersLabel;
    @FXML private Label avgPriceOfOrdersLabel;
    @FXML private Label avgPriceOfDeliveriesLabel;

    private Customer customer;


    public void setCostumerIdLabel(String costumerId) {
        this.costumerIdLabel.setText(costumerId);
    }

    public void setCostumerNameLabel(String costumerName) {
        this.costumerNameLabel.setText(costumerName);
    }

    public void setCostumerLocationLabel(String costumerLocation) {
        this.costumerLocationLabel.setText(costumerLocation);
    }

    public void setNumOfOrdersLabel(String numOfOrders) {
        this.numOfOrdersLabel.setText(numOfOrders);
    }

    public void setAvgPriceOfOrdersLabel(String avgPriceOfOrders) {
        this.avgPriceOfOrdersLabel.setText(avgPriceOfOrders);
    }

    public void setAvgPriceOfDeliveriesLabel(String avgPriceOfDeliveries) {
        this.avgPriceOfDeliveriesLabel.setText(avgPriceOfDeliveries);
    }

    public void setCostumerAllLabels(Customer customer) {
        setCostumerIdLabel(String.format("%d", customer.getId()));
        setCostumerNameLabel(customer.getName());
        setCostumerLocationLabel(String.format("x=%d, y=%d", customer.getLocation().getXLocation(), customer.getLocation().getYLocation()));
        setNumOfOrdersLabel(String.format("%d", customer.getNumOfCostumerOrders()));
        setAvgPriceOfOrdersLabel((String.format("%1$,.2f", customer.getPriceOfCostumerOrders())));
        setAvgPriceOfDeliveriesLabel((String.format("%1$,.2f", customer.getPriceOfCostumerOrdersDeliveries())));
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
        setCostumerAllLabels(customer);
    }

}

