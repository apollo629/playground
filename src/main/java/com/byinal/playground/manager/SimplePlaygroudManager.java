package com.byinal.playground.manager;

import com.byinal.playground.exception.PlaysiteNotFoundException;
import com.byinal.playground.kid.Kid;
import com.byinal.playground.Playground;
import com.byinal.playground.site.*;

import java.util.Map;
import java.util.stream.Collectors;

public class SimplePlaygroudManager implements PlaygroundManager {

    private final Playground playground;

    public SimplePlaygroudManager(Playground playground) {
        this.playground = playground;
    }

    @Override
    public boolean addKid(String playsiteName, Kid kid) {
        if(isPlaysiteExists(playsiteName)){
            Playsite playsite = getPlaysiteFromRegistery(playsiteName);
            return playsite.addKid(kid);
        }
        throw new PlaysiteNotFoundException();
    }

    @Override
    public boolean removeKid(String playsiteName, Kid kid) {
        return getPlaysiteFromRegistery(playsiteName).removeKid(kid);
    }

    @Override
    public Map<String, Integer> getTotalVisitorsByPlaysite() {
        return this.playground.getPlaysiteRegistry()
                .getPlaysiteMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> getPlaySiteTotalVisitors(e.getKey())));
    }

    private boolean isPlaysiteExists(String playSiteName) {
        return this.playground.getPlaysiteRegistry().getPlaysiteMap().containsKey(playSiteName);
    }

    private Playsite getPlaysiteFromRegistery(String playSiteName) {
        return this.playground.getPlaysiteRegistry().find(playSiteName);
    }

    private int getPlaySiteTotalVisitors(String playSiteName) {
        return getPlaysiteFromRegistery(playSiteName).getTotalVisitors();
    }

}
