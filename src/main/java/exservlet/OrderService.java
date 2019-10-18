package exservlet;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao = new OrderDao();

    public Order addOrder(Order order){
        return orderDao.addOrder(order);
    }
    public Order getOrder(Long id){
        return orderDao.getOrderById(id);
    }
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }
    public void deleteOrder(Long id){
        orderDao.deleteOrder(id);
    }
}
