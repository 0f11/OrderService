package exservlet;

import util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("api/parser")
public class MapServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String> map = new HashMap<>();
        MapperClass mapClass = new MapperClass();
        mapClass.setMap(map);
        String s = Util.readStream(request.getInputStream());
        String [] data = s.split(",");

        for (String datum : data) {
            String[] dataSplit = datum.split(":");
            String key = dataSplit[0].replace("{", "").replace("\"", "").trim();
            String value = dataSplit[1].replace("}", "").replace("\"", "").trim();
            mapClass.addDataToMap(key, value);
        }
        ObjectMapper objectmapper = new ObjectMapper();
        String json = objectmapper.writeValueAsString(mapClass.getMap());
        response.setHeader("Content-Type","application/json");
        response.getWriter().print(json);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
