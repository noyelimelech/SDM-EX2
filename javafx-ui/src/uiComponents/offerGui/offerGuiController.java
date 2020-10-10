
package uiComponents.offerGui;

import SDM.Offer;
import SDM.SDMEngine;
import SDM.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class offerGuiController {

    @FXML private Label offerIdLabel;
    @FXML private Label offerNameLabel;
    @FXML private Label amountLabel;
    @FXML private Label additionalPriceLabel;

    private Store storeOfOffer;

    public Store getStoreOfOffer() {
        return storeOfOffer;
    }

    public void setStoreOfOffer(Store storeOfOffer) {
        this.storeOfOffer = storeOfOffer;
    }

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
        offerNameLabel.setText(storeOfOffer.getItemsThatSellInThisStore().get(offer.getItemId()).getItem().getName());
        amountLabel.setText(String.format("%.2f", offer.getAmount()));
        additionalPriceLabel.setText(String.format("%d", offer.getForAdditionalPrice()));



    }


    public void setOffer(Offer offer)
    {
        setOfferLabels(offer);
    }
}