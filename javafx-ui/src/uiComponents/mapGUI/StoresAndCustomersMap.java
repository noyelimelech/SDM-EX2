package uiComponents.mapGUI;

import SDM.*;
import com.sun.javafx.css.Size;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;

import javax.print.DocFlavor;
import java.util.List;

public class StoresAndCustomersMap {

    private GridPane mainGridPane;
    private SDMEngine sdmEngine;
    private int gridPaneHeight = 800;
    private int gridPaneWidth = 800;
    private int biggestX = 1;
    private int biggestY = 1;

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    public void buildMap() {
        mainGridPane = new GridPane();

        setMainGridPaneProperties();
        findBiggestXAndY();
        setMainGridPaneSize();
        addStoresToMainGridPane(sdmEngine.getAllStores());
        addCustomersToMainGridPane(sdmEngine.getAllCustomers());
    }

    public Node getMap() {
        return mainGridPane;
    }

    private void addCustomersToMainGridPane(List<Customer> allCustomers) {
        double picWidth = (100.0 / biggestX) * gridPaneWidth;
        double picHeight = (100.0 / biggestY) * gridPaneHeight;

        for(Customer customer : allCustomers) {
            ImageView customerImg = createImageView(picWidth, picHeight, getClass().getResource("/uiComponents/Resources/stickMan.png").toString());
            Tooltip customerToolTip = createCustomerToolTip(customer);

            Tooltip.install(customerImg, customerToolTip);

            mainGridPane.add(customerImg, customer.getLocation().getXLocation(), customer.getLocation().getYLocation());
        }
    }

    private Tooltip createCustomerToolTip(Customer customer) {
        Tooltip customerToolTip = new Tooltip();
        customerToolTip.setText(String.format("ID:%d\nName:%s\nTotal Orders Made:%d",customer.getId(),customer.getName(),customer.getNumOfCostumerOrders()));

        return customerToolTip;
    }

    private void addStoresToMainGridPane(List<Store> allStores) {
        double picWidth = gridPaneWidth/biggestX;
        double picHeight = gridPaneHeight/biggestY;

        for(Store store : allStores) {
            ImageView storeImg = createImageView(picWidth, picHeight, getClass().getResource("/uiComponents/Resources/storePic.png").toString());
            Tooltip storeToolTip = createStoreToolTip(store);

            Tooltip.install(storeImg,storeToolTip);

            mainGridPane.add(storeImg, store.getLocation().getXLocation(), store.getLocation().getYLocation());
        }
    }

    private Tooltip createStoreToolTip(Store store) {
        Tooltip storeToolTip = new Tooltip();
        storeToolTip.setText(String.format("ID:%d\nName:%s\nPPK:%d\nTotal Orders:%d",store.getId(),store.getName(),store.getDeliveryPPK(),store.getOrders().size()));

        return storeToolTip;
    }

    private ImageView createImageView(double picWidth, double picHeight, String imageUrl) {
        ImageView storeImg = new ImageView(imageUrl);
        storeImg.setFitWidth(picWidth);
        storeImg.setFitHeight(picHeight);
        storeImg.setPreserveRatio(false);
        return storeImg;
    }

    private void setMainGridPaneSize() {
        for (int i = 0; i < biggestX; i++) {
            ColumnConstraints column = new ColumnConstraints(gridPaneWidth/biggestX);
            //column.setPercentWidth(100.0 / biggestX);
            mainGridPane.getColumnConstraints().add(column);
        }
        for (int i = 0; i < biggestY; i++) {
            RowConstraints row = new RowConstraints(gridPaneHeight/biggestY);
            //row.setPercentHeight(100.0 / biggestY);
            mainGridPane.getRowConstraints().add(row);
        }
    }

    private void setMainGridPaneProperties() {
        mainGridPane.setPrefSize(gridPaneWidth,gridPaneHeight);
        //mainGridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        mainGridPane.getStylesheets().add(getClass().getResource("mapCSS.css").toString());
        mainGridPane.getStyleClass().add("mainMap");
        mainGridPane.setGridLinesVisible(true);
        //mainGridPane.setStyle("-fx-border-width: 4px; -fx-border-color: black");
    }

    private void findBiggestXAndY() {
        for(Store store : sdmEngine.getAllStores()) {
            checkIfLocatedBigger(store);
        }

        for(Customer customer : sdmEngine.getAllCustomers()) {
            checkIfLocatedBigger(customer);
        }

        ++biggestX;
        ++biggestY;
    }

    private void checkIfLocatedBigger(Locatable locatable) {
        if(locatable.getLocation().getXLocation() > biggestX) {
            biggestX = locatable.getLocation().getXLocation();
        }
        if(locatable.getLocation().getYLocation() > biggestY) {
            biggestY = locatable.getLocation().getYLocation();
        }
    }


}
