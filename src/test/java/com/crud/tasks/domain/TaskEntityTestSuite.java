package com.crud.tasks.domain;

import com.crud.tasks.controller.TaskController;
import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.service.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TaskEntityTestSuite {

    @Autowired
    private DbService dbService;
    @Autowired
    private TaskController taskController;

    @Test
    void testSaveAndGetTasks() {
        //Given
        TaskDto task1 = TaskDto.builder().title("task1_title").content("task1_content").build();
        TaskDto task2 = TaskDto.builder().title("task2_title").content("task2_content").build();
        TaskDto task3 = TaskDto.builder().title("task3_title").content("task3_content").build();

        //When
        taskController.createTask(task1);
        taskController.createTask(task2);
        taskController.createTask(task3);
        ResponseEntity<List<TaskDto>> tasksListEntity = taskController.getTasks();
        List<TaskDto> taskDtoList = tasksListEntity.getBody();

        //Then
        assert taskDtoList != null;
        assertEquals(3, taskDtoList.size());
        assertEquals("task1_title", taskDtoList.get(0).getTitle());
        assertEquals("task2_content", taskDtoList.get(1).getContent());
    }

    @Test
    void testGetTask() throws TaskNotFoundException {
        //Given
        Task task1 = Task.builder().title("task1_title").content("task1_content").build();
        dbService.saveTask(task1);

        //When
        ResponseEntity<TaskDto> taskEntity = taskController.getTask(task1.getId());
        TaskDto taskDto = taskEntity.getBody();

        //Then
        assert taskDto != null;
        assertEquals(task1.getId(), taskDto.getId());
        assertEquals("task1_title", taskDto.getTitle());
    }

    @Test
    void testDeleteTask() throws TaskNotFoundException {
        //Given
        Task task1 = Task.builder().title("task1_title").content("task1_content").build();
        dbService.saveTask(task1);

        //When
        taskController.deleteTask(task1.getId());

        //Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(task1.getId()));
    }

    @Test
    void testUpdateTask() throws TaskNotFoundException {
        //Given
        Task task1 = Task.builder().title("task1_title").content("task1_content").build();
        dbService.saveTask(task1);
        TaskDto taskDto = new TaskDto(task1.getId(), "task1_title_modified", "task1_content_modified");

        //When
        taskController.updateTask(taskDto);
        Task task = dbService.getTask(task1.getId());

        //Then
        assertEquals("task1_title_modified", task.getTitle());
        assertEquals("task1_content_modified", task.getContent());
    }
}
