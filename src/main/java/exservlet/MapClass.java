package exservlet;

import java.util.HashMap;
import java.util.Map;

public class MapClass {

    private static  Map<Long, Orders> ORDERS_MAP;
    private static Integer ID_COUNTER = 1;

    public static Orders setToMap(Orders order){
        if (ORDERS_MAP == null){
            ORDERS_MAP = new HashMap<>();
        }
        order.setId(ID_COUNTER.longValue());
        ORDERS_MAP.put(order.getId(), order);
        ID_COUNTER++;
        return order;
    }
    public static Orders getById(String id){
        return ORDERS_MAP.get(Long.parseLong(id));
    }
}
