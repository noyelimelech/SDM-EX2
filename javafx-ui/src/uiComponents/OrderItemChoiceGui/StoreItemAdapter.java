package uiComponents.OrderItemChoiceGui;

import SDM.Item;
import SDM.StoreItem;

public class StoreItemAdapter {

    private Integer id;
    private String name;
    private Item.ItemType type;
    private Integer price;

    public StoreItemAdapter(Item itemToAdapt) {
        this.id = itemToAdapt.getId();
        this.name = itemToAdapt.getName();
        this.type = itemToAdapt.getType();
        this.price = null;
    }

    public StoreItemAdapter(StoreItem storeItemToAdapt) {
        this(storeItemToAdapt.getItem());
        price = storeItemToAdapt.getPrice();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item.ItemType getType() {
        return type;
    }

    public void setType(Item.ItemType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
