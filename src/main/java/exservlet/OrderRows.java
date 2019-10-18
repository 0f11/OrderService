package exservlet;

public class OrderRows {

    private Long id;
    private String itemName;
    private Integer quantity;
    private Integer price;
    private Long orderRowId;


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

    public Long getRowId() {
        return id;
    }

    public void setRowId(Long id) {
        this.id = id;
    }

    public Long getOrderRowId() {
        return orderRowId;
    }

    public void setOrderRowId(Long orderRowId) {
        this.orderRowId = orderRowId;
    }
}

