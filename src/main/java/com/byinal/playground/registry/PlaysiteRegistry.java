package com.byinal.playground.registry;

import com.byinal.playground.site.Playsite;

import java.util.HashMap;
import java.util.Map;

public class PlaysiteRegistry {

    private Map<String, Playsite> playsiteMap = new HashMap<>();

    public void register(Playsite playsite) {
        this.playsiteMap.put(playsite.getName(), playsite);
    }

    public Playsite find(String name) {
        return this.playsiteMap.get(name);
    }

    public Map<String, Playsite> getPlaysiteMap() {
        return this.playsiteMap;
    }
}
