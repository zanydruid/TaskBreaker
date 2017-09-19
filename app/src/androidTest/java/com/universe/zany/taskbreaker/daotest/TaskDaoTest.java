package com.universe.zany.taskbreaker.daotest;


import android.arch.persistence.room.Room;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao.TaskItemDatabase;
import com.universe.zany.taskbreaker.core.Task;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.universe.zany.taskbreaker.daotest.SyncLiveData.getValue;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

        mDatabase.taskDao().insertTask(task);
        List<Task> allList = getValue(mDatabase.taskDao().loadAllTask());
        assertEquals(1, allList.size());
        List<Task> resultList = getValue(mDatabase.taskDao().loadTaskFromDeadline(now.getTime()));
        assertEquals(1, resultList.size());
        int taskId = resultList.get(0).getId();
        Task resultTaskById = getValue(mDatabase.taskDao().findTaskById(taskId));
        assertEquals(task.getContent(), resultTaskById.getContent());
        List<Task> resultListBySearch = getValue(mDatabase.taskDao().searchTaskByContent("%test%"));
        assertEquals(1, resultListBySearch.size());

    }



}
