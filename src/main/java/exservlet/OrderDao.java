package exservlet;

import util.DataSourceProvider;
import util.DbUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderDao {

    public Order addOrder(Order order) {
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "insert into \"orders\" (order_number) values (?) ",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getOrderNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Order newOrder = new Order();
                newOrder.setOrderRows(new ArrayList<>());
                newOrder.setId(rs.getLong("id"));
                newOrder.setOrderNumber(rs.getString("order_number"));
                return newOrder;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Order> getAllOrders() {
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        List<Order> allOrders = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from \"orders\"");
            ps.executeQuery();
            ResultSet rs =  ps.getResultSet();
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("order_number"));
                allOrders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allOrders;
    }

    public Order getOrderById(Long id) {
        Order newOrder = new Order();
        DataSourceProvider.setConnectionInfo(DbUtil.loadConnectionInfo());
        BasicDataSource dataSource = DataSourceProvider.getDataSource();
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from \"orders\" where id = ?");
            ps.setLong(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                newOrder.setId(rs.getLong("id"));
                newOrder.setOrderNumber(rs.getString("order_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return newOrder;
    }
}
