package exservlet;
import java.util.List;

public class Order {
    private Long id;
    private String orderNumber;
    private List<OrderRows> orderRows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderRows> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<OrderRows> orderRow) {
        this.orderRows = orderRow;
    }
}

