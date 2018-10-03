package com.byinal.playground.exception;

public class PlaysiteNotFoundException extends RuntimeException {

    public PlaysiteNotFoundException() {
        super("Playsite is not found!");
    }

    public PlaysiteNotFoundException(String name) {
        super("Playsite with name: '" + name + "' is not found!");
    }

}
