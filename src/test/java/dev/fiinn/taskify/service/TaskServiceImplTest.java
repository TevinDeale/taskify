package dev.fiinn.taskify.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.fiinn.taskify.enums.Status;
import dev.fiinn.taskify.exception.GeneralTaskifyException;
import dev.fiinn.taskify.exception.InvalidDueDateException;
import dev.fiinn.taskify.exception.InvalidStatusException;
import dev.fiinn.taskify.exception.TaskNotFoundException;
import dev.fiinn.taskify.model.Task;
import dev.fiinn.taskify.repository.TaskRepository;
import dev.fiinn.taskify.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceImplTest {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskRepository taskRepository;

    private static List<Task> tasks;
    private static final String dueDate = DateUtil.toString(DateUtil.now());
    private static final LocalDate now = DateUtil.now();

    @BeforeAll
    static void setUp() throws IOException {

        String pathToFile = "src/test/java/resources/sampleData.json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        tasks = objectMapper.readValue(
                Files.readAllBytes(Paths.get(pathToFile)),
                new TypeReference<List<Task>>() {
                });
    }

    @BeforeEach
    void addTasksToRepo() {
        taskRepository.saveAll(tasks);
    }

    @Test
    void getTasks() {
        List<Task> foundTasks = taskService.getAllTasks();

        assertNotNull(foundTasks);
    }

    @Test
    void createNewTask() throws InvalidStatusException, InvalidDueDateException {

        Task newTask = taskService.createTask(
                "Test task",
                "This is a test task",
                "Pending",
                dueDate
        );

        List<Task> foundTasks = taskService.getAllTasks();

        assertNotNull(newTask);
        assertEquals(foundTasks.size(), 22);
        assertEquals(newTask.getId(), 22);
        assertEquals(newTask.getTitle(), "Test task");
        assertEquals(newTask.getDescription(), "This is a test task");
        assertEquals(newTask.getStatus(), Status.PENDING);
        assertEquals(newTask.getDueDate(), now);
        assertEquals(newTask.getCreatedAt(), now);
        assertEquals(newTask.getUpdatedAt(), now);
    }

    @Test
    void updateTask() throws TaskNotFoundException, InvalidStatusException, InvalidDueDateException {

        LocalDate newDate = now.plusDays(10);

        taskService.updateTask(
                "Pay bills",
                "This is a test update",
                "Pending",
                DateUtil.toString(newDate),
                15L
        );

        Task updatedTask = taskService.getTaskById(15L);

        assertNotNull(updatedTask);
        assertEquals(updatedTask.getId(), 15L);
        assertEquals(updatedTask.getTitle(), "Pay bills");
        assertNotEquals(updatedTask.getDescription(), "Ensure all monthly bills are paid");
        assertNotEquals(updatedTask.getDueDate(), DateUtil.parseString("10/31/2024"));
        assertEquals(updatedTask.getDescription(), "This is a test update");
        assertEquals(updatedTask.getDueDate(), newDate);

    }

    @Test
    void getTaskById() throws TaskNotFoundException {

        Task foundTask = taskService.getTaskById(17L);

        assertNotNull(foundTask);
        assertEquals(foundTask.getId(), 17L);
        assertEquals(foundTask.getTitle(), "Cook dinner");
    }

    @Test
    void deleteTask() throws TaskNotFoundException {
        taskService.deleteTask(17L);

        assertThrows(TaskNotFoundException.class, () -> {
            taskService.getTaskById(17L);
        });
    }

    @Test
    void completeTask() throws TaskNotFoundException, GeneralTaskifyException {

        taskService.completeTask(15L);

        Task completedTask = taskService.getTaskById(15L);

        System.out.println(completedTask.getUpdatedAt());

        assertNotNull(completedTask);
        assertEquals(completedTask.getId(), 15L);
        assertEquals(completedTask.getStatus(), Status.COMPLETED);
        assertEquals(completedTask.getCompletedAt(), now);
        assertEquals(completedTask.getUpdatedAt(), now);
    }
}
