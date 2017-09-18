package com.universe.zany.taskbreaker.daotest;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao.TaskDatabase;
import com.universe.zany.taskbreaker.core.Task;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TaskDatabase mDatabase;

//    @Rule
//    public InstantTaskExecutorRule instantTaskExecutorRule =
//            new InstantTaskExecutorRule();

    @Before
    public void setup() throws Exception{
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                TaskDatabase.class).build();
    }

    @After
    public void cleanup() {
        mDatabase.close();
    }

    @Test
    public void tests() {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date then = cal.getTime();
        Task task = new Task(now, "A test Task", then);

        mDatabase.taskDao().insertTask(task);
        List<Task> allList = mDatabase.taskDao().loadAllTask();
        assertEquals(1, allList.size());
        List<Task> resultList = mDatabase.taskDao().loadTaskFromDeadline(now.getTime());
        assertEquals(1, resultList.size());
        int taskId = resultList.get(0).tId;
        Task resultTaskById = mDatabase.taskDao().findTaskById(taskId);
        assertEquals(task.getContent(), resultTaskById.getContent());
        List<Task> resultListBySearch = mDatabase.taskDao().searchTaskByContent("%test%");
        assertEquals(1, resultListBySearch.size());

    }

}
