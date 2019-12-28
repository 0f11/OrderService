package exservlet;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetExtractorForOrder implements ResultSetExtractor<List<Order>> {
    private List<Order> ords = new ArrayList<>();

    @Override
    public List<Order> extractData(ResultSet rs) throws SQLException {

        while (rs.next()) {
            var id = rs.getLong("id");
            Order ord =
                    ords.stream()
                            .filter(o -> o.getId().equals(id))
                            .findFirst().orElse(null);
            if (ord == null) {
                ord = new Order();
                ord.setId(id);
                ord.setOrderNumber(rs.getString("order_number"));
                ord.setOrderRows(new ArrayList<>());
                ords.add(ord);
            }
            ord.getOrderRows().add(getOrderRowFromRs(rs));
        }
        return ords;
    }

    private OrderRows getOrderRowFromRs(ResultSet resultSet) throws SQLException {
        var orderRows = new OrderRows();
        orderRows.setRowId(resultSet.getLong("row_id"));
        orderRows.setItemName(resultSet.getString("item_name"));
        orderRows.setQuantity(resultSet.getInt("quantity"));
        orderRows.setPrice(resultSet.getInt("price"));
        orderRows.setOrderRowId(resultSet.getLong("row_order_id"));
        return orderRows;
    }
}
