package exservlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/api/orders")
public class NewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Orders post = MapClass.setToMap(objectMapper.readValue(request.getInputStream(), Orders.class));

        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(objectMapper.writeValueAsString(post));

    }

        protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String s = request.getParameter("id");
            if (s == null)
                response.getWriter().println("Invalid id");

            ObjectMapper objectMapper = new ObjectMapper();
            Orders order = MapClass.getById(s);
            response.setContentType("application/json");
            response.getWriter().println(objectMapper.writeValueAsString(order));
        }
    }

