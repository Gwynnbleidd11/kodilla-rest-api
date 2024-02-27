package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService taskService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetEmptyTasksList() throws Exception {
        //Given
        when(taskMapper.mapToTaskDtoList(taskService.getAllTasks())).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetListOfTasks() throws Exception {
        //Given
        TaskDto taskDto1 = new TaskDto(1L, "Test 1", "Test 1");
        TaskDto taskDto2 = new TaskDto(2L, "Test 2", "Test 2");
        List<TaskDto> tasksList = List.of(taskDto1, taskDto2);
        when(taskMapper.mapToTaskDtoList(taskService.getAllTasks())).thenReturn(tasksList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Test 2")));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(123L, "Test title", "Test content");
        when(taskMapper.mapToTaskDto(taskService.getTask(123L))).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        doNothing().when(taskService).deleteTask(1L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        Task task = new Task(123L, "Test title", "Test content");
        TaskDto taskDto = new TaskDto(123L, "Test title", "Test content");
//        when(taskService.saveTask(any(Task.class))).thenReturn(task);
//        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
//
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService).saveTask(taskArgumentCaptor.capture());
        Task capturedTask = taskArgumentCaptor.getValue();
        assertThat(capturedTask).isEqualTo(task);

//        verify(taskMapper, times(1)).mapToTask(taskDto);
//        verify(taskService, times(1)).saveTask(argThat(savedTask ->
//                savedTask.getId().equals(task.getId()) &&
//                savedTask.getTitle().equals(task.getTitle()) &&
//                savedTask.getContent().equals(task.getContent())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(123L, "Test title", "Test content");
        when(taskMapper.mapToTaskDto(taskService.saveTask(any(Task.class)))).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test content")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)));
    }
}
