package com.universe.zany.taskbreaker.viewmodeltest;

import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.viewmodels.MonthViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MonthViewModelTest {

    @Mock
    TaskRepository repo;

    @Test
    public void tests() {
        MonthViewModel model = new MonthViewModel(repo);
        model.init(2018, 5);
        assertEquals("June", model.toString());
    }
}
