package com.byinal.playground;

import com.byinal.playground.kid.Kid;
import com.byinal.playground.manager.PlaygroundManager;
import com.byinal.playground.manager.SimplePlaygroudManager;
import com.byinal.playground.site.Ballpit;
import com.byinal.playground.site.Carousel;
import com.byinal.playground.site.Slide;
import com.byinal.playground.ticket.Ticket;
import com.byinal.playground.ticket.TicketType;

import java.util.Iterator;

public class PlaygroundApplication {

    public static void main(String[] args) {
        Carousel carousel1 = new Carousel("carousel1", 2.0);
        Carousel carousel2 = new Carousel("carousel2", 3.0);
        Slide slide = new Slide("slide1", 4.0);
        Ballpit ballpit = new Ballpit("ballpit1", 2.0);

        Kid rasmus = new Kid("Rasmus", 5,
                new Ticket(TicketType.NORMAL, 100000000L));
        Kid hanna = new Kid("Hanna", 4,
                new Ticket(TicketType.NORMAL, 100000001L));
        Kid helgi = new Kid("Helgi", 3,
                new Ticket(TicketType.VIP, 100000002L));
        Kid kid4 = new Kid("kid4", 5,
                new Ticket(TicketType.NORMAL, 100000003L));
        Kid kid5 = new Kid("kid5", 4,
                new Ticket(TicketType.NORMAL, 100000004L));
        Kid kid6 = new Kid("kid6", 4,
                new Ticket(TicketType.VIP, 100000004L));

        Playground playground = new Playground();
        playground = playground.addPlaysites(carousel1, carousel2, slide, ballpit);

        PlaygroundManager manager = new SimplePlaygroudManager(playground);

        manager.addKid("carousel1", rasmus);
        manager.addKid("carousel1", hanna);
        manager.addKid("carousel1", kid4);
        manager.addKid("carousel1", helgi);
        manager.addKid("carousel1", kid5);
        manager.addKid("carousel1", kid6);


        System.out.println(rasmus.getNowPlayingIn());
        System.out.println(rasmus.getPlaysiteLogs().toString());
        Iterator<Kid> carousel1Iterator = playground.getPlaysiteRegistry().find("carousel1").getQueue().iterator();
        while (carousel1Iterator.hasNext()){
            System.out.println(carousel1Iterator.next());
        }


    }
}
