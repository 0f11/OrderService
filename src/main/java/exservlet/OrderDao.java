package exservlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void deleteOrder(long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Order addOrder(Order order) {
        var orderInfo = Map.of("order_number", order.getOrderNumber());
        var id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(orderInfo);
        order.setId(id.longValue());
        addOrderRows(order);
        return getOrderById(order.getId());
    }

    public void addOrderRows(Order order) {
        if (order.getOrderRows() == null || order.getId() == null || order.getOrderRows().isEmpty()) {
            return;
        }
        var jdbcInt = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("rows")
                .usingGeneratedKeyColumns("row_id");
        List<MapSqlParameterSource> orderRows = new ArrayList<>();
        for (var orderRow : order.getOrderRows()) {
            var sqlPs = new MapSqlParameterSource()
                    .addValue("item_name", orderRow.getItemName())
                    .addValue("quantity", orderRow.getQuantity())
                    .addValue("price", orderRow.getPrice())
                    .addValue("order_id", order.getId());
            orderRows.add(sqlPs);
        }
        jdbcInt.executeBatch(orderRows.toArray(new MapSqlParameterSource[0]));
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT ORD.id as id,\n" +
                "               ORD.order_number,\n" +
                "               ORDROW.row_id AS row_id,\n" +
                "               ORDROW.item_name,\n" +
                "               ORDROW.quantity,\n" +
                "               ORDROW.price,\n" +
                "               ORDROW.order_id AS  row_order_id\n" +
                "    FROM orders ORD\n" +
                "            LEFT JOIN \"rows\" ORDROW ON ORD.id = ORDROW.order_id";
        return jdbcTemplate.query(sql, new ResultSetExtractorForOrder());
    }

    public Order getOrderById(Long id) {

        String sql = "SELECT ORD.id as id,\n" +
                "       ORD.order_number,\n" +
                "       ORDROW.row_id AS row_id,\n" +
                "       ORDROW.item_name,\n" +
                "       ORDROW.quantity,\n" +
                "       ORDROW.price,\n" +
                "       ORDROW.order_id AS  row_order_id\n" +
                "FROM orders ORD\n" +
                "            LEFT JOIN \"rows\" ORDROW ON ORD.id = ORDROW.order_id\n" +
                "WHERE ORD.id = ?;";
        var psSet = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        };
        List<Order> result = jdbcTemplate.query(sql, psSet, new ResultSetExtractorForOrder());
        return result == null || result.isEmpty() ? null : result.get(0);
    }
}
