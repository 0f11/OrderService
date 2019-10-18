package exservlet;

import org.apache.commons.dbcp2.BasicDataSource;
import util.DataSourceProvider;
import util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao {

    public void deleteOrder(long id){
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection con = dataSource.getConnection()){
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.execute();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Order addOrder(Order order) {
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        String sql = "insert into orders (order_number) values (?) ";
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            ps.setString(1, order.getOrderNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getLong("id"));
            }

            addOrderRows(con, order);
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return getOrderById(order.getId());
    }
    public void addOrderRows(Connection con , Order order) throws SQLException{
        if (order.getOrderRows() == null || order.getId() == null || order.getOrderRows().isEmpty()) {
            return;
        }
        String sql = "insert into rows(item_name,quantity,price,order_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < order.getOrderRows().size();i++){
            OrderRows orderRow = order.getOrderRows().get(i);
            ps.setString(1,orderRow.getItemName());
            ps.setInt(2,orderRow.getQuantity());
            ps.setInt(3,orderRow.getPrice());
            ps.setLong(4,order.getId());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    public List<Order> getAllOrders() {
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        List<Order> allOrders = new ArrayList<>();
        String sql = "SELECT ORD.id as id,\n" +
                "               ORD.order_number,\n" +
                "               ORDROW.id AS row_id,\n" +
                "               ORDROW.item_name,\n" +
                "               ORDROW.quantity,\n" +
                "               ORDROW.price,\n" +
                "               ORDROW.order_id AS  row_order_id\n" +
                "    FROM orders ORD\n" +
                "            LEFT JOIN \"rows\" ORDROW ON ORD.id = ORDROW.order_id";
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs =  ps.getResultSet();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                Order order = new Order();
                order.setOrderRows(new ArrayList<>());
                OrderRows orderRow = new OrderRows();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("order_number"));
                orderRow.setRowId(rs.getLong("row_id"));
                orderRow.setItemName(rs.getString("item_name"));
                orderRow.setPrice(rs.getInt("price"));
                orderRow.setQuantity(rs.getInt("quantity"));
                orderRow.setOrderRowId(rs.getLong("row_order_id"));
                order.getOrderRows().add(orderRow);
                allOrders.add(order);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allOrders;
    }

    public Order getOrderById(Long id) {
        Order newOrder = new Order();
        OrderRows newOrderRow = new OrderRows();
        newOrder.setOrderRows(new ArrayList<>());
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        String sql = "SELECT ORD.id as id,\n" +
                "       ORD.order_number,\n" +
                "       ORDROW.id AS row_id,\n" +
                "       ORDROW.item_name,\n" +
                "       ORDROW.quantity,\n" +
                "       ORDROW.price,\n" +
                "       ORDROW.order_id AS  row_order_id\n" +
                "FROM orders ORD\n" +
                "            LEFT JOIN \"rows\" ORDROW ON ORD.id = ORDROW.order_id\n" +
                "WHERE ORD.id = ?;";
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                newOrder.setId(rs.getLong("id"));
                newOrder.setOrderNumber(rs.getString("order_number"));
                newOrderRow.setRowId(rs.getLong("row_id"));
                newOrderRow.setOrderRowId(rs.getLong("row_order_id"));
                newOrderRow.setItemName(rs.getString("item_name"));
                newOrderRow.setPrice(rs.getInt("price"));
                newOrderRow.setQuantity(rs.getInt("quantity"));
                newOrder.getOrderRows().add(newOrderRow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return newOrder;
    }
}
