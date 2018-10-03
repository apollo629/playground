package com.byinal.playground.site;

import com.byinal.playground.kid.Kid;
import com.byinal.playground.ticket.Ticket;
import com.byinal.playground.ticket.TicketType;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractPlaysite implements Playsite {

    final Double capaticy;
    Double numberOfPlaceTaken = 0.0;
    String name;
    Deque<Kid> queue;
    int totalVisitors = 0;

    public AbstractPlaysite(String name, Double capaticy) {
        this.capaticy = capaticy;
        this.name = name;
        this.queue = new LinkedBlockingDeque<>();

    }

    public Double getCapaticy() {
        return capaticy;
    }

    public Double getNumberOfPlaceTaken() {
        return numberOfPlaceTaken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfPlaceTaken(Double numberOfPlaceTaken) {
        this.numberOfPlaceTaken = numberOfPlaceTaken;
    }

    public Deque<Kid> getQueue() {
        return this.queue;
    }

    public void setQueue(Deque<Kid> queue) {
        this.queue = queue;
    }

    public int getTotalVisitors() {
        return totalVisitors;
    }

    public boolean addKid(Kid kid) {
        //add if there is a place in playsite and kid is not already in this playsite
        if (hasAnyPlace() && !getName().equals(kid.getNowPlayingIn())) {
            numberOfPlaceTaken++;
            totalVisitors++;
            kid.setNowPlayingIn(getName());
            return true;
        } else if (kid.askForQueue() && !this.queue.contains(kid)) {
            System.out.println("Capacity is full, can not add more kid to playsite. " +
                    "Kid accepts to be in queue and will be queued");
            return enqueue(kid);
        } else {
            return false;
        }
    }

    private int normalCounter = 0;

    public boolean enqueue(Kid kid) {
        Deque<Kid> tempQueue = new LinkedBlockingDeque<>();
        Ticket ticket = kid.getTicket();
        if (ticket.getTicketType() == TicketType.VIP && isQueueNotEmpty()) {
            Kid firstKid = this.queue.peekFirst();
            if (firstKid.getTicket().getTicketType().equals(TicketType.NORMAL)) {
                this.queue.addFirst(kid);
            } else {
                tempQueue.add(this.queue.poll());
                while (!this.queue.isEmpty()) {
                    Kid nextKid = this.queue.poll();
                    if(this.queue.isEmpty()){
                        tempQueue.addLast(nextKid);
                        while (!tempQueue.isEmpty()) {
                            this.queue.addFirst(tempQueue.pollLast());
                        }
                        this.queue.addLast(kid);
                        return true;
                    }
                    if (TicketType.NORMAL.equals(nextKid.getTicket().getTicketType()) && normalCounter < 3) {
                        tempQueue.add(nextKid);
                        normalCounter++;
                    } else if (TicketType.NORMAL.equals(nextKid.getTicket().getTicketType()) && normalCounter == 3) {
                        //found 4th normal after vip in the queue so new vip can be placed here
                        this.queue.addFirst(kid);
                        normalCounter = 0;
                        while (!tempQueue.isEmpty()) {
                            this.queue.addFirst(tempQueue.pollLast());
                        }
                        break;
                    } else {
                        //found VIP in the queue after 3 normal
                        normalCounter = 0;
                        tempQueue.add(nextKid);
                    }
                }
            }
            return true;
        } else {
            this.queue.addLast(kid);
            return true;
        }
    }

    public boolean removeKid(Kid kid) {
        numberOfPlaceTaken--;
        kid.leavePlaysite(getName());
        if (hasAnyPlace() && isQueueNotEmpty()) {
            Kid kidFromQueue = this.queue.getFirst();
            numberOfPlaceTaken++;
            kidFromQueue.setNowPlayingIn(getName());
            totalVisitors++;
        }
        return true;
    }

    private boolean isQueueNotEmpty() {
        return !this.queue.isEmpty();
    }

    public boolean hasAnyPlace() {
        return numberOfPlaceTaken < capaticy;
    }

}
