package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.TypeConverter;

public class StatusConverter {
    @TypeConverter
    public static int statusToInt(Task.Status status) {
        if (status == null) {
            return -100;
        }
        if (status == Task.Status.IN_PROGRESS) {
            return 0;
        } else if (status == Task.Status.COMPLETED) {
            return 1;
        } else {
            return -1;
        }
    }

    @TypeConverter
    public static Task.Status fromInt(int code) {
        if (code == -100) {
            return null;
        } else if (code == 0) {
            return Task.Status.IN_PROGRESS;
        } else if (code == 1){
            return Task.Status.COMPLETED;
        } else {
            return Task.Status.FAILED;
        }
    }
}
