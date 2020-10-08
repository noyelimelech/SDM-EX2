package SDM;

import SDM.jaxb.schema.generated.IfYouBuy;
import SDM.jaxb.schema.generated.ThenYouGet;

import javax.xml.bind.annotation.XmlElement;

public class Discount
{

    private String name;
    private IfBuy ifBuy;
    private ThenGet thenGet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IfBuy getIfBuy() {
        return ifBuy;
    }

    public void setIfBuy(IfBuy ifBuy) {
        this.ifBuy = ifBuy;
    }

    public ThenGet getThenGet() {
        return thenGet;
    }

    public void setThenGet(ThenGet thenGet) {
        this.thenGet = thenGet;
    }

}