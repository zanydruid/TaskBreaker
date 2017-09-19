package com.universe.zany.taskbreaker.daotest;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao.TaskItemDatabase;
import com.universe.zany.taskbreaker.core.Item;
import com.universe.zany.taskbreaker.core.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import static com.universe.zany.taskbreaker.daotest.SyncLiveData.getValue;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    private TaskItemDatabase mDatabase;

    @Before
    public void setup() throws Exception{
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                    TaskItemDatabase.class).build();
        // insert a Task
        Task mockTask = new Task(new Date(), "mock task", new Date());
        mDatabase.taskDao().insertTask(mockTask);
    }

    @After
    public void cleanup() {
        mDatabase.close();
    }

    @Test
    public void tests() throws InterruptedException {
        // test insert
        int mockTaskId = getValue(mDatabase.taskDao().loadAllTask()).get(0).getId();
        mDatabase.itemDao().insertItems(new Item(mockTaskId, "test item1"),
                                        new Item(mockTaskId, "test item2"));
        List<Item> list = getValue(mDatabase.itemDao().getItemByTaskId(1));
        assertEquals(2, list.size());

        // test update
        Item item1 = list.get(0);
        item1.setContent("test item1 updated");
        mDatabase.itemDao().updateItems(item1);
        Item itemResult = getValue(mDatabase.itemDao().getItemById(item1.getId()));
        assertEquals(item1.getContent(), itemResult.getContent());

        // test delete
        Item item2 = list.get(1);
        mDatabase.itemDao().deleteItems(item1, item2);
        List<Item> deletedList = getValue(mDatabase.itemDao().getItemByTaskId(1));
        assertEquals(0, deletedList.size());
    }

}
