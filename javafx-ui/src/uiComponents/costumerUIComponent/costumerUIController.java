package uiComponents.costumerUIComponent;

import SDM.Costumer;
import SDM.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class costumerUIController {

    @FXML private Label costumerIdLabel;
    @FXML private Label costumerNameLabel;
    @FXML private Label costumerLocationLabel;
    @FXML private Label numOfOrdersLabel;
    @FXML private Label avgPriceOfOrdersLabel;
    @FXML private Label avgPriceOfDeliveriesLabel;

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

    public void setCostumerAllLabels(Costumer costumer) {
        setCostumerIdLabel(toString().format("%d", costumer.getId()));
        setCostumerNameLabel(costumer.getName());
        setCostumerLocationLabel(costumer.getLocation().toString());
        setNumOfOrdersLabel(toString().format("%d", costumer.getNumOfCostumerOrders()));
        setAvgPriceOfOrdersLabel((toString().format("%1$,.2f", costumer.getPriceOfCostumerOrders())));
        setAvgPriceOfDeliveriesLabel((toString().format("%1$,.2f", costumer.getPriceOfCostumerOrdersDeliveries())));
    }
}

