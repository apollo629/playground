package com.byinal.playground.manager;

import com.byinal.playground.kid.Kid;

import java.util.Map;

public interface PlaygroundManager {

    boolean addKid(String playsiteName, Kid kid);

    boolean removeKid(String playsiteName, Kid kid);

    Map<String, Integer> getTotalVisitorsByPlaysite();

    Integer getTotalVisitors();

    Map<String, Double> getCurrentUtilization();

}
