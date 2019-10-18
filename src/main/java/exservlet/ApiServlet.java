package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/api/orders")
public class ApiServlet extends HttpServlet {
    private final OrderService os = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Order post = os.addOrder(objectMapper.readValue(request.getInputStream(), Order.class));

        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(objectMapper.writeValueAsString(post));

    }
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Order> allOrders = new ArrayList<>();
            String id = request.getParameter("id");

            if (id == null) {
                allOrders.addAll(os.getAllOrders());
            }else{
                Order order = os.getOrder(Long.parseLong(id));
                allOrders.add(order);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json");
            response.getWriter().println(objectMapper.writeValueAsString(id == null ? allOrders : allOrders.get(0)));
        }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
        String id = req.getParameter("id");
        os.deleteOrder(Long.parseLong(id));
        resp.getWriter().print("Order Deleted");
    }
}

