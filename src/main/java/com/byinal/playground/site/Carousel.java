package com.byinal.playground.site;

public class Carousel extends AbstractPlaysite {

    public Carousel(String name, Double capaticy) {
        super(name, capaticy);
    }

    @Override
    public Double calculateUtilization() {
        return (numberOfPlaceTaken / capaticy) * 100;
    }
}
