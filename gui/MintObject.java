package gui;

import java.util.HashMap;

/**
 *
 * @author Oliver Chu
 */
public class MintObject {
    private HashMap<String, Pointer> mapping;

    public MintObject() {
        mapping = new HashMap<String, Pointer>();
    }
    
    public SmartList<String> keys() {
        return new SmartList<String>(mapping.keySet());
    }
    
    public void put(String name, Pointer value) {
        mapping.put(name, value);
    }
    
    public void remove(String name) {
        mapping.remove(name);
    }
    
    public boolean containsName(String name) {
        return mapping.containsKey(name);
    }
    
    public Pointer get(String name) {
        return mapping.get(name);
    }
    
    public SmartList<Pointer> values() {
        return new SmartList<Pointer>(mapping.values());
    }
    
    public HashMap<String, Pointer> getMapping() {
        return mapping;
    }
    
    public boolean equals(MintObject m) {
        return mapping.equals(m.getMapping());
    }
    
    @Override
    public String toString() {
        return "object" + mapping.toString().replace("=", ": ");
    }
    
    public void putAll(MintObject mo) {
        HashMap<String, Pointer> map = mo.getMapping();
        mapping.putAll(map);
    }
}
