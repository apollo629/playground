package com.byinal.playground.site;

import com.byinal.playground.kid.Kid;
import com.byinal.playground.ticket.Ticket;
import com.byinal.playground.ticket.TicketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractPlaysite implements Playsite {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    final Double capaticy;
    Double numberOfPlaceTaken = 0.0;
    String name;
    Deque<Kid> queue;
    Integer totalVisitors = 0;
    private int normalCounter = 0;

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

    public Integer getTotalVisitors() {
        return totalVisitors;
    }

    public boolean addKid(Kid kid) {
        //add if there is a place in playsite and kid is not already in this playsite
        if (hasAnyPlace() && !getName().equals(kid.getNowPlayingIn())) {
            numberOfPlaceTaken++;
            totalVisitors++;
            kid.setNowPlayingIn(getName());
            logger.info("Kid: {} is added to playsite: {}", kid.getName(), this.getName());
            return true;
        } else if (kid.askForQueue() && !this.queue.contains(kid)) {
            logger.info("Capacity is full, can not add more kid to playsite. " +
                    "Kid accepts to be in queue and will be queued");
            return enqueue(kid);
        } else {
            return false;
        }
    }

    public boolean enqueue(Kid kid) {
        Deque<Kid> tempQueue = new LinkedBlockingDeque<>();
        Ticket ticket = kid.getTicket();
        if (ticket.getTicketType() == TicketType.VIP && isQueueNotEmpty()) {
            Kid firstKid = this.queue.peekFirst();
            if (firstKid.getTicket().getTicketType().equals(TicketType.NORMAL)) {
                logger.info("Kid: {} with VIP ticket is added to head of queue", kid.getName());
                this.queue.addFirst(kid);
            } else {
                tempQueue.add(this.queue.poll());
                while (!this.queue.isEmpty()) {
                    Kid nextKid = this.queue.poll();
                    if(this.queue.isEmpty()){
                        if(normalCounter == 3){
                            this.queue.add(nextKid);
                            this.queue.addFirst(kid);
                            addKidsFromTempToRealQueue(tempQueue);
                        }else{
                            logger.info("There is no 3 kid after first VIP. So kid: {} is added to queue after normals", kid.getName());
                            tempQueue.addLast(nextKid);
                            addKidsFromTempToRealQueue(tempQueue);
                            this.queue.add(kid);
                        }
                        return true;
                    }
                    if (TicketType.NORMAL.equals(nextKid.getTicket().getTicketType()) && normalCounter < 3) {
                        tempQueue.add(nextKid);
                        normalCounter++;
                    } else if (TicketType.NORMAL.equals(nextKid.getTicket().getTicketType()) && normalCounter == 3) {
                        //found 4th normal after vip in the queue so new vip can be placed here
                        logger.info("Kid with VIP ticket is added to queue after 3 Normal Kid");
                        this.queue.addFirst(kid);
                        normalCounter = 0;
                        addKidsFromTempToRealQueue(tempQueue);
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
            logger.info("Kid: {} with normal ticket is added to head of queue", kid.getName());
            this.queue.addLast(kid);
            return true;
        }
    }

    private void addKidsFromTempToRealQueue(Deque<Kid> tempQueue) {
        while (!tempQueue.isEmpty()) {
            this.queue.addFirst(tempQueue.pollLast());
        }
    }

    public boolean removeKid(Kid kid) {
        if(!getName().equals(kid.getNowPlayingIn())){
            logger.info("Kid: {} is not in this playsite: {}", kid.getName(), this.getName());
            return false;
        }
        numberOfPlaceTaken--;
        kid.leavePlaysite(getName());
        logger.info("Kid: {} is removed from playsite: {}", kid.getName(), this.getName());
        if (hasAnyPlace() && isQueueNotEmpty()) {
            Kid kidFromQueue = this.queue.pollFirst();
            numberOfPlaceTaken++;
            kidFromQueue.setNowPlayingIn(getName());
            totalVisitors++;
            logger.info("Place is available so kid: {} from head of queue is added to playsite: {}", kidFromQueue.getName(), this.getName());
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
