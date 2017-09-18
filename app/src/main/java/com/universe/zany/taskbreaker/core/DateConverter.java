package com.universe.zany.taskbreaker.core;


import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long time) {
        return time == null ? null : new Date(time);
    }

    @TypeConverter
    public static Long ToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
