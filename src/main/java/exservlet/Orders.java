package exservlet;
import java.util.List;

public class Orders {
    private Long id;
    private String orderNumber;
    private List<OrderRow> orderRows;

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

    public List<OrderRow> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<OrderRow> orderRow) {
        this.orderRows = orderRow;
    }
}

