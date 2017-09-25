package com.universe.zany.taskbreaker.viewmodeltest;

import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.viewmodels.TaskViewModel;
import com.universe.zany.taskbreaker.viewmodels.ViewModelFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelFactoryTest {

    @Mock
    TaskRepository repo;
    @Test
    public void test() {

        ViewModelFactory factory = new ViewModelFactory(repo);

        assertEquals(TaskViewModel.class, factory.create(TaskViewModel.class).getClass());
    }
}
