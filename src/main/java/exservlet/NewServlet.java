package exservlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/orders")
public class NewServlet extends HttpServlet {
    private static Long id = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = Util.readStream(request.getInputStream());
        Orders post = new Orders();

        post.setId(id++);

        String [] data = s.split(",");
        for (String datum : data) {
            String[] dataSplit = datum.split(":");
            String inputType = dataSplit[0].replace("{", "").replace("\"", "").trim();
            String input = dataSplit[1].replace("}", "").replace("\"", "").trim();
            if ("orderNumber".equals(inputType)) {
                post.setOrderNumber(input);
            }
        }
        response.setHeader("Content-Type","application/json");
        response.getWriter().print(post);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
