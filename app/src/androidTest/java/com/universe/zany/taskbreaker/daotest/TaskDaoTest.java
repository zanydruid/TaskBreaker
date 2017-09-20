package com.universe.zany.taskbreaker.daotest;


import android.arch.persistence.room.Room;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.dao.TaskItemDatabase;
import com.universe.zany.taskbreaker.core.Task;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TaskItemDatabase mDatabase;

    @Before
    public void setup() throws Exception{
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                TaskItemDatabase.class).build();
    }

    @After
    public void cleanup() {
        mDatabase.close();
    }

    @Test
    public void tests() throws InterruptedException{
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date then = cal.getTime();
        Task task = new Task(now, "A test Task", then);



    }



}
