package com.byinal.playground.kid;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

public class PlaysiteLog {

    private UUID playsiteId;
    private Long timestampAtJoin;
    private Long timestampAtLeave;

    public PlaysiteLog(UUID playsiteId, Long timestampAtJoin) {
        this.playsiteId = playsiteId;
        this.timestampAtJoin = timestampAtJoin;
    }

    public PlaysiteLog(UUID playsiteId, Long timestampAtJoin, Long timestampAtLeave) {
        this.playsiteId = playsiteId;
        this.timestampAtJoin = timestampAtJoin;
        this.timestampAtLeave = timestampAtLeave;
    }

    public UUID getPlaysiteId() {
        return playsiteId;
    }

    public void setPlaysiteId(UUID playsiteId) {
        this.playsiteId = playsiteId;
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

    public Long getTimePlayedInSec(){
        if (timestampAtLeave == null){
            return Instant.now().getEpochSecond() - timestampAtJoin;
        } else {
          return timestampAtLeave - timestampAtJoin;
        }
    }
}
