package exservlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    public Order getOrder(Long id) {
        return orderDao.getOrderById(id);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void deleteOrder(Long id) {
        orderDao.deleteOrder(id);
    }
}
