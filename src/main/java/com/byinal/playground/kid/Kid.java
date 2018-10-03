package com.byinal.playground.kid;

import com.byinal.playground.site.PlaysiteLog;
import com.byinal.playground.ticket.Ticket;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Kid implements Comparable {

    private String name;
    private Integer age;
    private Ticket ticket;
    private String nowPlayingIn;
    private Map<String, PlaysiteLog> playsiteLogs;

    public Kid(String name, Integer age, Ticket ticket) {
        this.name = name;
        this.age = age;
        this.ticket = ticket;
        this.playsiteLogs = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Map<String, PlaysiteLog> getPlaysiteLogs() {
        return playsiteLogs;
    }

    public String getNowPlayingIn() {
        return nowPlayingIn;
    }

    public boolean askForQueue() {
        return true;
    }

    public void setNowPlayingIn(String nowPlayingInId) {
        this.nowPlayingIn = nowPlayingInId;
        playsiteLogs.put(nowPlayingInId, new PlaysiteLog(nowPlayingInId, Instant.now().getEpochSecond()));
    }

    public void leavePlaysite(String playsiteId) {
        PlaysiteLog playsiteLog = playsiteLogs.get(playsiteId);
        playsiteLog.setTimestampAtLeave(Instant.now().getEpochSecond());
        playsiteLogs.replace(playsiteId, playsiteLog);
        this.nowPlayingIn = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kid kid = (Kid) o;
        return Objects.equals(name, kid.name) &&
                Objects.equals(age, kid.age) &&
                Objects.equals(ticket, kid.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, ticket);
    }

    @Override
    public int compareTo(Object o) {
        return equals(o) ? 0 : -1;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Kid{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", ticket=").append(ticket.toString());
        sb.append(", nowPlayingIn='").append(nowPlayingIn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
