package exservlet;

import java.util.HashMap;
import java.util.Map;

public class MapClass {

    private static  Map<Long, Orders> MAP;
    private static Integer ID_COUNTER = 1;

    public static Orders setToMap(Orders order){
        if (MAP == null){
            MAP = new HashMap<>();
        }
        order.setId(ID_COUNTER.longValue());
        MAP.put(order.getId(), order);
        ID_COUNTER++;
        return order;
    }
    public static Orders getById(String id){
        return MAP.get(Long.parseLong(id));
    }
}
