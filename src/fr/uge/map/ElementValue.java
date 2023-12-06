package fr.uge.map;

import java.util.HashMap;

public class ElementValue {
    private String stringValue;
    private HashMap<String, ElementValue> elementValueMap;
    private Integer intValue;

    // Constructeurs

    public ElementValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public ElementValue(Integer intValue) {
        this.intValue = intValue;
    }

    public ElementValue(HashMap<String, ElementValue> elementValueMap) {
        this.elementValueMap = elementValueMap;
    }

    // Getteurs et Setteurs

    public String getStringValue() {
        return stringValue;
    }

    public HashMap<String, ElementValue> getHashMapValue() {
        return elementValueMap;
    }

    public Integer getIntValue() {
        return intValue;
    }
    
    // Méthodes

    public ElementValue get(String key) {
        return (elementValueMap != null && elementValueMap.containsKey(key)) ?
               elementValueMap.get(key) : null;
    }

    @Override
    public String toString() {
        if (stringValue != null) {
            return stringValue;
        } else if (elementValueMap != null) {
            return elementValueMap.toString();
        } else if (intValue != null) {
            return intValue.toString();
        } else {
            return "ElementValue{}";
        }
    }
}
