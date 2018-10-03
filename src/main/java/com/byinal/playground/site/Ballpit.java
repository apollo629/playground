package com.byinal.playground.site;

public class Ballpit extends AbstractPlaysite {

    public Ballpit(String name, Double capaticy) {
        super(name, capaticy);
    }

    @Override
    public Double calculateUtilization() {
        return (numberOfPlaceTaken / capaticy) * 100;
    }

}
