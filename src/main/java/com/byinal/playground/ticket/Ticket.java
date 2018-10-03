package com.byinal.playground.ticket;

public class Ticket {

    private Long number;
    private TicketType ticketType;
    private int vipJumps;

    public Ticket(TicketType ticketType, Long number) {
        this.number = number;
        this.ticketType = ticketType;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public int getVipJumps() {
        return vipJumps;
    }

    public void setVipJumps(int vipJumps) {
        this.vipJumps = vipJumps;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number=" + number +
                ", ticketType=" + ticketType +
                '}';
    }
}
