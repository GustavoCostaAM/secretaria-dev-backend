package com.secretaria.secretaria.util;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class JSON<T> {
    private String key;
    private T value;
    private Map<String, T> map;

    public void addValue(String key, T value){
        map.put(key, value);
    }

    public Map<String, T> map(){
        return map;
    }

    public Map<String, T> toMap(){
        return map;
    }

    //default constructor
    public JSON() {
        this.map = new LinkedHashMap<>();
    }
}
