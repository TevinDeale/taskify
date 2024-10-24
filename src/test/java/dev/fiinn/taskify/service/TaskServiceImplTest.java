package dev.fiinn.taskify.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.exception.InvalidDueDateException;
import dev.fiinn.taskify.exception.InvalidStatusException;
import dev.fiinn.taskify.model.Task;
import dev.fiinn.taskify.repository.TaskRepository;
import dev.fiinn.taskify.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    private TaskServiceImpl taskService;
    private TaskRepository taskRepository;
    static List<Task> tasks;

    @BeforeAll
    static void setUp() throws IOException {

        String pathToFile = "src/test/java/resources/sampleData.json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        tasks = objectMapper.readValue(
                Files.readAllBytes(Paths.get(pathToFile)),
                new TypeReference<List<Task>>() {
                });

        for (Task task : tasks) {

        }
    }

    @Test
    void testCreateTask() throws InvalidStatusException, InvalidDueDateException {



        Task expectedTask = new Task.Builder().setName("Get soda")
                .setDescription("Go to HEB and buy Diet Pepsi")
                .setStatus(Status.IN_PROGRESS)
                .setDueDate(DateUtil.now())
                .build();

        when(taskRepository.save(any(Task.class))).thenAnswer(i -> {
            Task[] arguments = (Task[]) i.getArguments();
            System.out.println(arguments[0]);
            return arguments[0];
        });

        String dueDate = DateUtil.toString(DateUtil.now());

        Task createdTask = taskService.createTask(
                "Get soda",
                "Go to HEB and buy Diet Pepsi",
                "IN_PROGRESS",
                dueDate
        );

        verify(taskRepository, times(1)).save(any(Task.class));

    }
}
