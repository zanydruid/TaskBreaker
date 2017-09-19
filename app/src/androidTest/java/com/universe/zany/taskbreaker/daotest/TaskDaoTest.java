package com.universe.zany.taskbreaker.daotest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao.TaskDatabase;
import com.universe.zany.taskbreaker.core.Task;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    /**
     * This makes sure the method waits data becomes available from the observer.
     */
    public static <T>  T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public  void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }

}
