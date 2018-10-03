package com.byinal.playground;

import com.byinal.playground.registry.PlaysiteRegistry;
import com.byinal.playground.site.Playsite;

public class Playground {

    private PlaysiteRegistry playsiteRegistry;

    public Playground() {
        this.playsiteRegistry = new PlaysiteRegistry();
    }

    public PlaysiteRegistry getPlaysiteRegistry() {
        return playsiteRegistry;
    }

    public Playground addPlaysite(Playsite playsite) {
        this.playsiteRegistry.register(playsite);
        return this;
    }

    public Playground addPlaysites(Playsite... playsites) {
        for (Playsite playsite : playsites) {
            this.playsiteRegistry.register(playsite);
        }
        return this;
    }

}
