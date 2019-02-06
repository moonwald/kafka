package com.aldogrand.kfc.pollingmanager.rules;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Duration {

    private final long duration;

    private final TimeUnit unit;

    @JsonCreator
    public Duration(@JsonProperty("duration") long duration, @JsonProperty("unit") TimeUnit unit) {
        super();
        this.duration = duration;
        this.unit = unit;
    }

    public long getDuration() {
        return duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (duration ^ (duration >>> 32));
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Duration other = (Duration) obj;
        if (duration != other.duration) {
            return false;
        }
        if (unit != other.unit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Duration [duration=").append(duration).append(", unit=").append(unit).append("]");
        return builder.toString();
    }



}
