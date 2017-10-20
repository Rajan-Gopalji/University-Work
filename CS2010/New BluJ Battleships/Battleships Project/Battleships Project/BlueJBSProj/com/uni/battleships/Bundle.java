package com.uni.battleships;


import java.util.HashMap;
import java.util.Map;

/**
 * User: dogmaan
 * Date: 28/06/12
 * Time: 12:12
 * A Class that holds {Key, Object} pair values for generic communication
 */
public class Bundle
{

    private Map<String, Object> objectMap;

    public Bundle()
    {
        objectMap = new HashMap<String, Object>();
    }

    public void add(String key, Object value)
    {
        objectMap.put(key, value);
    }

    public Object get(String key)
    {
        return objectMap.get(key);
    }
}
