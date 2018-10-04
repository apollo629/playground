package com.byinal.playground.manager;

import com.byinal.playground.exception.PlaysiteNotFoundException;
import com.byinal.playground.kid.Kid;
import com.byinal.playground.Playground;
import com.byinal.playground.site.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimplePlaygroudManager implements PlaygroundManager {

    private static final Logger logger = LoggerFactory.getLogger(SimplePlaygroudManager.class);

    private final Playground playground;

    public SimplePlaygroudManager(Playground playground) {
        this.playground = playground;
    }

    @Override
    public boolean addKid(String playsiteName, Kid kid) {
        if(isPlaysiteExists(playsiteName)){
            if(kid.isPlaying()){
                logger.info("Kid: {} is still playing in another playsite: {}. Cannot add to this playsite: {}", kid.getName(), kid.getNowPlayingIn(), playsiteName);
                return false;
            }
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

    @Override
    public Integer getTotalVisitors() {
        return this.playground.getPlaysiteRegistry().getPlaysiteMap().values()
                .stream()
                .mapToInt(Playsite::getTotalVisitors).sum();
    }

    @Override
    public Map<String, Double> getCurrentUtilization() {
        Map<String, Double> currentUtilization = new HashMap<>();
        Map<String, Playsite> playsites = this.playground.getPlaysiteRegistry().getPlaysiteMap();
        playsites.forEach((key, value) -> currentUtilization.put(key, value.calculateUtilization()));
        return currentUtilization;
    }

    private boolean isPlaysiteExists(String playSiteName) {
        return this.playground.getPlaysiteRegistry().getPlaysiteMap().containsKey(playSiteName);
    }

    private Playsite getPlaysiteFromRegistery(String playSiteName) {
        return Optional.ofNullable(this.playground.getPlaysiteRegistry().find(playSiteName))
        .orElseThrow(PlaysiteNotFoundException::new);
    }

    private int getPlaySiteTotalVisitors(String playSiteName) {
        return getPlaysiteFromRegistery(playSiteName).getTotalVisitors();
    }

}
