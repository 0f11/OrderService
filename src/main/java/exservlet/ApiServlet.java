package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;

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
    private  OrderService os;

    @Override
    public void init(){
        var servletContext = getServletContext();
        var sprintContext = (ApplicationContext) servletContext.getAttribute("context");
        os = (OrderService) sprintContext.getBean("orderService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Order post = objectMapper.readValue(request.getInputStream(), Order.class);
        if (post.getOrderNumber().length() < 2 || post.getOrderNumber() == null){
            response.setStatus(400);
            response.getWriter().println("too_short_number");
            return;
        }
        var correctOrder = os.addOrder(post);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(objectMapper.writeValueAsString(correctOrder));

    }
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws IOException {
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

