package com.byinal.playground.site;

public class DoubleSwing extends AbstractPlaysite {

    public DoubleSwing(String name, Double capaticy) {
        super(name, capaticy);
    }

    @Override
    public Double calculateUtilization() {
        if(numberOfPlaceTaken.equals(capaticy)){
            return 100.0;
        }
        return 0.0;
    }
}
