package com.universe.zany.taskbreaker.utiltest;


import com.universe.zany.taskbreaker.core.Month;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.util.Distributor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DistrituborTest {

    @Test
    public void tests() {

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

        List<Task> mockTaskList = new ArrayList<>();
        mockTaskList.add(task1);
        mockTaskList.add(task2);
        mockTaskList.add(task3);
        mockTaskList.add(task4);
        mockTaskList.add(task5);
        List<Month> resultList = Distributor.fillTasksInMonth(new Date(), mockTaskList);
        assertEquals(3, resultList.size());
        for (Month month : resultList) {
            System.out.print(month.toString());
        }
    }
}
