package exservlet;

public class OrderRow {

    private String itemName;
    private Integer quantity;
    private Integer price;


    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }
    public String getItemName(){
        return itemName;
    }
    public Integer getQuantity(){
        return quantity;
    }

    }

