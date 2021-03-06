package com.byinal.playground.site;

public class Slide extends AbstractPlaysite {

    public Slide(String name, Double capaticy) {
        super(name, capaticy);
    }

    @Override
    public Double calculateUtilization() {
        return (numberOfPlaceTaken / capaticy) * 100;
    }
}
