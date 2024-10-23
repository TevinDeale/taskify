package dev.fiinn.taskify.controller;

import dev.fiinn.taskify.dto.TaskDto;
import dev.fiinn.taskify.exception.*;
import dev.fiinn.taskify.model.Task;
import dev.fiinn.taskify.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<Task>> createTask(@RequestBody List<TaskDto> taskDtos) throws InvalidStatusException, InvalidDueDateException {
        List<Task> newTasks = new ArrayList<>();

        for (TaskDto taskDto : taskDtos) {
            newTasks.add(taskService.createTask(
                            taskDto.getTitle(),
                            taskDto.getDescription(),
                            taskDto.getStatus(),
                            taskDto.getDueDate()
            ));
        };

        return ResponseEntity.status(HttpStatus.CREATED).body(newTasks);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> taskList = taskService.getAllTasks();

        if (taskList.isEmpty()) {
            throw new EmptyListException("There are no tasks available, create one at /api/tasks/create");
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTaskByStatus(@PathVariable String status) throws InvalidStatusException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskByStatus(status));
    }

    @GetMapping("/created")
    public ResponseEntity<List<Task>> getTaskCreatedBetween(@RequestParam String startDate, @RequestParam String endDate) throws InvalidDateRangeException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksCreatedBetween(startDate, endDate));
    }

    @GetMapping("/updated")
    public ResponseEntity<List<Task>> getTaskUpdatedBetween(@RequestParam String startDate, @RequestParam String endDate) throws InvalidDateRangeException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksUpdatedBetween(startDate, endDate));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getTaskCompletedBetween(@RequestParam String startDate, @RequestParam String endDate) throws InvalidDateRangeException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskCompletedBetween(startDate, endDate));
    }

    @GetMapping("/due")
    public ResponseEntity<List<Task>> getTaskDueBetween(@RequestParam String startDate, @RequestParam String endDate) throws InvalidDateRangeException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksDueDateBetween(startDate, endDate));
    }

    @GetMapping("/due/today")
    public ResponseEntity<List<Task>> getTaskDueToday() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTaskDueToday());
    }

    @GetMapping("/created/today")
    public ResponseEntity<List<Task>> getTasksCreatedToday() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksCreatedToday());
    }

    @GetMapping("/updated/today")
    public ResponseEntity<List<Task>> getTaskUpdatedToday() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksUpdatedToday());
    }

    @GetMapping("/completed/today")
    public ResponseEntity<List<Task>> getTaskCompletedToday() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.getTasksCompletedToday());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasksByTitleOrDescription(@RequestParam String searchTerm) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.searchTasksByTitleOrDescription(searchTerm, searchTerm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) throws TaskNotFoundException, InvalidStatusException, InvalidDueDateException {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(taskService.updateTask(
                        taskDto.getTitle(),
                        taskDto.getDescription(),
                        taskDto.getStatus(),
                        taskDto.getDueDate(),
                        id
                ));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws GeneralTaskifyException, TaskNotFoundException {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(taskService.completeTask(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) throws TaskNotFoundException {

        taskService.deleteTask(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Task deleted successfully.");
    }
}
