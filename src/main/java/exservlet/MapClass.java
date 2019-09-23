package exservlet;

import java.util.HashMap;
import java.util.Map;

public class MapClass {

    private static Map<Long, Orders> orderMap;
    private static Integer idCounter = 1;

    public static Orders addToMap(Orders order){
        if (orderMap == null){
            orderMap = new HashMap<>();
        }
        order.setId(idCounter.longValue());
        orderMap.put(order.getId(), order);
        idCounter++;
        return order;
    }
    public static Orders getById(String id){
        return orderMap.get(Long.parseLong(id));
    }
}
