package common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public enum GyanSetuContex {
    SINGLETON;

    Map<String, Object> cache = new LinkedHashMap<String, Object>();


    /**
     * Loads Entries from supplied properties
     *
     * @param props
     */
    public void loadFromProperties(Properties props) {
        Set<Map.Entry<Object, Object>> entrySet = props.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet) {
            String propName = entry.getKey().toString();
            String propValue = entry.getValue().toString();
            cache.put(propName, propValue);
        }
    }


    /**
     * Gets all the keys mapped in the cache
     *
     * @return
     */
    public Set<String> getEntryKeys() {
        return cache.keySet();
    }


    public void setEntry(String key, Object value) {
        cache.put(key, value);
    }

    public Object getEntry(String key) {
        return cache.get(key);
    }


    public String getEntryAsString(String key) {
        Object o = cache.get(key);
        if (o == null)
            return null;

        return o.toString();
    }
}
