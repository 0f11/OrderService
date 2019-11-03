package exservlet;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetExtractorForOrder implements ResultSetExtractor<List<Order>> {
    private List<Order> orders = new ArrayList<>();
    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
        OrderRows newOrderRow = new OrderRows();
        Order newOrder = new Order();
        newOrder.setOrderRows(new ArrayList<>());

        while (rs.next()) {
            newOrder.setId(rs.getLong("id"));
            newOrder.setOrderNumber(rs.getString("order_number"));
            newOrderRow.setRowId(rs.getLong("row_id"));
            newOrderRow.setOrderRowId(rs.getLong("row_order_id"));
            newOrderRow.setItemName(rs.getString("item_name"));
            newOrderRow.setPrice(rs.getInt("price"));
            newOrderRow.setQuantity(rs.getInt("quantity"));
            newOrder.getOrderRows().add(newOrderRow);
            orders.add(newOrder);
        }
        return orders;
    }
}
