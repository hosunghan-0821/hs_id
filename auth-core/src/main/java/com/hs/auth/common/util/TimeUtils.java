package com.hs.auth.common.util;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public final class TimeUtils {
    
    public static final ZoneId UTC_ZONE = ZoneOffset.UTC;
    
    private TimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    public static ZonedDateTime nowUtc() {
        return ZonedDateTime.now(UTC_ZONE);
    }
    
    public static ZonedDateTime fromDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.toInstant().atZone(UTC_ZONE);
    }
    
    public static Date toDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            throw new IllegalArgumentException("ZonedDateTime cannot be null");
        }
        return Date.from(zonedDateTime.toInstant());
    }
}