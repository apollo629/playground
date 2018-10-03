package com.byinal.playground.site;

import com.byinal.playground.kid.Kid;

import java.util.Deque;

public interface Playsite {

    Double calculateUtilization();

    boolean addKid(Kid kid);

    boolean enqueue(Kid kid);

    boolean removeKid(Kid kid);

    boolean hasAnyPlace();

    String getName();

    int getTotalVisitors();

    public Deque<Kid> getQueue();

}
