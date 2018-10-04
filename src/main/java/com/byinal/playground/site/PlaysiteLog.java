package com.byinal.playground.site;

import java.time.Instant;

public class PlaysiteLog {

    private String playsiteName;
    private Long timestampAtJoin;
    private Long timestampAtLeave;

    public PlaysiteLog(String playsiteName, Long timestampAtJoin) {
        this.playsiteName = playsiteName;
        this.timestampAtJoin = timestampAtJoin;
    }

    public PlaysiteLog(String playsiteName, Long timestampAtJoin, Long timestampAtLeave) {
        this.playsiteName = playsiteName;
        this.timestampAtJoin = timestampAtJoin;
        this.timestampAtLeave = timestampAtLeave;
    }

    public String getPlaysiteName() {
        return playsiteName;
    }

    public void setPlaysiteName(String playsiteName) {
        this.playsiteName = playsiteName;
    }

    public Long getTimestampAtJoin() {
        return timestampAtJoin;
    }

    public void setTimestampAtJoin(Long timestampAtJoin) {
        this.timestampAtJoin = timestampAtJoin;
    }

    public Long getTimestampAtLeave() {
        return timestampAtLeave;
    }

    public void setTimestampAtLeave(Long timestampAtLeave) {
        this.timestampAtLeave = timestampAtLeave;
    }

    public Long getTimePlayedInSec() {
        if (timestampAtLeave == null) {
            return Instant.now().getEpochSecond() - timestampAtJoin;
        } else {
            return timestampAtLeave - timestampAtJoin;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Kid played ");
        sb.append(String.valueOf(getTimePlayedInSec())).append(" sec in ");
        sb.append(playsiteName).append("");
        return sb.toString();
    }
}
