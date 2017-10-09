package com.universe.zany.taskbreaker.instrumentationtests;


import android.arch.persistence.room.Room;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.data.dao.TaskDatabase;
import com.universe.zany.taskbreaker.core.Task;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.universe.zany.taskbreaker.instrumentationtests.SyncLiveData.getValue;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TaskDatabase mDatabase;

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
    public void tests() throws InterruptedException{
        // month + 1, day + 3
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date date1 = cal.getTime();
        // month + 0, day + 2
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date date2 = cal.getTime();
        // month + 0, day + 35
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 35);
        Date date3 = cal.getTime();
        // month + 2, day + 0
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 2);
        Date date4 = cal.getTime();

        Task task1 = new Task(new Date(), "task 1: task month + 1, day + 3", date1);
        Task task2 = new Task(new Date(), "task 2: task month + 1, day + 3", date1);
        Task task3 = new Task(new Date(), "task 3: task month + 0, day + 2", date2);
        Task task4 = new Task(new Date(), "task 4: task month + 0, day + 35", date3);
        Task task5 = new Task(new Date(), "task 5: task month + 2, day + 0", date4);
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(task1);
        mockTasks.add(task2);
        mockTasks.add(task3);
        mockTasks.add(task4);
        mockTasks.add(task5);

        // test loadAllTask
        mDatabase.taskDao().insertTasks(mockTasks);
        List<Task> allTasks = getValue(mDatabase.taskDao().loadAllTask());
        assertEquals(5, allTasks.size());

        // test loadTasksInRange
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 40);
        List<Task> tasksNowAndNextMonth = getValue(mDatabase.taskDao().
                loadTasksInRange(new Date().getTime(), cal.getTimeInMillis()));
        assertEquals(4, tasksNowAndNextMonth.size());

        // test searchTaskByContent
        List<Task> searchResult = getValue(mDatabase.taskDao().searchTaskByContent("%month + 1%"));
        assertEquals(2, searchResult.size());

        // test findTaskById
        Task tempTask = allTasks.get(0);
        Task findTask = getValue(mDatabase.taskDao().findTaskById(tempTask.getId()));
        assertEquals(tempTask.getContent(), findTask.getContent());

        // test loadTasksInMonth

        // test loadTasksInDay

        // test loadAllPassedTasks
        //TODO finish all tests

    }

}
