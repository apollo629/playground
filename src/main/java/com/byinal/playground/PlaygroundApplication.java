package com.byinal.playground;

import com.byinal.playground.kid.Kid;
import com.byinal.playground.manager.PlaygroundManager;
import com.byinal.playground.manager.SimplePlaygroudManager;
import com.byinal.playground.site.Ballpit;
import com.byinal.playground.site.Carousel;
import com.byinal.playground.site.Playsite;
import com.byinal.playground.site.Slide;
import com.byinal.playground.ticket.Ticket;
import com.byinal.playground.ticket.TicketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class PlaygroundApplication {

    private static final Logger logger = LoggerFactory.getLogger(PlaygroundApplication.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Test log");

        //create playsites
        Carousel carousel1 = new Carousel("carousel-1", 2.0);
        Carousel carousel2 = new Carousel("carousel-2", 3.0);
        Slide slide = new Slide("slide-1", 4.0);
        Ballpit ballpit = new Ballpit("ballpit-1", 2.0);

        //create kids
        Kid alp = new Kid("Alp", 4, new Ticket(TicketType.NORMAL, 100000000L));
        Kid fatih = new Kid("Fatih", 4, new Ticket(TicketType.NORMAL, 100000001L));
        Kid ali = new Kid("Ali", 4, new Ticket(TicketType.VIP, 100000002L));
        Kid kid4 = new Kid("kid4", 4, new Ticket(TicketType.NORMAL, 100000003L));
        Kid kid5 = new Kid("kid5", 4, new Ticket(TicketType.NORMAL, 100000004L));
        Kid kid6 = new Kid("kid6", 4, new Ticket(TicketType.VIP, 100000005L));
        Kid kid7 = new Kid("kid7", 4, new Ticket(TicketType.NORMAL, 100000006L));
        Kid kid8 = new Kid("kid8", 4, new Ticket(TicketType.NORMAL, 100000007L));

        //create a playground
        Playground playground = new Playground();
        playground = playground.addPlaysites(carousel1, carousel2, slide, ballpit);

        //create a manager for playground
        PlaygroundManager manager = new SimplePlaygroudManager(playground);

        //add kids to playground
        manager.addKid("carousel-1", alp);
        manager.addKid("carousel-1", fatih);
        Thread.sleep(1000);
        manager.addKid("carousel-1", kid4);
        manager.addKid("carousel-1", ali);
        manager.addKid("carousel-1", kid5);
        manager.addKid("carousel-1", kid7);
        manager.addKid("carousel-1", kid8);
        manager.addKid("carousel-1", kid6);

        logger.info("Alp is now playing at {}", alp.getNowPlayingIn());

        //take a look at the queue at playsite 'carousel-1'
        Playsite playsite = playground.getPlaysiteRegistry().find("carousel-1");

        //take a look at the queue
        printQueueByPlaysite(playsite);

        //take a look at total visitors by playsite
        logger.info("Total Visitors by playsite: {}", manager.getTotalVisitorsByPlaysite());
        //take a look at total visitors by playsite
        logger.info("Total Visitors at playground: {}", manager.getTotalVisitors());

        //remove a kid from playsite 'carousel-1'
        manager.removeKid("carousel-1", fatih);

        //take a look at the queue at playsite 'carousel-1' after removing on kid from playsite
        printQueueByPlaysite(carousel1);

        //take a look at total visitors by playsite after removal of one kid
        logger.info("Total Visitors by playsite: {}", manager.getTotalVisitorsByPlaysite());

        manager.addKid("ballpit-1", fatih);
        Thread.sleep(1000);

        //remove Ali from 'carousel-2' and add he to 'slide-1'
        manager.removeKid("carousel-1", ali);
        manager.addKid("slide-1", ali);
        Thread.sleep(1000);

        //take a look at total visitors by playsite
        logger.info("Total Visitors by playsite: {}", manager.getTotalVisitorsByPlaysite());
        //take a look at total visitors by playsite
        logger.info("Total Visitors at playground: {}", manager.getTotalVisitors());

        logger.info("Kid: {}, history of played sites: {}", fatih.getName(), fatih.getPlaysiteLogs().values().toString());
        logger.info("Kid: {}, history of played sites: {}", ali.getName(), ali.getPlaysiteLogs().values().toString());
        logger.info("Playground Utilization By Playsite: {}", manager.getCurrentUtilization());

    }

    private static void printQueueByPlaysite(Playsite playsite) {
        Iterator<Kid> iterator = playsite.getQueue().iterator();
        int i = 1;
        logger.info("----------- QUEUE -----------");
        while (iterator.hasNext()){
            logger.info("{}- {}", i, iterator.next());
            i++;
        }
    }
}
