package com.aldogrand.kfc.pollingmanager.utils;

import java.util.concurrent.TimeUnit;

import com.aldogrand.kfc.pollingmanager.rules.Duration;

public class DateTimeUtils {

    public static Long convertToMilliseconds(Duration duration) {                
        TimeUnit timeUnit = duration.getUnit();
        return TimeUnit.MILLISECONDS.convert(duration.getDuration(), timeUnit);        
    }
}
