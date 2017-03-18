package com.glooory.petal.app.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Glooory on 17/3/18.
 */

public class TimeUtils {

    public static final int SECOND = 1000;
    public static final int MINUTE = SECOND * 60;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;
    public static final int WEEK = DAY * 7;

    private TimeUtils() {
    }

    public static String getTimeDifference(long oldTime, long currentTime) {
        long tempOldTime = oldTime * 1000;
        long timeDifference = currentTime - tempOldTime;
        if (timeDifference < MINUTE) {
            return timeDifference / SECOND + " 秒前";
        } else if (timeDifference < HOUR) {
            return timeDifference / MINUTE + " 分钟前";
        } else if (timeDifference < DAY) {
            return timeDifference / HOUR + " 小时前";
        } else if (timeDifference < WEEK) {
            return timeDifference / DAY + " 天前";
        } else {
            return DateFormat.getDateTimeInstance(2, 2, Locale.CHINESE).format(new Date(tempOldTime));
        }
    }
}