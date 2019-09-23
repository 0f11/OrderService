package exservlet;

import java.util.Map;

public class MapperClass {
    private Map<String, String> map;

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public String reverseString(String key) {
        String reversed = "";
        for (int i = key.length() - 1; i >= 0; i--) {
            reversed = reversed + key.charAt(i);
        }
        return reversed;
    }

    public void addDataToMap(String key, String value) {
        String keyAdd = reverseString(key);
        String valueAdd = reverseString(value);
        map.put(keyAdd, valueAdd);
    }
}
