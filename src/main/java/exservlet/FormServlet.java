package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("orders/form")

public class FormServlet extends HttpServlet {
    private  OrderService os;

    @Override
    public void init(){
        var servletContext = getServletContext();
        var sprintContext = (ApplicationContext) servletContext.getAttribute("context");
        os = (OrderService) sprintContext.getBean("orderService");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getParameter("orderNumber");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("orderNumber", s);
        Order order = os.addOrder(objectMapper.convertValue(map, Order.class));
        response.setHeader("Content-Type", "text/plain");
        response.getWriter().print(order.getId());

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
