
package uiComponents.offerGui;

import SDM.Offer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class offerGuiController {

    @FXML private Label offerIdLabel;
    @FXML private Label offerNameLabel;
    @FXML private Label amountLabel;
    @FXML private Label additionalPriceLabel;


/*

    public void setOfferIdLabel(String offerIdLabel) {
        this.offerIdLabel.setText(offerIdLabel);
    }

    public void setOfferNameLabel(String offerNameLabel) {
        this.offerNameLabel.setText(offerNameLabel);
    }

    public void setAmountLabel(String amountLabel) {
        this.amountLabel.setText(amountLabel);
    }

    public void setAdditionalPriceLabel(String additionalPriceLabel) {
        this.additionalPriceLabel.setText(additionalPriceLabel);
    }


 */
    void setOfferLabels(Offer offer)
    {
        offerIdLabel.setText((String.format("%d",offer.getItemId())));
        //setOfferNameLabel(offer.get);
        amountLabel.setText(String.format("%.2f", offer.getAmount()));
        additionalPriceLabel.setText(String.format("%d", offer.getForAdditionalPrice()));



    }


    public void setOffer(Offer offer)
    {
        setOfferLabels(offer);
    }
}